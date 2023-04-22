package com.example.face_ui_location.controller;

import com.example.face_ui_location.parm.PublishCheckParms;
import com.example.face_ui_location.parm.SubmitCheckInfoParms;
import com.example.face_ui_location.service.checkservice.CheckInfoService;
import com.example.face_ui_location.service.courseservice.CourseService;
import com.example.face_ui_location.service.personservice.StudentService;
import com.example.face_ui_location.service.personservice.TeacherService;
import com.example.face_ui_location.utils.*;
import com.example.face_ui_location.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("checkinfo")
public class CheckInfoController {
    @Autowired
    private CheckInfoService checkInfoService;

    @Autowired
    private GetSystemTimeUtils getSystemTimeUtils;

    @Autowired
    private LocationUtils locationUtils;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;


    //获取学生的签到信息
    @GetMapping("stuallcheckinfo")
    public Result getStuAllCheckService(Integer sno, Integer cno) {
        //签到的业务逻辑是十分复杂的到时候这个controller是需要改的
        //controller处理数据checkinfo为签到信息，0迟到，1早退，2为正常，ischeck，0未签，1签到
        List<CheckInfoVo> checkInfoVos = checkInfoService.findStuAllCheckInfo(sno, cno);
        for (CheckInfoVo checkInfoVo : checkInfoVos) {
            if (checkInfoVo.getCheckinfo() == 0) {
                checkInfoVo.setCheckinfostring("签到过期");
            }
            if (checkInfoVo.getIscheck() == 0) {
                checkInfoVo.setIscheckstring("未签");
            } else {
                checkInfoVo.setIscheckstring("已签");
            }
        }
        if (checkInfoVos != null) {
            return Result.success(checkInfoVos);
        } else {
            return Result.fail(500, "该学生从来没有签到");
        }

    }

    //1发布签到信息,暂时将签到信息存入数据库中
    //2.在发布签到的时候应该把选这个课的学生集合遍历出来，然后要封装成一个大参数存进checkin表中
    @PostMapping("publishcheck")
    public Result publishCheck(@RequestBody PublishCheckParms publishCheckParms) {
        String publishdate = getSystemTimeUtils.getSystemTime();
        publishCheckParms.setPublishdate(publishdate);
        System.out.println(publishCheckParms);
        int flag = checkInfoService.publishCheck(publishCheckParms.getBegintime(), publishCheckParms.getAllowmaxloc(), publishCheckParms.getEndtime(), publishCheckParms.getPublishdate(), publishCheckParms.getCno(), publishCheckParms.getJingdu(), publishCheckParms.getWeidu());
        //先取出刚才的集合信息。
        GetPublishCheckVo getPublishCheckVo = checkInfoService.getPublishCheck(publishCheckParms.getBegintime(), publishCheckParms.getAllowmaxloc(), publishCheckParms.getEndtime(), publishCheckParms.getPublishdate(), publishCheckParms.getCno(), publishCheckParms.getJingdu(), publishCheckParms.getWeidu());
        //根据刚才的信息查询出所有属于这个课程的学生（根据唯一的tcno来查询唯一课程信息）
       List<Integer> sno =  studentService.findSnoInCourse(getPublishCheckVo.getTcno());
        //封装信息,其实还在查询。
        //根据tcno查询出来begintime
        String begintime = checkInfoService.selectBeginTime(getPublishCheckVo.getTcno());
        System.out.println("封装的begintime"+begintime);
        //通过cno来查询课程信息
        CourseInfoVo courseInfoVo = courseService.seleCourseInfo(getPublishCheckVo.getCno());
        System.out.println("封装的courseInfoVo"+courseInfoVo);
        //根据cno查询老师tno,根据tno查询老师姓名
        String TeacherName = teacherService.selectTeaName(getPublishCheckVo.getCno());
        System.out.println("老师姓名是"+TeacherName);
        //对数据进行统一封装
        String date = getSystemTimeUtils.getSystemTime();
        for (Integer integer : sno) {
            checkInfoService.insertCheckInfo(begintime,TeacherName,courseInfoVo.getCourseName(),integer,getPublishCheckVo.getCno(),date,courseInfoVo.getLocation(),getPublishCheckVo.getTcno());
        }
        //查出学号之后循环往进insert信息，
        System.out.println(getPublishCheckVo);
        if (flag != 0) {
            return Result.success(200);
        }
        return Result.fail(500, "服务器繁忙");
    }

    //获取课程的名字
    @GetMapping("getcoursename")
    public Result getCourseName(Integer cno) {
        String CourseName = checkInfoService.getCourseNameBycno(cno);
        return Result.success(CourseName);
    }

