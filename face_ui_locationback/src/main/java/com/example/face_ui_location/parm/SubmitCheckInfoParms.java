package com.example.face_ui_location.parm;

import lombok.Data;

//接收前端传递过来的签到信息
@Data
public class SubmitCheckInfoParms {
    private Integer checkid;
    private Double jingdu;
    private Double weidu;
    private Integer sno;
    private Integer cno;
}
