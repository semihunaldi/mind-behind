package com.mindbehind.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by semihunaldi on 28.06.2021
 */

@Configuration
public class EmbeddedMongoConfig {

    MongodStarter starter = MongodStarter.getDefaultInstance();
    MongodExecutable mongodExecutable;

    @Bean
    public MongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        MongoClient mongo = MongoClients.create("mongodb://localhost:27017");
        return new SimpleMongoClientDatabaseFactory(mongo, "mindbehind");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }

    @PostConstruct
    public void construct() throws UnknownHostException, IOException {


        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27017, Network.localhostIsIPv6()))
                .build();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @PreDestroy
    public void destroy() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
