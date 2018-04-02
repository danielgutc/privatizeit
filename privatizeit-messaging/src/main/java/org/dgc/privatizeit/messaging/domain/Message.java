package org.dgc.privatizeit.messaging.domain;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable
{
    private String issuerId;
    private Long duration;
    private byte[] payload;
    private List<MessageProperty> properties;
    private List<String> recipientsId;

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

    public List<String> getRecipientsId()
    {
        return recipientsId;
    }

    public void setRecipientsId(List<String> recipientsId)
    {
        this.recipientsId = recipientsId;
    }
    //endregion
}
