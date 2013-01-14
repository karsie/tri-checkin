package com.tricode.checkin.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "employees")
public class XmlEmployees {
    private ArrayList<XmlEmployee> employees;

    @XmlElement(name = "employee")
    public ArrayList<XmlEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<XmlEmployee> employees) {
        this.employees = employees;
    }
}
