package com.example.face_ui_location.service;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaceInsertDao {
    @Insert("insert into face values (null,#{faceBase},#{createTime},0,#{faceName},null,0,null,null,null)")
    void inserFaceInfo(String faceBase, String faceName, String createTime);
}
