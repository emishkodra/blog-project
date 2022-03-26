package com.dev.service.impl;

import com.dev.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    public static final String NOTIFY_MSG_SESSION_KEY = "blogNotificationMessages";

    public enum NotificationMessageType{
        INFO,
        ERROR
    }

    @Autowired
    private HttpSession httpSession;

    @Override
    public void addInfoMessage(String message){
        addNotificationMessage(NotificationMessageType.INFO, message);
    }

    @Override
    public void addErrorMessage(String message){
        addNotificationMessage(NotificationMessageType.ERROR, message);
    }

    private void addNotificationMessage(NotificationMessageType type, String message){
        List<NotificationMessage> notifyMessages = (List<NotificationMessage>)httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if (notifyMessages == null){
            notifyMessages = new ArrayList<NotificationMessage>();
        }else {
            notifyMessages.add(new NotificationMessage(type, message));
            httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);
        }
    }

    public class NotificationMessage {
        NotificationMessageType type;
        String text;

        public NotificationMessage(NotificationMessageType type, String text){
            this.type = type;
            this.text = text;
        }

        public NotificationMessageType getType() {
            return type;
        }

        public String getText() {
            return text;
        }
    }
}
