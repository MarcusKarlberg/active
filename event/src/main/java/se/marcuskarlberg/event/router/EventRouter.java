package se.marcuskarlberg.event.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import se.marcuskarlberg.event.handler.EventHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EventRouter {
    @Bean
    public RouterFunction<ServerResponse> eventsRoute(EventHandler eventHandler) {
        return route()
                .POST("/api/v1/events", eventHandler::createEvent)
                .GET("/api/v1/events", eventHandler::getEvents)
                .build();
    }
}
