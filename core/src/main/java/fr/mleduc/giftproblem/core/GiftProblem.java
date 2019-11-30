package fr.mleduc.giftproblem.core;

import fr.mleduc.giftproblem.core.cs.Problem;
import fr.mleduc.giftproblem.core.domain.Family;
import fr.mleduc.giftproblem.core.domain.GiftSolution;
import fr.mleduc.giftproblem.core.domain.Person;
import fr.mleduc.giftproblem.core.utils.Pair;

import java.util.List;
import java.util.Random;

public class GiftProblem {
    public static void main(String[] args) {
        Family fA = new Family("a", true, true);
        Person pA1 = new Person("pA1", fA, "p1@a.com");
        Person pA2 = new Person("pA2", fA, "p2@a.com");
        Family fB = new Family("a", true, true);
        Person pB1 = new Person("pB1", fB, "p1@b.com");
        Person pB2 = new Person("pB2", fB, true, false, "tp2@b.com");

        Problem problem = new Problem(pA1, pA2, pB1, pB2);

        List<GiftSolution> giftSolutions = problem.solve();
        int res = new Random().nextInt(giftSolutions.size());

        GiftSolution giftSolution = giftSolutions.get(res);
        graphvizDebug(giftSolution);

        //SendMail sendMail = new SendMail("MAIL", "PASS");
        for (Pair<Person, Person> pair : giftSolution.getAttributions()) {
            String mail = pair.getKey().getMail();
            String donneur = pair.getKey().getName();
            String receveur = pair.getValue().getName();
            //  sendMail.send(mail, donneur, receveur);
        }
    }

    private static void graphvizDebug(GiftSolution giftSolution) {
        System.out.println("Graphiv debug");
        System.out.println("digraph {");
        for (Pair<Person, Person> pair : giftSolution.getAttributions()) {
            System.out.println("\"" + pair.getKey() + " - " + pair.getKey().getMail() + "\" -> \"" + pair.getValue() + " - " + pair.getValue().getMail() + "\"");
        }
        System.out.println("}");
        System.out.println();
    }
}
