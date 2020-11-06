package com.smartnjangui.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{

	private static final String RESOURCE_ID = "smartnjangui-api";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception
	{
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http
		.sessionManagement().disable()
		.csrf().disable()
		.httpBasic().disable()
		.formLogin().disable() // disable form authentication
		.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/**").authenticated()
		.and()
		.logout()
        .and()
		.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	

}
