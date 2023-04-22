package com.example.face_ui_location.controller;

import com.example.face_ui_location.domain.Course;
import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.domain.Teacher;
import com.example.face_ui_location.mapper.TeaMapper;
import com.example.face_ui_location.parm.StuLoginParm;
import com.example.face_ui_location.parm.TeaLoginParm;
import com.example.face_ui_location.service.personservice.TeacherService;
import com.example.face_ui_location.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeaMapper teaMapper;

    //要在管理员页面下展示所有的老师信息
    @PostMapping("allteacher")
    public Result findAllTea() {
        List<Teacher> teacherList = teacherService.findAllTea();
        return Result.success(teacherList);
    }

    //问题：老师需要注册吗还是管理员分发账号
    //老师的登录。
    @PostMapping("teacherlogin")
    public Result teacherlogin(@RequestBody TeaLoginParm teaLoginParm) {
        Teacher teacher = teacherService.login(teaLoginParm.getTno(), teaLoginParm.getPassword());
        if (teacher != null) {
            return Result.success(teacher);
        }
        return Result.fail(500, "服务器繁忙");
    }

    //返回老师的课程信息
    @GetMapping("teachercourse")
    public Result teacherCourse(Integer tno){
     List<Course>  courses =   teacherService.findTeacherCourse(tno);
     if(courses!=null){
         return Result.success(courses);
     }
     return Result.fail(500,"服务器繁忙");
    }
    //通过课程tno来返回学生列表集合
    @GetMapping("coursestulist")
    public Result coursestulist(Integer cno){
        List<Student> studentList = teacherService.findCourseStu(cno);
        if(studentList!=null){
            return Result.success(studentList);
        }
        return Result.fail(500,"服务器繁忙");
    }
    //返回所有未审批的老师数量
    @GetMapping("nocheckteachercount")
    public Result getNoChecKTeacherCount(){
        Integer count = teacherService.getNoChecKTeacherCount();
        return Result.success(count);
    }
}
