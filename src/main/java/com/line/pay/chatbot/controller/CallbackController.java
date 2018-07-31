package com.line.pay.chatbot.controller;

import com.line.pay.chatbot.service.DialogFlowService;
import com.line.pay.chatbot.service.LineMessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.stream.Collectors;

@Controller
public class CallbackController implements ServletContextAware {
    private static Logger logger = LogManager.getLogger(CallbackController.class.getName());

    private static final String X_LINE_SIGNATURE = "X-Line-Signature";
    private static final String HASH_ALGORITHM = "HmacSHA256";

    @Autowired
    private DialogFlowService dialogFlowService;
    @Autowired
    private LineMessageService lineMessageService;

    @RequestMapping(value="/callback", method=RequestMethod.POST)
    public ResponseEntity handleCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            var signature = request.getHeader(X_LINE_SIGNATURE);
            var body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            if (!isSignatureValid(signature, body)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean isSignatureValid(String signature, String body) {
        var result = false;

        try {
            var sha256HMAC = Mac.getInstance(HASH_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(lineMessageService.getChannelSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secretKey);

            final byte[] headerSignature = Base64.getDecoder().decode(signature);
            final byte[] bodySignature = sha256HMAC.doFinal(body.getBytes());

            logger.info("headerSignature = " + headerSignature);
            logger.info("bodySignature = " + bodySignature);

            if(MessageDigest.isEqual(headerSignature, bodySignature)) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            return result;
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
