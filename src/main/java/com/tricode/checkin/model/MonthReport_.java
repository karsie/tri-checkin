package com.tricode.checkin.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@StaticMetamodel(MonthReport.class)
public class MonthReport_ extends UserReport_ {

    public static volatile SingularAttribute<MonthReport, Integer> year;

    public static volatile SingularAttribute<MonthReport, Integer> month;

    public static volatile ListAttribute<MonthReport, List<Boolean>> eatingIn;
}
