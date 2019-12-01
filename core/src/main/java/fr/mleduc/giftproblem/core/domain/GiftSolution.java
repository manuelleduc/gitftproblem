package fr.mleduc.giftproblem.core.domain;

import fr.mleduc.giftproblem.core.utils.Pair;

import javax.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftSolution {
    private List<Pair<Person, Person>> attributions = new ArrayList<>();

    public void addPair(Person from, Person to) {
        this.attributions.add(new Pair<>(from, to));
    }

    public List<Pair<Person, Person>> getAttributions() {
        return attributions;
    }

    @JsonbTransient
        public static boolean isSingleLoop(GiftSolution solution) {
            List<Pair<Person, Person>> attributions = solution.getAttributions();
            int size = attributions.size();

            final List<Person> lst = new ArrayList<>();
            if(attributions.size() > 0) {
                Pair<Person, Person> current = attributions.get(0);
                while (!lst.contains(current.getKey())) {
                    lst.add(current.getKey());
                    final Pair<Person, Person> currentF = current;
                    current = attributions.stream()
                            .filter(p -> Objects.equals(p.getKey(), currentF.getValue()))
                            .findFirst()
                            .get();
                }
            }

            return lst.size() == size;
        }
}
