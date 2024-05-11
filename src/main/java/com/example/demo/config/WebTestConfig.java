package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.provider.ResourceProvider;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;

@Configuration
@Profile("test")
public class WebTestConfig {


	@Autowired
	ResourceProvider resourceProvider;

	@Bean(initMethod = "start", destroyMethod = "stop")
	Server inMemoryH2DatabaseServer() throws SQLException {
			
		    return Server.createTcpServer(this.resourceProvider.getH2ServerParams());

	}

}