package com.example.face_ui_location.controller;

import com.example.face_ui_location.domain.Admin;
import com.example.face_ui_location.domain.Course;
import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.domain.Teacher;
import com.example.face_ui_location.parm.AdminLogin;
import com.example.face_ui_location.service.adminservice.AdminService;
import com.example.face_ui_location.service.checkservice.CheckInfoService;
import com.example.face_ui_location.service.personservice.StudentService;
import com.example.face_ui_location.service.personservice.TeacherService;
import com.example.face_ui_location.vo.HistoryCheckInfoVo;
import com.example.face_ui_location.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private CheckInfoService checkInfoService;

    //管理员的登录接口
    @PostMapping("adminlogin")
    public Result login(@RequestBody AdminLogin adminLogin) {
        Admin admin = adminService.login(adminLogin.getName(), adminLogin.getPassword());
        if (admin != null) {
            return Result.success(admin);
        }
        return Result.fail(500, "服务器繁忙");
    }

    //查询所有老师的信息，包括注册信息
    @GetMapping("allteacher")
    public Result selectAllTeacher() {
        List<Teacher> teacherList = teacherService.findAllTea();
        if (teacherList != null) {
            return Result.success(teacherList);
        }
        return Result.fail(500, "服务器繁忙");
    }

    //查询老师详情信息
    @GetMapping("teacherdetail")
    public Result selectTeacherDetail(Integer tno) {
        List<Course> courseList = teacherService.findTeacherCourse(tno);
        if (courseList != null) {
            return Result.success(courseList);
        }
        return Result.fail(500, "服务器繁忙");
    }
    //查询课程对应的学生信息
    @GetMapping("coursestulist")
    public Result selectCourseStuList(Integer cno){
        List<Student> studentList = studentService.findCourseStu(cno);
        if(studentList!=null){
            return Result.success(studentList);
        }
        return Result.fail(500,"服务器繁忙");
    }
    //查询对应的签到信息
    @GetMapping("checkinfo")
    public Result HistoryCheckInfo(Integer sno){
        List<HistoryCheckInfoVo> historyCheckInfoVos = checkInfoService.findHistroyCheck(sno);
        if(historyCheckInfoVos!=null){
            return Result.success(historyCheckInfoVos);
        }
        return Result.fail(500,"服务器繁忙");
    }
    //批准老师isregister
    @GetMapping("passregister")
    public Result passRegister(Integer tno){
        System.out.println("this is a "+tno);
      int flag =   teacherService.updateIsRegister(tno);
      if(flag>0){
          return Result.success("该老师成功注册");
      }
      return Result.fail(500,"服务器繁忙");
    }
}
