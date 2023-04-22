package com.example.face_ui_location.controller;

import com.example.face_ui_location.domain.Student;
import com.example.face_ui_location.mapper.Stumapper;
import com.example.face_ui_location.parm.InsertCourseParms;
import com.example.face_ui_location.service.personservice.StudentService;
import com.example.face_ui_location.vo.Result;
import com.example.face_ui_location.vo.StuCourseInfoVo;
import com.example.face_ui_location.vo.StuDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private Stumapper stumapper;


    //要在管理员页面上展示所有的学生信息

    @PostMapping("allstudent")
    public Result findAllStu(){
       List<Student> studentList =  studentService.findAllStu();
        System.out.println(studentList);
       return Result.success(studentList);
    }
    //返回学生列表的课程信息
    @GetMapping("courseinfo")
    public  Result findMyCourseInfo(Integer sno){
       List<StuCourseInfoVo> stuCourseInfoVos =  studentService.findStuCourseInfo(sno);
        return Result.success(stuCourseInfoVos);
    }
    //学生选课的controller
    @PostMapping("selectstucourse")
    public Result selectStuCourse(@RequestBody InsertCourseParms insertCourseParms){
        System.out.println(insertCourseParms.getCno()+":"+insertCourseParms.getSno());
        //就是把课程信息添加到学生的个人课程列表里面，只需要学生的唯一标识，还需要课程的id，剩下都是不太需要的信息，重新封装params
        int flag = studentService.InsertCourse(insertCourseParms.getCno(),insertCourseParms.getSno());
        //重新查询出来一个做返回
        if(flag!=0){
            InsertCourseParms back = studentService.findOne(insertCourseParms.getSno(),insertCourseParms.getCno());
            return Result.success(back);
        }
        return Result.fail(200,"服务器繁忙");
    }

    //查询学生的详情信息
    @GetMapping("studetails")
    public Result selectStuDetails(Integer sno){
         StuDetailsVo stuDetailsVo =  studentService.findStuDetails(sno);
         return Result.success(stuDetailsVo);

    }

}
