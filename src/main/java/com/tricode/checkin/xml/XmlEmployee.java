package com.tricode.checkin.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class XmlEmployee {
    private String name;
    private String id;
    private String status;
    private String lastSignIn;
    private String lastSignOut;
    private String phone;

    @XmlAttribute(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "afasId", required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(required = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(String lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public String getLastSignOut() {
        return lastSignOut;
    }

    public void setLastSignOut(String lastSignOut) {
        this.lastSignOut = lastSignOut;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
