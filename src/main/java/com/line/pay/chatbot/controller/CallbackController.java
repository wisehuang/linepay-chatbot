package com.line.pay.chatbot.controller;

import com.line.pay.chatbot.service.DialogFlowService;
import com.line.pay.chatbot.service.LineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CallbackController implements ServletContextAware {

    @Autowired
    private DialogFlowService dialogFlowService;
    @Autowired
    private LineMessageService lineMessageService;

    @RequestMapping(value="/callback", method=RequestMethod.POST)
    public ResponseEntity handleCallback(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
