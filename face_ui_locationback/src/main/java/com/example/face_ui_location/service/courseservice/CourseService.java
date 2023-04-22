package com.example.face_ui_location.service.courseservice;

import com.example.face_ui_location.vo.AllCourseInfo;
import com.example.face_ui_location.vo.CourseInfoVo;
import com.example.face_ui_location.vo.StuCourseInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseService {
    @Select("SELECT * FROM teacher,course WHERE teacher.tno = course.tno")
    List<AllCourseInfo> findAllCourseInfo();

    @Select("select courseName,location,courseDate from course where cno = #{cno}")
    CourseInfoVo seleCourseInfo(Integer cno);
    @Insert("insert into course values (null,#{courseName},#{location},#{courseDate},#{tno})")
    void insertCourseInfo(String courseName, String courseDate, Integer tno, String location);
}
