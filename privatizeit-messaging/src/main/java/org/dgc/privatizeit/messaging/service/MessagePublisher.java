package org.dgc.privatizeit.messaging.service;

import org.dgc.privatizeit.messaging.domain.Message;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class MessagePublisher implements Publisher<Message>
{
    private Subscriber<? super Message> subscriber;

    @Override
    public void subscribe(Subscriber<? super Message> subscriber)
    {
        this.subscriber = subscriber;
    }

    public void sendMessage(Message message)
    {
        subscriber.onNext(message);
    }
}
