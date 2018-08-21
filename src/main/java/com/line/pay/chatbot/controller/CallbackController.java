package com.line.pay.chatbot.controller;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.line.pay.chatbot.events.Event;
import com.line.pay.chatbot.events.TemplateMessage;
import com.line.pay.chatbot.payment.ReserveResponse;
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

            if (!lineMessageService.isSignatureValid(signature, body)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            var bodyStr = new String(body);

            logger.info("body:" + bodyStr);

            var webhookEvent = lineMessageService.handleWebhookEvent(bodyStr);

            for (var event : webhookEvent.getEvents()) {
                logger.info("event type:" + event.getType());

                if ("message".equals(event.getType())) {
                    replyTemplateMessage(event);
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private void replyTemplateMessage(Event event) throws Exception {
        var msgString = event.getMessage().getText();
        var replyToken = event.getReplyToken();

        logger.info("event content:" + msgString);
        logger.info("reply token:" + replyToken);

        var reserveResponse = new ReserveResponse();

        if ("pay".equals(msgString.split(" ")[0])) {
            var amount = Long.valueOf(msgString.split(" ")[1]);

            reserveResponse = linePayService.invokeReserve(amount);
        }

        var appUrl = reserveResponse.getInfo().getPaymentUrl().getApp();


        var templateMessage = lineMessageService.getTemplateMessage(replyToken, appUrl);

        var gson = new Gson();
        var json = gson.toJson(templateMessage);

        logger.info("Reply TemplateMessage:" + json);

        lineMessageService.replyMessage(json);
    }

    @RequestMapping(value="/confirm", method=RequestMethod.GET)
    public String handleConfirm(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("transactionId") long transactionId,
                                        @RequestParam("amount") int amount) {

        try {
            linePayService.invokeConfirm(transactionId, amount);

            return "redirect:line://ti/p/@wej7798j";
        } catch (Exception e) {
            logger.error(e);
            return "redirect:line://ti/p/@wej7798j";
        }
    }



    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
