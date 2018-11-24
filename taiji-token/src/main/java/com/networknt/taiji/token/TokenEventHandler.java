package com.networknt.taiji.token;

import com.networknt.taiji.event.EventHandlerContext;
import com.networknt.taiji.event.EventHandlerMethod;
import com.networknt.taiji.event.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventSubscriber(id="taijiTokenEventHandlers")
public class TokenEventHandler {
    static final Logger logger = LoggerFactory.getLogger(TokenEventHandler.class);

    @EventHandlerMethod
    public void updateSnapshot(EventHandlerContext<TokenCreatedEvent> ctx) {
        TokenCreatedEvent event = ctx.getEvent();
        if(logger.isDebugEnabled()) logger.debug("event = " + event);
    }
}
