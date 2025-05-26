package com.example.SpringSecurity.utils.FactoryPattern;

//	Inheritance + Polymorphism
public class EmailNotification implements Notification { //Inheritance

    //Polymorphism
    @Override
    public void send(String to, String message) {
        System.out.println("Send email to " + to + ": " + message);
    }
}