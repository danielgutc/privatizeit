package org.dgc.privatizeit.messaging.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.dgc.privatizeit.messaging.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService
{
    private static final String MESSAGES_TOPIC = "MESSAGES";
    private ITopic<Message> messagesTopic;
    private Map<String, UnicastProcessor<Message>> processors = new HashMap<>();

@Autowired
    private HazelcastInstance hazelcastInstance;

    @PostConstruct
    public void init()
    {
        messagesTopic = this.hazelcastInstance.getTopic(MESSAGES_TOPIC);
        messagesTopic.addMessageListener(this::processMessage);
    }

    /**
     * Register a new user connection to enable new messages notifications.
     * @param userId to be connected.
     * @return a reactive publisher that notifies new messages.
     */
    public UnicastProcessor<Message> registerUser(String userId)
    {
        UnicastProcessor<Message> processor = UnicastProcessor.<Message>create();
        processors.put(userId, processor);

        return processor;
    }

    /**
     * Add a new message to the distributed messages topic
     * @param message
     */
    public void addMessage(Message message)
    {
        messagesTopic.publish(message);
    }

    /**
     * Listener implementation for the distributed topic.
     * @param message to be notified to the user.
     */
   private void processMessage(com.hazelcast.core.Message<Message> message)
   {
       if (processors.containsKey(message.getMessageObject().getRecipientId()))
       {
           processors.get(message.getMessageObject().getRecipientId()).onNext(message.getMessageObject());
       }
   }
}
