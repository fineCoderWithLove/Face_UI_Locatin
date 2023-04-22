package com.example.face_ui_location.domain;

import lombok.Data;

@Data
public class Student {
   private String src;
   //封装时候遇到问题，年龄,姓名,状态,全部为空？？？mybatisplus中的_s会被转义
   private Integer sno;

   private String stuName;

   private Integer stuAge;
   private String gender;

//   private Integer stuStatus;状态应该放在签到表中，因为学生和签到的关系是多对多的关系
   private int limits;
   private String password;
}
