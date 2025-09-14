package com.nsoft.offer.ws.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nsoft.offer.model.dto.EventDto;
import com.nsoft.offer.model.dto.MarketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private WebSocketHandler eventWebSocketHandler;

    private final WebSocketNotificationHandler socketHandler;
    private final JsonMapper jsonMapper = new JsonMapper();

    public NotificationService(WebSocketNotificationHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void sendEventDeletedNotification(String id) throws JsonProcessingException {
        log.info("[{}] Sending event deleted notification for event id: {}", this.getClass().getSimpleName(), id);
        var message = new Notification("Event deleted!", id);
        socketHandler.broadcast(jsonMapper.writeValueAsString(message));
    }

    public void sendEventCreatedNotification(EventDto dto) throws JsonProcessingException {
        log.info("[{}] Sending event created notification for event id: {}", this.getClass().getSimpleName(), dto.getId());
        var eventJson = jsonMapper.writeValueAsString(dto);
        var message = new Notification("Event created", eventJson);
        socketHandler.broadcast(jsonMapper.writeValueAsString(message));
    }

    public void sendMarketCreatedNotification(MarketDto dto) throws JsonProcessingException {
        log.info("[{}] Sending market created notification for market id: {}", this.getClass().getSimpleName(), dto.getId());
        var marketJson = jsonMapper.writeValueAsString(dto);
        var message = new Notification("Market created!", marketJson);
        socketHandler.broadcast(jsonMapper.writeValueAsString(message));
    }

}
