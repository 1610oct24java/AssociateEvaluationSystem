package com.revature.aes.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.inject.Inject;

import com.revature.aes.util.PropertyReader;
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

	@Autowired
	private PropertyReader propertyReader;

	private static String restTemp;

	@Inject
	private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

	public static final boolean onEc2 = true;

	public String getHostName(){

		if (onEc2) {
			return getEc2HostName();
		}

		else {

			return getLocalMachineName();

		}

	}

	public String getEc2HostName() {

		Properties properties = propertyReader.propertyRead("ipConfig.properties");
		restTemp = properties.getProperty("restTemplate");

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(restTemp, String.class) + ":"
				+ getPort();

	}

	public String getLocalMachineName(){

		String hostName;

        try{

            hostName = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e) {
            log.error(Logging.errorMsg("Failed to set localhost address to ip const",e));
            hostName = "localhost";
        }

return hostName+":"+getPort();

	}

	public String getPort() {

		return serverProperties.getPort().toString();

	}

}
