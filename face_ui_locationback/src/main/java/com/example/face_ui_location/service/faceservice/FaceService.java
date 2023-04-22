package com.example.face_ui_location.service.faceservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.face_ui_location.domain.Face;
import com.example.face_ui_location.vo.FaceResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaceService extends IService<Face> {


    FaceResult vef(String imageBase);
}
