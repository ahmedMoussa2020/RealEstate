package com.example.demo.provider;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import com.example.demo.provider.factory.YamlPropertySourceFactory;

@Component // annotation to indicate that this class should be treated as a Spring component
// @PropertySource  annotation to specify the location of the configuration file (config.yml
@PropertySource(value = "classpath:config.yml", factory = YamlPropertySourceFactory.class)
public class ResourceProvider {
	
// annotation is used to inject the values of several configuration properties from  the YAML file named config.yml using the ${propertyName}
//${propertyName} format for placeholders. It then stores these values in the instance variables defined in the ResourceProvider class:
@Value("${jwt.secret}")
private String jwtSecret;

@Value("${jwt.expiration}")
private long jwtExpiration;

@Value("${jwt.issuer}")
private String jwtIssuer;
	
@Value("${jwt.audience}")
private String jwtAudience;

@Value("${jwt.prefix}")
private String jwtPrefix;

@Value("${jwt.excluded.urls}")
private String[] jwtExcludedUrls;

@Value("${client.url}")
private String clientUrl;
    
@Value("${client.email.verify.param}")
private String clientVerifyParam;

@Value("${client.email.verify.expiration}")
private long clientVerifyExpiration;
    
@Value("${client.email.reset.param}")
private String clientResetParam;

@Value("${client.email.reset.expiration}")
private long clientResetExpiration;

@Value("${h2.server.params}")
private String[] h2ServerParams;

public String getJwtSecret() {
	return jwtSecret;
}

public long getJwtExpiration() {
	return jwtExpiration;
}

public String getJwtIssuer() {
	return jwtIssuer;
}

public String getJwtAudience() {
	return jwtAudience;
}

public String getJwtPrefix() {
	return jwtPrefix;
}

public String[] getJwtExcludedUrls() {
	return jwtExcludedUrls;
}

public String getClientUrl() {
	return clientUrl;
}

public String getClientVerifyParam() {
	return clientVerifyParam;
}

public long getClientVerifyExpiration() {
	return clientVerifyExpiration;
}

public String getClientResetParam() {
	return clientResetParam;
}

public long getClientResetExpiration() {
	return clientResetExpiration;
}

public String[] getH2ServerParams() {
	return h2ServerParams;
}




}