package fr.mleduc.giftproblem.core.cs;

import fr.mleduc.giftproblem.core.domain.Family;
import fr.mleduc.giftproblem.core.domain.GiftSolution;
import fr.mleduc.giftproblem.core.domain.Person;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

import java.util.*;

public class Problem {

    private List<Person> people = new ArrayList<>();
    private Set<Family> familySet = new HashSet<>();

    public Problem(Person... people) {
        this.people.addAll(Arrays.asList(people));
        this.people.forEach(p -> familySet.add(p.getFamily()));
    }

    public Problem(List<Person> people) {
        this.people.addAll(people);
        this.people.forEach(p -> familySet.add(p.getFamily()));
    }

    public List<GiftSolution> solve() {
        Model model = createModel();
        List<GiftSolution> giftSolutions = new ArrayList<>();
        Solver solver = model.getSolver();

        while (solver.solve()) {
            GiftSolution giftSolution = new GiftSolution();

            int i = 0;
            for (Variable v : model.getVars()) {
                if(v.getName().startsWith("var_")) {
                    giftSolution.addPair(people.get(i), people.get(((IntVar) v).getValue()));
                    i++;
                }
            }

            boolean singleLoop = GiftSolution.isSingleLoop(giftSolution);
            if (singleLoop) {
                giftSolutions.add(giftSolution);
                return giftSolutions;
            }

        }


        return giftSolutions;
    }

    private Model createModel() {
        Model model = new Model("Gift Problem");

        // adding some randomness to the result.
        Collections.shuffle(people);

        // encoding the people into model's variables.
        int size = people.size();
        IntVar[] persIntVar = new IntVar[size];
        for (int i = 0; i < size; i++) {
            String firstName = people.get(i).getName();
            IntVar intVar = model.intVar("var_" + firstName + "_" + i, +0, size - 1);
            persIntVar[i] = intVar;
        }

        // RULE 1: Everybody must give and receive a gift.
        if(people.size()>0)
            model.allDifferent(persIntVar).post();

        // RULE 2: no gift in a familly
        for (Family f : familySet) {
            for (Person p : f.getPeople()) {
                for (Person p2 : f.getPeople()) {
                    int pidx = people.indexOf(p);
                    int p2idx = people.indexOf(p2);
                    model.arithm(persIntVar[pidx], "!=", p2idx).post();
                }
            }
        }

        // RULE 3: gifts can only be shared between people present the same day.
        for (Person p : people) {
            for (Person p2 : people) {
                boolean presentOnTheSameDay = p.isOn24() && p2.isOn24()
                        || p.isOn25() && p2.isOn25();
                if (!presentOnTheSameDay) {
                    int pidx = people.indexOf(p);
                    int p2idx = people.indexOf(p2);
                    model.arithm(persIntVar[pidx], "!=", p2idx).post();
                }
            }
        }

        return model;
    }
}
