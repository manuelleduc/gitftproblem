package fr.mleduc.giftproblem.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Family {
    private String name;
    private List<Person> people = new ArrayList<>();
    private boolean on24;
    private boolean on25;

    public Family() {
        this.name = null;
        this.on24 = true;
        this.on25 = true;
    }

    public Family(String name, boolean on24, boolean on25) {
        this.name = name;
        this.on24 = on24;
        this.on25 = on25;
    }

    public String getName() {
        return name;
    }

    public List<Person> getPeople() {
        return people;
    }

    public boolean isOn24() {
        return on24;
    }

    public boolean isOn25() {
        return on25;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPeople(List<Person> people) {
        people.forEach(personne -> personne.setFamily(this));
        this.people = people;
    }

    public void setOn24(boolean on24) {
        this.on24 = on24;
    }

    public void setOn25(boolean on25) {
        this.on25 = on25;
    }
}
