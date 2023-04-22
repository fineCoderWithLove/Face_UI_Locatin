package com.example.face_ui_location.vo;

import lombok.Data;

@Data
public class HistoryCheckInfoVo {
    private String teaname;
    private String course;
    private String checklocation;
    private String checkdate;
    private Integer ischeck;
    private Integer checkinfo;

    //以下为信息可读性来设置的字段
    //这是一个笨方法仅仅是为了简单，一下为展示信息内容，不存入库中只返回前端作为判断
    private String ischeckstring;
    private String checkinfostring;

}
