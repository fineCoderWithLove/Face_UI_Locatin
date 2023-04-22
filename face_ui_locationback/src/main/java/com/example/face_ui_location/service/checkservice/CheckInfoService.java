package com.example.face_ui_location.service.checkservice;

import com.example.face_ui_location.utils.TeaTodayCheckVo;
import com.example.face_ui_location.utils.TodayCheckInfoVo;
import com.example.face_ui_location.vo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CheckInfoService {
    @Select("SELECT checkin.`checkdate`,checkin.`checklocation`,checkin.`ischeck`,checkin.`checkinfo` FROM checkin WHERE sno = #{sno} AND cno = #{cno} ")
    List<CheckInfoVo> findStuAllCheckInfo(Integer sno, Integer cno);

    @Insert("insert into teachercheckin values (null,#{jingdu},#{weidu},#{endtime},#{publishdate},#{starttime},#{cno},#{allowmaxloc})")
    int publishCheck(String starttime, Integer allowmaxloc, String endtime, String publishdate, Integer cno, Double jingdu, Double weidu);

    @Select("select courseName from course where cno = #{cno}")
    String getCourseNameBycno(Integer cno);

    @Select("select jingdu,weidu,ischeck,checkinfo,nowtime,cno,tcno,teaname,course,checklocation,coursebegin,checkid from checkin where checkdate = #{date} and sno = #{sno}")
    List<TodayCheckInfoVo> selectTodayCheck(Integer sno, String date);

    @Select("select weidu,jingdu,endtime,begintime,allowmaxloc,cno,tcno  from teachercheckin where publishdate = #{date}")
    List<TeaTodayCheckVo> findTeaTodayCheck(String date);

    @Update("update checkin set weidu = #{weidu},jingdu = #{jingdu},nowtime = #{nowtime} where checkdate = #{date} and checkid = #{checkid}")
    int updateCheckInfo(Double weidu, Double jingdu, Integer checkid, String date, String nowtime);

    //如果这么多字段都一样，只能说明老师手滑了多点了几次，所以查出一项就行
    @Select("select * from  teachercheckin where begintime = #{begintime} and allowmaxloc = #{allowmaxloc} and endtime = #{endtime} and publishdate = #{publishdate} and weidu = #{jingdu} and jingdu = #{weidu} limit 1")
    GetPublishCheckVo getPublishCheck(String begintime, Integer allowmaxloc, String endtime, String publishdate, Integer cno, double jingdu, double weidu);

    @Select("select begintime from teachercheckin where tcno = #{tcno}")
    String selectBeginTime(Integer tcno);

    @Insert("insert into checkin values (#{begintime},#{teacherName},#{courseName},null,#{sno},null,#{cno},#{courseDate},#{location},null,#{tcno},null,0,2)")
    int insertCheckInfo(String begintime, String teacherName, String courseName, Integer sno, Integer cno, String courseDate, String location, Integer tcno);

    @Select("SELECT stuName FROM student WHERE sno IN (SELECT sno FROM checkin WHERE checkdate = #{todaytime} AND cno = #{cno} ) \n")
    List<String> selectAllTodayCheckInfo(String todaytime, Integer cno);

    @Select("SELECT student.`sno`,student.`stuName`,checkin.`ischeck`,checkin.`checkinfo` FROM student INNER JOIN checkin ON student.`sno` = checkin.`sno` WHERE checkin.`checkdate` = #{todaytime} and checkin.cno = #{cno}")
    List<AllTodayCheckInfoVo> selectCheckInInfo(Integer cno, String todaytime);

    @Select("SELECT student.`sno`,student.`stuName`,checkin.`ischeck`,checkin.`checkinfo` FROM student INNER JOIN checkin ON student.`sno` = checkin.`sno` WHERE checkin.`checkdate` = #{todaytime} and checkin.cno = #{cno} and ischeck = 1 ")
    List<AllTodayCheckInfoVo> findisCheckInfo(Integer cno, String todaytime);

    @Select("SELECT student.`sno`,student.`stuName`,checkin.`ischeck`,checkin.`checkinfo` FROM student INNER JOIN checkin ON student.`sno` = checkin.`sno` WHERE checkin.`checkdate` = #{todaytime} and checkin.cno = #{cno} and ischeck = 0 and checkinfo = 2")
    List<AllTodayCheckInfoVo> findNoCheckStu(Integer cno, String todaytime);

    @Select("SELECT teaname,course,checklocation,checkdate,ischeck,checkinfo FROM checkin WHERE sno = #{sno}")
    List<HistoryCheckInfoVo> findHistroyCheck(Integer sno);
    //查询没有签到的的且学号是。。的且是今天的。。
    @Select("SELECT COUNT(*) FROM checkin WHERE ischeck = 0 AND sno = #{sno} and checkdate = #{checkdate}")
    Integer selectCountOfNoCheck(Integer sno,String checkdate);
}
