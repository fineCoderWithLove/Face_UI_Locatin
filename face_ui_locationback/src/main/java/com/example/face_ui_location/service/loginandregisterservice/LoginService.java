package com.example.face_ui_location.service.loginandregisterservice;

import com.example.face_ui_location.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginService {

    @Select("select * from student where sno = #{sno} and password = #{password}")
    Student login(int sno, String password);
}
