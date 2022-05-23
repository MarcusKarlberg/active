package se.mk.active.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DataInitializer dataInitializer;
    private final Environment env;
    private static final String PROP_INIT_DATA = "active.data.init";

    public StartupApplicationListener(DataInitializer dataInitializer, Environment env) {
        this.dataInitializer = dataInitializer;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("StartupApplicationListener initiated.");
        String isInit = env.getProperty(PROP_INIT_DATA);

        if(Boolean.parseBoolean(isInit)) {
            dataInitializer.initData();
        }
    }
}
