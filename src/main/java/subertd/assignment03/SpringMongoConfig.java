package subertd.assignment03;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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

        MongoClient connection; // The connection to establish and return

        if (environmentHost != null && environmentPort != null) {
            logger.log(Level.INFO, "Attempting a MongoDB connection with\n"
                            + "host: " + environmentHost + "\n"
                            + "port: " + environmentPort + "\n"
                            + "userName" + environmentUserName + "\n"
                            + "password" + environmentPassword
            );

            connection = new MongoClient(environmentHost,
                    Integer.parseInt(environmentPort));

            if (environmentUserName != null && environmentPassword != null) {
                List<MongoCredential> credentialsList = connection.getCredentialsList();
                credentialsList.add(MongoCredential.createCredential(
                        environmentUserName,
                        getDatabaseName(),
                        environmentPassword.toCharArray()
                ));
            }
        }
        else { // If environment variables are not provided, use the default
            connection = new MongoClient(DEFAULT_HOST);
        }

        logger.log(Level.INFO, "MongoDB connection: " + connection);
        return connection;
    }
}
