package org.hongxi.whatsmars.common.mongo.object;

/**
 * Author: qing
 * Date: 14-10-15
 */
public class MongoDBCredential {

    private String databaseName;

    private String username;
    private String password;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
