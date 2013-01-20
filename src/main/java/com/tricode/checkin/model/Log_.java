package com.tricode.checkin.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Log.class)
public class Log_ {

    public static volatile SingularAttribute<Log, Integer> id;

    public static volatile SingularAttribute<Log, Integer> userId;

    public static volatile SingularAttribute<Log, Long> timestamp;
}
