package com.tricode.checkin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = -43910303192875882L;

    @Id
    @GeneratedValue
    private Integer id;

    private String externalId;

    private String name;

    private String first;

    private String last;

    @Enumerated(EnumType.STRING)
    private LocationStatus status;

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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", status=" + status +
                '}';
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
