package com.example.face_ui_location;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(value={"com.example.face_ui_location.mapper","com.example.face_ui_location.service"})
public class FaceUiLocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceUiLocationApplication.class, args);
    }

}
