package fr.mleduc.giftproblem.core.domain;

import javax.json.bind.annotation.JsonbTransient;

public class Person {
    private String name;

    @JsonbTransient
    private Family family;
    private Boolean on24;
    private Boolean on25;
    private String mail;

    public Person() {
        this.family = null;
        this.on24 = null;
        this.on25 = null;
        this.mail = null;
    }

    public Person(String name, Family family, String mail) {
        this(name, family, null, null, mail);
    }

    public Person(String name, Family family, Boolean on24, Boolean on25, String mail) {
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
        if (on24 != null) return on24;
        else return this.family.isOn24();
    }

    public void setOn24(boolean on24) {
        this.on24 = on24;
    }

    public boolean isOn25() {
        if (on25 != null) return on25;
        else return this.family.isOn25();
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
