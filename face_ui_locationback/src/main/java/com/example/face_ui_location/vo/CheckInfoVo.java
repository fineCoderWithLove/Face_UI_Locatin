package com.example.face_ui_location.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CheckInfoVo {
    private Date checkdate;
    //目前为字符串类型，稍后需要改变
    private String checklocation;
    private Integer ischeck;
    private Integer checkinfo;


    //以下为信息可读性来设置的字段
    //这是一个笨方法仅仅是为了简单，一下为展示信息内容，不存入库中只返回前端作为判断
    private String ischeckstring;
    private String checkinfostring;


}
