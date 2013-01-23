package com.tricode.checkin.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserReport.class)
public class UserReport_ {

    public static volatile SingularAttribute<UserReport, Integer> id;

    public static volatile SingularAttribute<UserReport, Integer> userId;
}
