package com.itlong.whatsmars.common.mongo.object;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * Author: qing
 * Date: 14-10-10
 */
public class MongoDBDriver {

    private Properties properties;
    /**
     */
    private MongoClient mongo = null;

    /**
     */
    private Integer connectionsPerHost = 32;

    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;

    private Integer maxWaitTime = 30000;
    private Integer connectTimeout = 30000;
    private Integer socketTimeout = 30000;

    private Integer maxConnectionIdle = 6000;


    private MongoDBConfig configuration;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setConfiguration(MongoDBConfig configuration) {
        this.configuration = configuration;
    }

    public void init() throws Exception{
        if(properties != null){

            String perHost = properties.getProperty("mongodb.driver.connectionsPerHost");
            ////////////
            if(StringUtils.isNotBlank(perHost)){
                connectionsPerHost = Integer.valueOf(perHost);
            }
            ////////////
            String multiplier = properties.getProperty("mongodb.driver.threadsAllowedToBlockForConnectionMultiplier");
            if(StringUtils.isNotBlank(multiplier)){
                threadsAllowedToBlockForConnectionMultiplier = Integer.valueOf(multiplier);
            }
            /////////////
            String waitTime = properties.getProperty("mongodb.driver.maxWaitTime");
            if(StringUtils.isNotBlank(waitTime)){
                maxWaitTime = Integer.valueOf(waitTime);
            }
            ////////////
            String ctimeout = properties.getProperty("mongodb.driver.connectTimeout");
            if(StringUtils.isNotBlank(ctimeout)){
                connectTimeout = Integer.valueOf(ctimeout);
            }
            ////////////
            String stimeout = properties.getProperty("mongodb.driver.socketTimeout");
            if(StringUtils.isNotBlank(stimeout)){
                socketTimeout = Integer.valueOf(stimeout);
            }
            ////////////
            String mci = properties.getProperty("mongodb.driver.maxConnectionIdle");
            if(StringUtils.isNotBlank(mci)){
                maxConnectionIdle = Integer.valueOf(mci);
            }
        }
        ////init,db check and connected.
        execute();

    }

    private void execute() throws Exception{
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.connectionsPerHost(this.connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        builder.socketKeepAlive(true);
        builder.maxWaitTime(this.maxWaitTime);
        builder.connectTimeout(this.connectTimeout);
        builder.socketTimeout(this.socketTimeout);
        builder.maxConnectionIdleTime(maxConnectionIdle);

        MongoClientOptions options = builder.build();

        this.mongo = new MongoClient(configuration.buildAddresses(), configuration.buildCredentials(),options);

    }


    public void close() {
        mongo.close();
    }

    public Mongo getMongo() {
        return mongo;
    }

    public MongoDatabase getDatabase(String dbName) {
        return mongo.getDatabase(dbName);
    }

    /**
     * old api
     * @param dbName
     * @return
     */
    public DB getDB(String dbName) {
        return mongo.getDB(dbName);
    }

}