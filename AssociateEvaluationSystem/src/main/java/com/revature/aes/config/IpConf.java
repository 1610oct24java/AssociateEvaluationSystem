package com.revature.aes.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.logging.Logging;

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

            return getEc2HostName();
        }

        else{

            return getLocalMachineName();

        }

    }

    public String getEc2HostName(){

        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<String> response = restTemplate.exchange("http://169.254.169.254/latest/meta-data/public-hostname", HttpMethod.GET, null, String.class);
        //return response.getBody()+":"+getPort();
        return restTemplate.getForObject("http://169.254.169.254/latest/meta-data/public-hostname", String.class) + ":" + getPort();

    }

    public String getLocalMachineName(){

        String hostName;

        try{

            hostName = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e) {
            log.error("Failed to set localhost address to ip const");
            log.stackTraceLogging(e);
            hostName = "localhost";
        }

        return hostName+":"+getPort();

    }

    public String getPort(){

        return serverProperties.getPort().toString();

    }

}
