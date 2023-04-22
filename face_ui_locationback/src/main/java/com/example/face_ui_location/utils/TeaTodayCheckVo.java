package com.example.face_ui_location.utils;

import lombok.Data;

@Data
public class TeaTodayCheckVo {
    private Integer cno;
    private Double weidu;
    private Double jingdu;
    private String endtime;
    private String begintime;
    //允许最大签到距离
    private Integer tcno;
    private int allowmaxloc;

}
