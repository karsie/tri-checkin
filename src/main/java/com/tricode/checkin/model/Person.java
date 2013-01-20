package com.tricode.checkin.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Person implements Serializable, Cloneable {

    private static final long serialVersionUID = -43910303192875882L;

    private static final Logger log = LoggerFactory.getLogger(Person.class);

    @Id
    @GeneratedValue
    private Integer id;

    private String externalId;

    private String name;

    private String first;

    private String last;

    @Enumerated(EnumType.STRING)
    private LocationStatus status;

    private boolean eatingIn;

    private Calendar birthDate;

    private Calendar startDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String afasId) {
        this.externalId = afasId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public LocationStatus getStatus() {
        return status;
    }

    public void setStatus(LocationStatus status) {
        this.status = status;
    }

    public boolean isEatingIn() {
        return eatingIn;
    }

    public void setEatingIn(boolean eatingIn) {
        this.eatingIn = eatingIn;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", status=" + status +
                ", eatingIn=" + eatingIn +
                '}';
    }

    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("unable to clone person", e);
            return null;
        }
    }

    public static class Builder {

        private final Person person;

        private Builder(Integer id) {
            person = new Person();
            person.setId(id);
        }

        public static Builder withId(int id) {
            return new Builder(id);
        }

        public static Builder empty() {
            return new Builder(null);
        }

        public Builder withExternalId(String externalId) {
            person.setExternalId(externalId);
            return this;
        }

        public Builder withFirstName(String firstName) {
            person.setFirst(firstName);
            return this;
        }

        public Builder withLastName(String lastName) {
            person.setLast(lastName);
            return this;
        }

        public Builder withNames(String fullName, String firstName, String lastName) {
            person.setName(fullName);
            person.setFirst(firstName);
            person.setLast(lastName);
            return this;
        }

        public Builder withStatus(LocationStatus status) {
            person.setStatus(status);
            return this;
        }

        public Person get() {
            return person;
        }
    }
}
