package subertd.assignment03;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableMongoRepositories
public class SpringMongoConfig extends AbstractMongoConfiguration {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String ENVIRONMENT_HOST = "OPENSHIFT_MONGODB_DB_HOST";
    private static final String ENVIRONMENT_PORT = "OPENSHIFT_MONGODB_DB_PORT";
    private static final String ENVIRONMENT_USER_NAME =
            "OPENSHIFT_MONGODB_DB_USERNAME";
    private static final String ENVIRONMENT_PASSWORD =
            "OPENSHIFT_MONGODB_DB_PASSWORD";

    private static final Logger logger =
            Logger.getLogger(SpringMongoConfig.class.getName());

    @Override
    public String getDatabaseName() {
        final String database = "cs496assignment3part2";
        logger.log(Level.INFO, "MongoDB db: " + database);
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {

        final String environmentHost = System.getenv(ENVIRONMENT_HOST);
        final String environmentPort = System.getenv(ENVIRONMENT_PORT);
        final String environmentUserName = System.getenv(ENVIRONMENT_USER_NAME);
        final String environmentPassword = System.getenv(ENVIRONMENT_PASSWORD);

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, new StringBuffer()
                 .append("Environment MongoDB variablse\n")
                 .append("host: ").append(environmentHost).append("\n")
                 .append("port: ").append(environmentPort).append("\n")
                 .append("userName: ").append(environmentUserName).append("\n")
                 .append("password: ").append(environmentPassword)
                 .toString()
            );
        }

        if (environmentHost != null && environmentPort != null) {

            final ServerAddress serverAddress = new ServerAddress(
                    environmentHost, Integer.parseInt(environmentPort));

            if (environmentUserName != null && environmentPassword != null) {

                MongoCredential credential = MongoCredential.createCredential(
                        environmentUserName,
                        getDatabaseName(),
                        environmentPassword.toCharArray());

                return new MongoClient(
                        serverAddress, Arrays.asList(credential));
            }
            else {
                return new MongoClient(serverAddress);
            }
        }
        else { // If environment variables are not provided, use the default
            return new MongoClient(DEFAULT_HOST);
        }
    }
}
