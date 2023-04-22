package com.example.face_ui_location.utils;

import org.springframework.stereotype.Component;

@Component
public class LocationUtils {
    private static final double EARTH_RADIUS = 6371000; // 地球半径，单位为米

    //这是一个判断地理位置的方法，通过传入的经纬度来换算成米，再来判断学生是否签到成功！0为未签到成功，1为签到成功
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

    public boolean ischeckinlocation(Double weidu, Double jingdu, Double teaTodayCheckVoWeidu, Double teaTodayCheckVoJingdu, int allowmaxloc) {
        Double distance = calculate(weidu, jingdu, teaTodayCheckVoWeidu, teaTodayCheckVoJingdu);
        if(distance<=allowmaxloc*100){
            System.out.println("距离是"+distance+"且在");
            return true;
        }
        System.out.println("距离是："+distance+"且不在");
        return false;
    }

}
