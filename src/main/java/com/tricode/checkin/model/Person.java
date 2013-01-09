package com.tricode.checkin.model;

public class Person {

    private Integer id;
    private String name;
    private String first;
    private String last;
    private LocationStatus status;
    private boolean added;
    private long signInTime;

    public Person() {
    }

    public Person(Integer id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.name = first + " " + last;
        this.status = LocationStatus.OUT;
        this.added = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public long getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(long signInTime) {
        this.signInTime = signInTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", status=" + status +
                ", added=" + added +
                ", signInTime=" + signInTime +
                '}';
    }
}
