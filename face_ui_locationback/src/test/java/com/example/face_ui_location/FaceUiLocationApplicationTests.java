package com.example.face_ui_location;

import com.example.face_ui_location.service.checkservice.CheckInfoService;
import com.example.face_ui_location.utils.TeaTodayCheckVo;
import com.example.face_ui_location.utils.TodayCheckInfoVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FaceUiLocationApplicationTests {

    @Autowired
    private CheckInfoService checkInfoService;
    @Test
    public void isInArea(){
        List<TodayCheckInfoVo> todayCheckInfoVos = checkInfoService.selectTodayCheck(2109124, "2023-03-28");//改查询信息没有问题
        //查询当日所有老师发布的签到信息，应该得到一个集合，然后两层for循环嵌套
        List<TeaTodayCheckVo> teaTodayCheckVos = checkInfoService.findTeaTodayCheck("2023-03-28");//只要date嘛
        System.out.println(todayCheckInfoVos);
        System.out.println(teaTodayCheckVos);

    }



        private static final double EARTH_RADIUS = 6371000; // 地球半径，单位为米
        @Test
        public static double calculate(double lat1, double lon1, double lat2, double lon2) {
            double radLat1 = Math.toRadians(lat1);
            double radLon1 = Math.toRadians(lon1);
            double radLat2 = Math.toRadians(lat2);
            double radLon2 = Math.toRadians(lon2);

            double a = Math.sin((radLat2 - radLat1) / 2) * Math.sin((radLat2 - radLat1) / 2)
                    + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin((radLon2 - radLon1) / 2) * Math.sin((radLon2 - radLon1) / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return EARTH_RADIUS * c;
        }
    //其中，`lat1`、`lon1`为第一个经纬度坐标，`lat2`、`lon2`为第二个经纬度坐标，`distance`为计算出的距离，单位为米。
    //传进来三个参数
    @Test
    public void isOntime(){
        String begintime = "07:55";
        String endtime = "8:30";
        System.out.println(Integer.parseInt(begintime.split(":")[0]));
        //将字符串转换为min然后判断是不是在区间之内
        Integer beginmin = Integer.parseInt(begintime.split(":")[0])*60+Integer.parseInt(begintime.split(":")[1]);
        Integer endmin = Integer.parseInt(endtime.split(":")[0])*60+Integer.parseInt(endtime.split(":")[1]);
        System.out.println("区间是:"+beginmin+"~"+endmin);

    }

}
