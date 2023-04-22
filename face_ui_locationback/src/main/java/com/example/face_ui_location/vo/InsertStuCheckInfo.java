package com.example.face_ui_location.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//全部的构造方法
public class InsertStuCheckInfo {
    //按需封装
    private String coursebegin;
    private String teaname;
    private String course;
    private String nowtime;
    private Integer sno;
    private Integer checkid;
    private Integer cno;
    private String checkdate;
    private String checklocation;
    private Double weidu;
    private Integer tcno;
    private Double jingdu;
    private Integer ischeck;
    private Integer checkinfo;

}
