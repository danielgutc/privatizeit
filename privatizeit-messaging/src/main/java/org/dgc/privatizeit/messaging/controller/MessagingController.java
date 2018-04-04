package org.dgc.privatizeit.messaging.controller;

import org.dgc.privatizeit.messaging.domain.Message;
import org.dgc.privatizeit.messaging.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MessagingController
{
    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message, Authentication authentication)
    {
        if (!authentication.getName().equals(message.getIssuerId()))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        messageService.addMessage(message);

        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //public Flux<ServerSentEvent<List<Message>>> receiveMessages(Authentication authentication)
    public Flux<ServerSentEvent<Message>> receiveMessages(Authentication authentication)
    {
        //return Flux.interval(Duration.ofSeconds(1L)).map(l -> this.getUserPendingMessages(authentication.getName()));
        return Flux.from(messageService.registerUser(authentication.getName())).map(m -> ServerSentEvent.builder(m).build());
    }
}
