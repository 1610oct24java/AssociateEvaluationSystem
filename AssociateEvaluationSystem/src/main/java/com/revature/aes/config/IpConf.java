package com.revature.aes.config;

import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Nick on 1/26/2017.
 */

@Service
public class IpConf {

    @Autowired
    Logging log;

    @Inject
    private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

    public static final boolean onEc2 = true;

    public String getHostName(){

        if(onEc2){
            return getEc2HostName();
        }

        else{

            return getLocalMachineName();

        }

    }

    public String getEc2HostName(){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://169.254.169.254/latest/meta-data/public-hostname", HttpMethod.GET, null, String.class);
        return response.getBody()+":"+getPort();

    }

    public String getLocalMachineName(){

        String hostName;

        try{

            hostName = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e) {
            log.error("Failed to set localhost address to ip const");
            hostName = "localhost";
        }

        return hostName+":"+getPort();

    }

    public String getPort(){

        return serverProperties.getPort().toString();

    }

}
