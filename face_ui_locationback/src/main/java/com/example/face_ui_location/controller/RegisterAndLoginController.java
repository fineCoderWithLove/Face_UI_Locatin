package com.example.face_ui_location.controller;

import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.parm.RegisterTeaParms;
import com.example.face_ui_location.parm.StuLoginParm;
import com.example.face_ui_location.parm.RegisterParm;
import com.example.face_ui_location.service.loginandregisterservice.LoginService;
import com.example.face_ui_location.service.loginandregisterservice.RegiserService;
import com.example.face_ui_location.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registerandcontroller")
public class RegisterAndLoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RegiserService regiserService;
    //学生的登录
    @PostMapping("login")
    public Result login(@RequestBody StuLoginParm stuLoginParm){
       Student student = loginService.login(stuLoginParm.getSno(), stuLoginParm.getPassword());
       if(student!=null) {
           return Result.success(student);
       }
        return Result.fail(500,"账号或者密码错误！");
    }
    //学生的注册
    @PostMapping("register")
    public Result register(@RequestBody RegisterParm registerParm) throws Exception{
      int flag =  regiserService.registerStu(registerParm.getSno(),registerParm.getPassword(),registerParm.getName(),registerParm.getGender(),registerParm.getAge());
       if(flag!=0){
           return Result.success(200);
       }
       return Result.fail(500,"服务器繁忙！");
    }

    //老师的注册信息
    @PostMapping("teacherregister")
    public Result registerTeacher(@RequestBody RegisterTeaParms registerTeaParms){
       int count  = regiserService.teacherRegister(registerTeaParms.getTeaAge(),registerTeaParms.getTeaName(),registerTeaParms.getTno(),registerTeaParms.getPassword());
       if(count!=0){
           return Result.success("信息已提交，请等待审批");
       }
       return Result.fail(500,"服务器繁忙");
    }

}
