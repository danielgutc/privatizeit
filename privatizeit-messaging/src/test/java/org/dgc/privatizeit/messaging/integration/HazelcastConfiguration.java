package org.dgc.privatizeit.messaging.integration;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration
{
    @Bean
    public HazelcastInstance createHazelcastInstance()
    {
        return Hazelcast.newHazelcastInstance();
    }
}
