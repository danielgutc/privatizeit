package org.dgc.privatizeit.messaging.domain;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable
{
    private String issuerId;
    private Long duration;
    private byte[] payload;
    private List<MessageProperty> properties;
    private String recipientId;

    //region Getters/Setters

    public String getIssuerId()
    {
        return issuerId;
    }

    public void setIssuerId(String issuerId)
    {
        this.issuerId = issuerId;
    }

    public Long getDuration()
    {
        return duration;
    }

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public byte[] getPayload()
    {
        return payload;
    }

    public void setPayload(byte[] payload)
    {
        this.payload = payload;
    }

    public List<MessageProperty> getProperties()
    {
        return properties;
    }

    public void setProperties(List<MessageProperty> properties)
    {
        this.properties = properties;
    }

    public String getRecipientId()
    {
        return recipientId;
    }

    public void setRecipientId(String recipientId)
    {
        this.recipientId = recipientId;
    }

    //endregion


    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("issuerId='").append(issuerId).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", payload=");
        if (payload == null) sb.append("null");
        else
        {
            sb.append('[');
            for (int i = 0; i < payload.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(payload[i]);
            sb.append(']');
        }
        sb.append(", properties=").append(properties);
        sb.append(", recipientId='").append(recipientId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
