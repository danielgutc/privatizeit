package org.dgc.privatizeit.messaging.integration;

import org.dgc.privatizeit.messaging.domain.Message;
import org.dgc.privatizeit.messaging.domain.MessageBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagingControllerIntTest
{
    @Autowired
    private WebTestClient client;

    @Test //TODO Fix test java.lang.IllegalStateException: Timeout on blocking read for 5000 MILLISECONDS
    public void sendMessage()
    {
        FluxExchangeResult<Message> result = client.get()
                .uri("/message")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWwiLCJleHAiOjE2MDkyMzM5NzJ9.tUTFSSjAJ2DeSNdkNXgEMD90d0MgbQOwAf56B29a_InBMn3ECmMk8zAYSn0n9EnceYnsdj-PjNDnTCfAWRlYbQ")
                .exchange()
                .returnResult(Message.class);

        result.getResponseBody().subscribe(m -> System.out.println(m.toString()));

        client.post()
                .uri("/message")
                .body(Mono.just(MessageBuilder.aMessage().withIssuerId("daniel").withRecipientId("daniel").withDuration(1000L).build()), Message.class)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWwiLCJleHAiOjE2MDkyMzM5NzJ9.tUTFSSjAJ2DeSNdkNXgEMD90d0MgbQOwAf56B29a_InBMn3ECmMk8zAYSn0n9EnceYnsdj-PjNDnTCfAWRlYbQ")
                .exchange();

    }
}
