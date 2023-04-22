package com.example.face_ui_location.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GetSystemTimeUtils {
    private static final double EARTH_RADIUS = 6371000; // 地球半径，单位为米

    public String getSystemTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strTime = dtf.format(localDateTime);
        return strTime;
    }
    //学生获取时间信息的
    public String getHourAndSecond(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String strTime = dtf.format(localDateTime);
        return strTime;
    }
    //返回值注意问题，其实不适合用boolean来判断更适合用int类型，因为不仅仅是迟到的原因，还有签到过时的原因，比如默认为2不代表任何含义，0为签到迟到（也就是签到过期），1为早退，
    public boolean isOntime(String beginTime,String endTime,String checkinTime){
        //将字符串转换为min然后判断是不是在区间之内
        System.out.println("开始时间"+beginTime);
        System.out.println("结束时间时间"+endTime);
        System.out.println("受检时间"+checkinTime);
        Integer beginmin = Integer.parseInt(beginTime.split(":")[0])*60+Integer.parseInt(beginTime.split(":")[1]);
        Integer endmin = Integer.parseInt(endTime.split(":")[0])*60+Integer.parseInt(endTime.split(":")[1]);
        Integer checkinmin = Integer.parseInt(checkinTime.split(":")[0])*60+Integer.parseInt(checkinTime.split(":")[1]);
        System.out.println("-------------------------------------------------------");
        System.out.println(beginmin);
        System.out.println(endmin);
        System.out.println(checkinmin);
        //如果签到在区间之内就返回true,就是签到正常的意思
        if(checkinmin<=endmin&&checkinmin>=beginmin){//checkmin小于终止时间且大于开始时间相当于时间允许签到了，就返回true
            System.out.println("时间在区间范围内");
            return true;
        }
        System.out.println("时间不在区间范围内签到过期了");
        return false;

    }

}
