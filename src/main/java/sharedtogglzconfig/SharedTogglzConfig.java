package sharedtogglzconfig;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.cache.CachingStateRepository;
import org.togglz.core.user.NoOpUserProvider;
import org.togglz.core.user.UserProvider;
import org.togglz.mongodb.MongoStateRepository;
import org.togglz.mongodb.MongoStateRepository.Builder;

import com.mongodb.MongoClient;

@Configuration
@Import(SharedTogglzServlet.class)
public class SharedTogglzConfig implements TogglzConfig {

    private static final String SPRING_DATA_MONGODB_DATABASE = "spring.data.mongodb.database";
    private static final String SPRING_DATA_MONGODB_HOST = "spring.data.mongodb.host";
    private static final String TOGGLZ_CACHE_TIMEOUT = "spring.data.togglz.cacheTimeout";

    @Autowired(required = false)
    private UserProvider userProvider;

    @Autowired
    private Environment env;

    @Autowired
    private MongoStateRepository dataSource;

    @Autowired
    private Class<? extends Feature> feature;

    @Bean
    public MongoStateRepository setStateRepository() throws UnknownHostException {
        final Builder builder =
                new MongoStateRepository.Builder(new MongoClient(env.getProperty(SPRING_DATA_MONGODB_HOST)),
                        env.getProperty(SPRING_DATA_MONGODB_DATABASE));

        return builder.build();
    }

    public Long getCacheTimeout() {
        return Long.valueOf(env.getProperty(TOGGLZ_CACHE_TIMEOUT));
    }

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return feature;
    }

    @Override
    public StateRepository getStateRepository() {
        return new CachingStateRepository(dataSource, getCacheTimeout());
    }

    @Override
    public UserProvider getUserProvider() {
        return userProvider != null ? userProvider : new NoOpUserProvider();
    }

}
