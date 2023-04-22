package com.example.face_ui_location.utils;


public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371000; // 地球半径，单位为米
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
    public static void main(String[]args){
        Double stuJingdu = 127.05994;
        Double stuWeidu = 11.5125;
        Double TeaJingdu = 127.05994;
        Double TeaWeidu = 10.5125;
        double distance = DistanceCalculator.calculate(stuWeidu, stuJingdu, TeaWeidu, TeaJingdu);
        System.out.println(distance);
    }
}
//其中，`lat1`、`lon1`为第一个经纬度坐标，`lat2`、`lon2`为第二个经纬度坐标，`distance`为计算出的距离，单位为米。