    //    通过学生的学号和课程的编号查询今日课程的签到表,这个接口处理好了签到的信息。只是处理好签到的信息
    @GetMapping("getstucheckinfo")
    public Result getStuCheckInfo(Integer cno, Integer sno) {
        //获取当天的信息
        String date = getSystemTimeUtils.getSystemTime();
        //查询所有签到信息比对出当天的信息,查询的结果应该是签到的状态，并且是当天的签到信息。
        List<TodayCheckInfoVo> todayCheckInfoVos = checkInfoService.selectTodayCheck(sno, date);//改查询信息没有问题
        //查询当日所有老师发布的签到信息，应该得到一个集合，然后两层for循环嵌套
        List<TeaTodayCheckVo> teaTodayCheckVos = checkInfoService.findTeaTodayCheck(date);//只要date嘛
        for (TodayCheckInfoVo todayCheckInfoVo : todayCheckInfoVos) {
            for (TeaTodayCheckVo teaTodayCheckVo : teaTodayCheckVos) {
            }

        }
        //重新查询修改过的信息，再对结果进行处理返回到前端
        List<TodayCheckInfoVo> todayCheckInfoVosSecond = checkInfoService.selectTodayCheck(sno, date);
        for (TodayCheckInfoVo todayCheckInfoVo : todayCheckInfoVosSecond) {
            if (todayCheckInfoVo.getCheckinfo() == 0) {
                todayCheckInfoVo.setCheckinfostring("签到过期");
            }
            if (todayCheckInfoVo.getIscheck() == 0) {
                todayCheckInfoVo.setIscheckstring("未签");
            } else {
                todayCheckInfoVo.setIscheckstring("已签");
            }
        }
        return Result.success(todayCheckInfoVosSecond);
    }

