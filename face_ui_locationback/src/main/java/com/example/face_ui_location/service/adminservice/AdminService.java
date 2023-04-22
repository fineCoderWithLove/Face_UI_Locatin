package com.example.face_ui_location.service.adminservice;

import com.example.face_ui_location.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminService {
    @Select("select * from admin where name = #{name} and password = #{password}")
    Admin login(String name, String password);
}
