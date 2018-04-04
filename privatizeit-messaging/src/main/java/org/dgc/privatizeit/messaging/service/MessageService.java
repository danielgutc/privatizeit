package org.dgc.privatizeit.messaging.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;
import org.dgc.privatizeit.messaging.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

@Service
public class MessageService
{
    private static final String MESSAGES_MAP = "MESSAGES";
    private static final String MESSAGES_TOPIC = "MESSAGES";
    private IMap<String, List<Message>> messagesMap;
    private ITopic<Message> messagesTopic;
    //private Map<String, Flux<ServerSentEvent<List<Message>>>> usersConnections = new HashMap<>();
    private Map<String, MessagePublisher> publishers = new HashMap<>();

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostConstruct
    public void init()
    {
        //this.messagesMap = hazelcastInstance.getMap(MESSAGES_MAP);
        messagesTopic = this.hazelcastInstance.getTopic(MESSAGES_TOPIC);
        messagesTopic.addMessageListener(this::processMessage);
    }

   /* public void addMessage(Message message)
    {
        if (!messagesMap.containsKey(message.getRecipientId()))
        {
            messagesMap.put(message.getRecipientId(), new ArrayList<Message>());
        }

        messagesMap.get(message.getRecipientId()).add(message);
    }

    public List<Message> getMessages(String userId)
    {
        return messagesMap.get(userId);
    }*/

/*   public Flux<ServerSentEvent<List<Message>>> registerUser(String userId, Flux<ServerSentEvent<List<Message>>> userConnection)
   {
       usersConnections.put(userId, userConnection);

       return userConnection;
   }*/

    public MessagePublisher registerUser(String userId)
    {
        MessagePublisher messagePublisher = new MessagePublisher();
        publishers.put(userId, messagePublisher);

        return messagePublisher;
    }

    public void addMessage(Message message)
    {
        messagesTopic.publish(message);
    }

   private void processMessage(com.hazelcast.core.Message<Message> message)
   {
      /* if (usersConnections.containsKey(message.getMessageObject().getRecipientId()))
       {
           Flux<ServerSentEvent<List<Message>>> userConnection = usersConnections.get(message.getMessageObject().getRecipientId());
           userConnection.map(l -> ServerSentEvent.builder(message.getMessageObject()).build());
       }*/

       if (publishers.containsKey(message.getMessageObject().getRecipientId()))
       {
           publishers.get(message.getMessageObject().getRecipientId()).sendMessage(message.getMessageObject());
       }
   }
}
