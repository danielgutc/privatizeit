package org.dgc.privatizeit.messaging.domain;

import java.io.Serializable;

public class MessageProperty implements Serializable
{
    private String key;
    private String value;

    //region Getters/Setters

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    //endregion


    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("MessageProperty{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
