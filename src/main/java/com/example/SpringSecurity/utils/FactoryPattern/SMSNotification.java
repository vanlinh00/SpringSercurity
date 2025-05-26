package com.example.SpringSecurity.utils.FactoryPattern;


//	Inheritance + Polymorphism
public class SMSNotification implements Notification {  //Inheritance

    //Polymorphism
    @Override
    public void send(String to, String message) {
        System.out.println("Send SMS to " + to + ": " + message);
    }
}