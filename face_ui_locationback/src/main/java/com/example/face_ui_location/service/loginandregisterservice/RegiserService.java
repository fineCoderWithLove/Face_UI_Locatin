package com.example.face_ui_location.service.loginandregisterservice;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegiserService {
    @Insert("insert into student  values (null,#{sno},#{name},#{age},#{gender},0,#{password})")
    int registerStu(Integer sno, String password, String name, String gender, Integer age);

    @Insert("insert into teacher values (0,#{password},#{tno},#{teaName},#{teaAge})")
    int teacherRegister(Integer teaAge, String teaName, Integer tno, String password);
}
