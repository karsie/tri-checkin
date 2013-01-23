package com.tricode.checkin.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@StaticMetamodel(WeekReport.class)
public class WeekReport_ extends UserReport_ {

    public static volatile SingularAttribute<WeekReport, Integer> year;

    public static volatile SingularAttribute<WeekReport, Integer> week;

    public static volatile ListAttribute<WeekReport, List<Long>> days;

    public static volatile ListAttribute<WeekReport, List<Boolean>> eatingIn;
}
