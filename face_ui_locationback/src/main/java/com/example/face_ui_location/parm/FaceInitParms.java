package com.example.face_ui_location.parm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FaceInitParms {
    private String imageBase;
    /**
     * 插入时间
     */

    private String createTime;
    private String name;


}
