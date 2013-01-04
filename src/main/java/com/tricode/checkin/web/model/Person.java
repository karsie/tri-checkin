package com.tricode.checkin.web.model;

public class Person {

    private String id;
    private String name;
    private String first;
    private String last;
    private LocationStatus status;
    private boolean added;

    public Person() {
    }
    
    public Person(String id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.name = first + " " + last;
        this.status = LocationStatus.OUT;
        this.added = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
