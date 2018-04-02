package org.dgc.privatizeit.messaging.controller;

import org.dgc.privatizeit.messaging.domain.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingController
{

    @PostMapping("/message")
    public void sendMessage(@RequestBody Message message)
    {

    }
}
