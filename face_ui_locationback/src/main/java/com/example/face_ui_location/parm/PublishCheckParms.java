package com.example.face_ui_location.parm;

import lombok.Data;

import java.util.Date;

@Data
public class PublishCheckParms {
    private String endtime;
    private String begintime;
    private Integer allowmaxloc;
    private String publishdate;
    private Integer cno;
    private double jingdu;
    private double weidu;
}
