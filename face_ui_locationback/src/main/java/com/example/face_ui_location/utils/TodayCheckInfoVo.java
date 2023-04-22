package com.example.face_ui_location.utils;

import lombok.Data;

@Data
public class TodayCheckInfoVo {
    //一下为数据库字段名称
    private Integer checkid;
    private String teaname;
    private String course;
    private Double weidu;
    private Double jingdu;
    private Integer cno;
    private Integer ischeck;
    private Integer checkinfo;
    private String checklocation;
    private String nowtime;
    private Integer tcno;
    private String coursebegin;

    //以下为信息可读性来设置的字段
    //这是一个笨方法仅仅是为了简单，一下为展示信息内容，不存入库中只返回前端作为判断
    private String ischeckstring;
    private String checkinfostring;

}
