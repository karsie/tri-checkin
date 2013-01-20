package com.tricode.checkin.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StatusChangeLog.class)
public class StatusChangeLog_ extends Log_ {

    public static volatile SingularAttribute<StatusChangeLog, LocationStatus> statusFrom;

    public static volatile SingularAttribute<StatusChangeLog, LocationStatus> statusTo;
}
