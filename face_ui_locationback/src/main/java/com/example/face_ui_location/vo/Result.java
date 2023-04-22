package com.example.face_ui_location.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//全部的构造方法
public class Result {
    private boolean success;//是否成功
    private int code;//状态码
    private String msg;//信息
    private Object data;//数据
    public  static  Result success(Object data){
        return new Result(true,200,"success",data);
    }
    public  static  Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
