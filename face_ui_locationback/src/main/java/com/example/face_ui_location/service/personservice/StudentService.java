package com.example.face_ui_location.service.personservice;

import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.parm.InsertCourseParms;
import com.example.face_ui_location.vo.StuCourseInfoVo;
import com.example.face_ui_location.vo.StuDetailsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentService {
    @Select("select * from student ")
    List<Student> findAllStu();


    @Select("SELECT a.courseName,a.location,a.courseDate,b.teaName,a.cno FROM (\n" +
            "\t\tSELECT * FROM course WHERE cno IN \n" +
            "\t\t\t(SELECT cno FROM stutocourse WHERE sno = #{sno})\n" +
            "\t) AS a ,\n" +
            "\t(\n" +
            "\tSELECT * FROM teacher WHERE tno IN \n" +
            "\t(SELECT tno FROM stutocourse WHERE sno = #{sno})\n" +
            "\t) AS b WHERE a.tno = b.tno")
    List<StuCourseInfoVo> findStuCourseInfo(Integer sno);

    @Insert("INSERT INTO stutocourse VALUES (#{mysno},#{cno})")
    int InsertCourse(Integer cno, Integer mysno);

    @Select("select * from stutocourse where cno = #{cno} and sno=#{mysno} limit 1")
    InsertCourseParms findOne(Integer mysno, Integer cno);

    @Select("SELECT src,sno,stuName,stuAge,gender FROM student WHERE sno = #{sno}")
    StuDetailsVo findStuDetails(Integer sno);

    @Update("update checkin set ischeck  = 1 where sno = #{sno} and checkid = #{checkid} and checkdate = #{date}")
    int updateStuIscheck(Integer sno, Integer checkid, String date);

    @Update("update checkin set checkinfo = 0 where checkid = #{checkid}  ")
    int updatecheckinfo(Integer sno, Integer checkid, String date);

    @Select("SELECT sno FROM stutocourse WHERE cno = (SELECT  cno FROM teachercheckin WHERE tcno = #{tcno})")
    List<Integer> findSnoInCourse(Integer tcno);

    @Update("update student set src = #{src} where sno = #{sno}")
    boolean updateStuImage(Integer sno, String src);

    @Select("SELECT * FROM student WHERE sno IN (\n" +
            "SELECT sno FROM stutocourse WHERE cno = #{cno})\n")
    List<Student> findCourseStu(Integer cno);
}
