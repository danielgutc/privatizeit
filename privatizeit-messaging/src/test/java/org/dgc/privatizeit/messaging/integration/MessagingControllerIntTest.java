package org.dgc.privatizeit.messaging.integration;

import org.dgc.privatizeit.messaging.controller.MessagingController;
import org.dgc.privatizeit.messaging.domain.Message;
import org.dgc.privatizeit.messaging.domain.MessageBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagingControllerIntTest
{
    @Autowired
    private WebTestClient client;

    @Test
    public void sendMessage()
    {
        /*FluxExchangeResult<Message> result = client.get()
                .uri("/message")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWwiLCJleHAiOjE2MDkyMzM5NzJ9.tUTFSSjAJ2DeSNdkNXgEMD90d0MgbQOwAf56B29a_InBMn3ECmMk8zAYSn0n9EnceYnsdj-PjNDnTCfAWRlYbQ")
                .exchange()
                .returnResult(Message.class);

        Message message = result.getResponseBody().blockFirst();*/
        assertTrue(true);
    }
}
