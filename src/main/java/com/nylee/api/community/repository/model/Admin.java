package com.nylee.api.community.repository.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Admin {

    private Integer idx;
    private String  id;
    private String  pwd;
    private Integer level;
    private String  rgdt;
    private String  userName;
    private String  userPhone;
    private String  userEmail;
    private String  lastUpdt;
    private Integer lastUpidx;
    private Integer rgidx;
    private String  encKey;
    private String  lastActionTime;

    //extends
    private String  rgid;
    private String  lastUpid;
}
