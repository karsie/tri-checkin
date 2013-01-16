package com.tricode.checkin.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StatusChangeLog.class)
public class StatusChangeLog_ {

    public static volatile SingularAttribute<StatusChangeLog, Integer> id;

    public static volatile SingularAttribute<StatusChangeLog, Integer> userId;

    public static volatile SingularAttribute<StatusChangeLog, LocationStatus> statusFrom;

    public static volatile SingularAttribute<StatusChangeLog, LocationStatus> statusTo;

    public static volatile SingularAttribute<StatusChangeLog, Long> timestamp;
}
