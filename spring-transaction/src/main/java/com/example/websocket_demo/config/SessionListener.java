package com.example.websocket_demo.config;


import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[Session Created] ID=" + se.getSession().getId() +
                ", 생성시간=" + new java.util.Date(se.getSession().getCreationTime()));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("[Session Destroyed] ID=" + se.getSession().getId() +
                ", 마지막접속=" + new java.util.Date(se.getSession().getLastAccessedTime()));
    }
}