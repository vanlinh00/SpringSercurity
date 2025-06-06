package com.example.SpringSecurity.service.StrategryPattern;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("sms")
public class SmsNotification implements NotificationStrategy {
//
//    @Value("${twilio.accountSid}")
//    private String accountSid;
//
//    @Value("${twilio.authToken}")
//    private String authToken;
//
//    @Value("${twilio.fromPhoneNumber}")
//    private String fromPhoneNumber;

    @Override
    public void send(String to, String note) {

//        // Khởi tạo Twilio client
//        Twilio.init(accountSid, authToken);
//
//        Message message = Message.creator(
//                new PhoneNumber(to),   // Số nhận
//                new PhoneNumber(fromPhoneNumber), // Số gửi
//                note                       // Nội dung tin nhắn
//        ).create();

        System.out.println("✅ SMS sent with SID: ");// + message.getSid());

    }
}