    //处理学生交上来的表单
    @PostMapping("submitcheckinfo")
    public Result SubmitCheckInfo(@RequestBody SubmitCheckInfoParms submitCheckInfoParms) {
        System.out.println("我封装的checkid------------------------------------------------------" + submitCheckInfoParms.getCheckid());
        if (submitCheckInfoParms.getJingdu() == 0 || submitCheckInfoParms.getWeidu() == 0) {
            return Result.fail(500, "服务器繁忙请重试！");
        }
        //获取系统当前时间
        String date = getSystemTimeUtils.getSystemTime();
        //获取系统小时分钟
        String hourAndMin = getSystemTimeUtils.getHourAndSecond();
        int flag = checkInfoService.updateCheckInfo(submitCheckInfoParms.getWeidu(), submitCheckInfoParms.getJingdu(), submitCheckInfoParms.getCheckid(), date, hourAndMin);
        //查询所有签到信息比对出当天的信息,查询的结果应该是签到的状态，并且是当天的签到信息。
        List<TodayCheckInfoVo> todayCheckInfoVos = checkInfoService.selectTodayCheck(submitCheckInfoParms.getSno(), date);//改查询信息没有问题
        //查询当日所有老师发布的签到信息，应该得到一个集合，然后两层for循环嵌套
        List<TeaTodayCheckVo> teaTodayCheckVos = checkInfoService.findTeaTodayCheck(date);//只要date嘛
        int i = 0;
        for (TodayCheckInfoVo todayCheckInfoVo : todayCheckInfoVos) {
            for (TeaTodayCheckVo teaTodayCheckVo : teaTodayCheckVos) {
                //双重循环查找学生所有今天的签到信息和老师发表的签到信息，如果课程的信息对的上，那就是同一个签到信息。
                if (todayCheckInfoVo.getCno() == teaTodayCheckVo.getCno()) {
                    //如果通过位置判断就update数据库中的ischeck信息说明已经签到,要传递四个参数分别是对应的经纬度.
                    if (locationUtils.ischeckinlocation(todayCheckInfoVo.getWeidu(), todayCheckInfoVo.getJingdu(), teaTodayCheckVo.getWeidu(), teaTodayCheckVo.getJingdu(), teaTodayCheckVo.getAllowmaxloc())) {
                        //在此处进行update操作,更新操作为修改他的ischeck信息
                        System.out.println("-----------------------------------------------"+todayCheckInfoVo.getWeidu());
                        System.out.println("-----------------------------------------------"+todayCheckInfoVo.getJingdu());
                        System.out.println("-----------------------------------------------"+teaTodayCheckVo.getWeidu());
                        System.out.println("-----------------------------------------------"+teaTodayCheckVo.getJingdu());
                        System.out.println("-----------------------------------------------"+teaTodayCheckVo.getAllowmaxloc());
                        studentService.updateStuIscheck(submitCheckInfoParms.getSno(), submitCheckInfoParms.getCheckid(), date);//此处的cno是由前端传递过来的
                        System.out.println("影响的行数为" + flag);
                        //flag！=0说明没有进行修改，也就是还没有到签到的范围，也就是不需要判断时间
                        //已经签到才能判断是不是迟到或者早退。如果不按时签到就视为迟到,老师发签到表的时候默认ischeck都是0->代表未签到状态，然后学生进行修改，如果学生的位置都不在Area那就不用判断签到的时间了
                        if (submitCheckInfoParms.getCno() == teaTodayCheckVo.getCno()) {
                            i++;
                            System.out.println("第" + i + "次比对:" + todayCheckInfoVo.getTcno() + ":" + teaTodayCheckVo.getTcno());
                            if (!getSystemTimeUtils.isOntime(teaTodayCheckVo.getBegintime(), teaTodayCheckVo.getEndtime(), todayCheckInfoVo.getNowtime())) {
                                //更新迟到的操作,就是更新checkinfo的信息。
                                System.out.println("-----------------------------------------------------------------------------------------------信息被更新了");
                                //bug就是这个checkid传进去成null值了
                                int issuccess = studentService.updatecheckinfo(submitCheckInfoParms.getSno(), submitCheckInfoParms.getCheckid(), date);
                            }
                        }
                    }
                }
            }

        }
        List<TodayCheckInfoVo> todayCheckInfoVosSecond = checkInfoService.selectTodayCheck(submitCheckInfoParms.getSno(), date);
        for (TodayCheckInfoVo todayCheckInfoVo : todayCheckInfoVosSecond) {
            if (todayCheckInfoVo.getCheckinfo() == 0) {
                todayCheckInfoVo.setCheckinfostring("签到过期");
            }
            if (todayCheckInfoVo.getIscheck() == 0) {
                todayCheckInfoVo.setIscheckstring("未签");
            } else {
                todayCheckInfoVo.setIscheckstring("已签");
            }
        }
        System.out.println(submitCheckInfoParms);
        return Result.success(todayCheckInfoVosSecond);

    }
    //根据传来的课程编号来查找今天所有的学生签到信息
    @GetMapping("getcoursetodaycheckinfo")
    public Result getCourseTodayCheckInfo(Integer cno){
        //这一快的信息只需要学号，姓名和签到状态就行了。
        String todaytime = getSystemTimeUtils.getSystemTime();
        List<AllTodayCheckInfoVo> allTodayCheckInfoVos = checkInfoService.selectCheckInInfo(cno,todaytime);
        for (AllTodayCheckInfoVo allTodayCheckInfoVo : allTodayCheckInfoVos) {
            if (allTodayCheckInfoVo.getCheckinfo() == 0) {
                allTodayCheckInfoVo.setCheckinfostring("签到过期");
            }
            if (allTodayCheckInfoVo.getIscheck() == 0) {
                allTodayCheckInfoVo.setIscheckstring("未签");
            } else {
                allTodayCheckInfoVo.setIscheckstring("已签");
            }
        }
        if(allTodayCheckInfoVos!=null){
            return Result.success(allTodayCheckInfoVos);
        }
       return Result.fail(500,"服务器繁忙");
    }
    //获取签到成功的签到
    @GetMapping("getischeckinfo")
    public Result getIscheckInfo(Integer cno){
        //这一快的信息只需要学号，姓名和签到状态就行了。
        String todaytime = getSystemTimeUtils.getSystemTime();
        List<AllTodayCheckInfoVo> allTodayCheckInfoVos = checkInfoService.findisCheckInfo(cno,todaytime);
        for (AllTodayCheckInfoVo allTodayCheckInfoVo : allTodayCheckInfoVos) {
            if (allTodayCheckInfoVo.getCheckinfo() == 0) {
                allTodayCheckInfoVo.setCheckinfostring("签到过期");
            }
            if (allTodayCheckInfoVo.getIscheck() == 0) {
                allTodayCheckInfoVo.setIscheckstring("未签");
            } else {
                allTodayCheckInfoVo.setIscheckstring("已签");
            }
        }
        if(allTodayCheckInfoVos!=null){
            return Result.success(allTodayCheckInfoVos);
        }
        return Result.fail(500,"服务器繁忙");
    }
    @GetMapping("getnocheckstu")
    public Result getNoCheckInfo(Integer cno){
        //这一快的信息只需要学号，姓名和签到状态就行了。
        String todaytime = getSystemTimeUtils.getSystemTime();
        List<AllTodayCheckInfoVo> allTodayCheckInfoVos = checkInfoService.findNoCheckStu(cno,todaytime);
        for (AllTodayCheckInfoVo allTodayCheckInfoVo : allTodayCheckInfoVos) {
            if (allTodayCheckInfoVo.getCheckinfo() == 0) {
                allTodayCheckInfoVo.setCheckinfostring("签到过期");
            }
            if (allTodayCheckInfoVo.getIscheck() == 0) {
                allTodayCheckInfoVo.setIscheckstring("未签");
            } else {
                allTodayCheckInfoVo.setIscheckstring("已签");
            }
        }
        if(allTodayCheckInfoVos!=null){
            return Result.success(allTodayCheckInfoVos);
        }
        return Result.fail(500,"服务器繁忙");
    }
    //请求今天没有签到的信息数量
    @GetMapping("countofnocheck")
    public Result getCountNoCheck(Integer sno){
        String publishdate = getSystemTimeUtils.getSystemTime();
     Integer count =    checkInfoService.selectCountOfNoCheck(sno,publishdate);
        return Result.success(count);
    }

}
