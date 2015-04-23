package subertd.assignment03;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.logging.Logger;

/**
 * Created by Donald on 4/22/2015.
 *
 * @citation This class is based on a template found at:
 * http://www.mkyong.com/mongodb/spring-data-mongodb-hello-world-example/
 */
@Configuration
@EnableMongoRepositories
public class SpringMongoConfig extends AbstractMongoConfiguration {

    private static final Logger logger =
            Logger.getLogger(SpringMongoConfig.class.getName());

    @Override
    public String getDatabaseName() {
        final String result = "assignment03";
        logger.exiting(SpringMongoConfig.class.getName(),
                "getDatabaseName()", result);
        return result;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        final Mongo result = new MongoClient("127.0.0.1"); // TODO make this work with openshift
        logger.exiting(SpringMongoConfig.class.getName(),
                "mongo()", result);
        return result;
    }
}
