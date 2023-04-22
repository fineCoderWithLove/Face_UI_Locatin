package com.example.face_ui_location.controller;

import com.example.face_ui_location.parm.BaseCode;
import com.example.face_ui_location.parm.FaceInitParms;
import com.example.face_ui_location.service.FaceInsertDao;
import com.example.face_ui_location.service.faceservice.FaceService;
import com.example.face_ui_location.service.personservice.StudentService;
import com.example.face_ui_location.utils.QiniuUtils;
import com.example.face_ui_location.vo.FaceResult;
import com.example.face_ui_location.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("fileupload")
public class FileUpLoadController {


    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FaceService faceService;
    @Autowired
    private FaceInsertDao faceInsertDao;

    @PostMapping("upload")
    public Result UpLoadStuImage(@RequestParam("file") MultipartFile file,@RequestParam("sno") Integer sno){
        System.out.println("进入到接口里面了");
        System.out.println("kom----asd-asd-as-dasdasdasdasdasdasdasdasdasdasdasdasd");
        System.out.println(sno);
        String src = qiniuUtils.url+"/"+file.getOriginalFilename();
        boolean flag =  qiniuUtils.upload(file,file.getOriginalFilename());
        if(flag){
            //持久化
            System.out.println("success");
           boolean secFlag =  studentService.updateStuImage(sno,src);
           if(secFlag){
               return Result.success(200);
           }
        }
        return Result.fail(500,"服务器繁忙");
    }

    @PostMapping("base64")
    public  Result UpLoadBase64(@RequestBody FaceInitParms faceInitParms){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        faceInitParms.setCreateTime(now.format(formatter2));
        faceInsertDao.inserFaceInfo(faceInitParms.getImageBase(),faceInitParms.getName(),faceInitParms.getCreateTime());
        return Result.success(200);
    }

    @PostMapping("/vef")
    public FaceResult faceVef(@RequestBody String imageBase){
        return faceService.vef(imageBase);
    }

}
