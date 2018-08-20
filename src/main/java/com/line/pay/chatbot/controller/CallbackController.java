package com.line.pay.chatbot.controller;

import com.google.common.io.ByteStreams;
import com.line.pay.chatbot.service.DialogFlowService;
import com.line.pay.chatbot.service.LineMessageService;
import com.line.pay.chatbot.service.LinePayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CallbackController implements ServletContextAware {
    private static Logger logger = LogManager.getLogger(CallbackController.class.getName());

    private static final String X_LINE_SIGNATURE = "X-Line-Signature";

    @Autowired
    private LineMessageService lineMessageService;

    @Autowired
    private LinePayService linePayService;

    @RequestMapping(value="/callback", method=RequestMethod.POST)
    public ResponseEntity handleCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            var signature = request.getHeader(X_LINE_SIGNATURE);
            var body = ByteStreams.toByteArray(request.getInputStream());

            String bodyStr = new String(body);

            lineMessageService.handleWebhookEvent(bodyStr);

            if (!lineMessageService.isSignatureValid(signature, body)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/confirm", method=RequestMethod.GET)
    public ResponseEntity handleConfirm(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("orderId") String orderId,
                                        @RequestParam("transactionId") long transactionId,
                                        @RequestParam("amount") int amount) {

        try {
            linePayService.invokeConfirm(transactionId, amount);

            return new ResponseEntity("DONE", HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
