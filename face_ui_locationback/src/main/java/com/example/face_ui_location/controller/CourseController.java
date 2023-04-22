package com.example.face_ui_location.controller;


import com.example.face_ui_location.parm.CourseParms;
import com.example.face_ui_location.service.courseservice.CourseService;
import com.example.face_ui_location.vo.AllCourseInfo;
import com.example.face_ui_location.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //仅仅查询所有的课程
    @GetMapping("allcourse")
    public Result findAllCourse(){
     List<AllCourseInfo> courses =  courseService.findAllCourseInfo();
     return  Result.success(courses);
    }

    //老师提交老师的课程信息
    @PostMapping("submitcourse")
    public Result submitCourseInfo(@RequestBody CourseParms courseParms){
        System.out.println(courseParms);
        courseService.insertCourseInfo(courseParms.getCourseName(),courseParms.getCourseDate(),courseParms.getTno(),courseParms.getLocation());
        return Result.success(200);
    }
}
