package fr.mleduc.giftproblem.core.domain;

import javax.json.bind.annotation.JsonbTransient;

public class Person {
    private String name;

    @JsonbTransient
    private Family family;
    private boolean on24;
    private boolean on25;
    private String mail;

    public Person() {
        this.family = null;
        this.on24 = true;
        this.on25 = true;
        this.mail = null;
    }

    public Person(String name, Family family, String mail) {
        this(name, family, true, true, mail);
    }

    public Person(String name, Family family, boolean on24, boolean on25, String mail) {
        this.name = name;
        this.family = family;
        this.family.getPeople().add(this);
        this.on24 = on24;
        this.on25 = on25;
        this.mail = mail.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isOn24() {
        return on24 && this.family.isOn24();
    }

    public void setOn24(boolean on24) {
        this.on24 = on24;
    }

    public boolean isOn25() {
        return on25 && this.family.isOn25();
    }

    public void setOn25(boolean on25) {
        this.on25 = on25;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
