package org.dgc.privatizeit.messaging.controller;

import org.dgc.privatizeit.messaging.config.WebFluxSecurityConfig;
import org.dgc.privatizeit.messaging.domain.Message;
import org.dgc.privatizeit.messaging.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MessagingController
{
    private static final Logger logger = LoggerFactory.getLogger(MessagingController.class);

    @Autowired
    private MessageService messageService;

    /**
     * Send a message.
     * @param message to be sent.
     * @param authentication in the scope allows the system to verify the sender.
     * @return
     */
    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message, Authentication authentication)
    {
        if (!authentication.getName().equals(message.getIssuerId()))
        {
            logger.warn("USer %s tried to send a message impersonating %s", authentication.getName(), message.getIssuerId());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        messageService.addMessage(message);

        return ResponseEntity.accepted().build();
    }

    /**
     * Connect users to allow receiving notifications.
     * @param authentication is used to determine the user that is connecting.
     * @return an asynchronous SSE Flux
     */
    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<ServerSentEvent<Message>> receiveMessages(Authentication authentication)
    {
        logger.info("Registering user %s", authentication.getName());

        return messageService.registerUser(authentication.getName()).map(m -> ServerSentEvent.builder(m).build());
    }
}
