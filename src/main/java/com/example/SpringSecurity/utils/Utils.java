package com.example.SpringSecurity.utils;

public class Utils {

    //✅ Dùng StringBuilder khi cần nối chuỗi nhiều lần, tránh tạo quá nhiều object như khi dùng +.

    public static String buildAlertMessage(String username, String zoneName) {
        return new StringBuilder()
                .append("⚠️ User ")
                .append(username)
                .append(" vừa ra khỏi vùng an toàn: ")
                .append(zoneName)
                .toString();
    }

}
