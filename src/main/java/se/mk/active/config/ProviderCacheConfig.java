package se.mk.active.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderCacheConfig {

    @Bean
    public Config cacheConfig() {
        return new Config()
                .setInstanceName("hazel-instance")
                .addMapConfig(new MapConfig()
                        .setName("provider-cache")
                        .setTimeToLiveSeconds(6000));
    }
}
