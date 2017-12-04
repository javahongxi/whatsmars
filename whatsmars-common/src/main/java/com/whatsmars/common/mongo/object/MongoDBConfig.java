package com.whatsmars.common.mongo.object;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: qing
 * Date: 14-10-12
 */
public class MongoDBConfig {

    private String addresses;


    private List<MongoDBCredential> credentials;

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public List<MongoDBCredential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<MongoDBCredential> credentials) {
        this.credentials = credentials;
    }

    public List<MongoCredential> buildCredentials() {
        List<MongoCredential> mongoCredentials = new ArrayList<MongoCredential>();
        for(MongoDBCredential item : this.credentials) {
            MongoCredential credential = MongoCredential.createCredential(item.getUsername(),item.getDatabaseName(),item.getPassword().toCharArray());
            mongoCredentials.add(credential);
        }
        return mongoCredentials;
    }

    public List<ServerAddress> buildAddresses() throws Exception{
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        String[] sources = addresses.split(";");
        for(String item : sources) {
            String[] hp = item.split(":");
            serverAddresses.add(new ServerAddress(hp[0],Integer.valueOf(hp[1])));
        }
        return serverAddresses;
    }
}
