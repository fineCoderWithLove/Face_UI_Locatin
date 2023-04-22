package com.example.face_ui_location.service.personservice;

import com.example.face_ui_location.domain.Course;
import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherService {

    @Select("select * from teacher ")
    List<Teacher> findAllTea();

    @Select("select * from teacher where tno = #{tno} and password = #{password}")
    Teacher login(Integer tno, String password);

    @Select("select * from course where tno = #{tno}")
    List<Course> findTeacherCourse(Integer tno);

    //根据课程标号来查学生
    @Select("SELECT * FROM student WHERE sno IN (SELECT sno FROM stutocourse WHERE cno = #{cno})")
    List<Student> findCourseStu(Integer cno);

    @Select("SELECT teaName FROM teacher WHERE tno = (SELECT tno FROM course WHERE cno =  #{cno})")
    String selectTeaName(Integer cno);

    @Update("update teacher set isregister = 1  where tno = #{tno} ")
    int updateIsRegister(Integer tno);

    @Select("select count(*) from teacher where isregister != 1")
    Integer getNoChecKTeacherCount();
}
