package ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by vlmazlov on 07.11.14.
 */
@Service
public class URLManager
{
    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.host:localhost}")
    private String host;

    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.port:8080}")
    private int port;

    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.service.prefix:}")
    private String prefix;

    public String getUrl() {
        return "http://" + host + ":" + port + "/" + prefix;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host)
    {
        this.host = host;
    }
    public void setPort(int port) {
        this.port = port;
    }
}
