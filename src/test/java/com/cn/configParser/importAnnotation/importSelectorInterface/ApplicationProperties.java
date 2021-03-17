package com.cn.configParser.importAnnotation.importSelectorInterface;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:29
 **/
public class ApplicationProperties {
    private String connectionUrl;
    private String connectName;

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "connectionUrl='" + connectionUrl + '\'' +
                ", connectName='" + connectName + '\'' +
                '}';
    }
}
