package com.dev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String logError = "StatusCode->" + RequestDispatcher.ERROR_STATUS_CODE + "\n ErrorException->"
                + RequestDispatcher.ERROR_EXCEPTION.toString() + "\n ErrorException->"
                + RequestDispatcher.ERROR_MESSAGE.toString();

        // log error details
        log.error("<-------------------------------->");
        log.error("An error occured!!!");
        log.error(logError);
        log.error("<-------------------------------->");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403";
            }
        }

        // display generic error
        return "error";
    }

}