package com.djh.admin.config.db.sqlserver2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/25.
 */
@Component
@ConfigurationProperties(prefix = "datasource.sqlserver2")

public class SqlServer2DBProperties {
    private String url;
    private String driverClassName;
    private String validationQuery;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
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

    public String getValidationQuery() { return validationQuery; }

    public void setValidationQuery(String validationQuery) { this.validationQuery = validationQuery; }
}
