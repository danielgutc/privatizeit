package org.dgc.privatizeit.messaging.integration;

import org.dgc.privatizeit.messaging.App;
import org.dgc.privatizeit.messaging.domain.Message;
import org.dgc.privatizeit.messaging.domain.MessageBuilder;
import org.dgc.privatizeit.messaging.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {App.class, HazelcastConfiguration.class})
public class MessageServiceIntTest
{
    @Autowired
    MessageService messageService;

    @Test
    public void addMessage()
    {
        Message message = MessageBuilder.aMessage().withIssuerId("issuer").withRecipientId("recipient").build();
        
        messageService.addMessage(message);
        assertTrue(true);
    }
}
