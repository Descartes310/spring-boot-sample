package com.smartnjangui.api.configurations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiModel;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${host.full.dns.auth.link}")
    private String authLink;
	
	@ApiModel(description="String in this format: yyyy-MM-dd HH:mm:ss")
	class Timestamp {};
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
//				.tags(tags()[0], tags())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.smartnjangui.api.controllers"))
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(java.sql.Timestamp.class, Timestamp.class)
				.securitySchemes(Collections.singletonList(securitySchema()))
		        .securityContexts(Collections.singletonList(securityContext())).pathMapping("/");
	}
	

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Smart Njangui API").version("1.0.0").build();
    }
	
//	private Tag[] tags() {
//		return new Tag[] {
//				new Tag("auth","Authentication operations"),
//				new Tag("public", "All public operation that do not need authentication"),
//				new Tag("users", "All user related operation (profile)")
//		};
//	}
	
	
	private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
    	List<String> exceptions = new ArrayList<>();
    	exceptions.add("/auth/register"); 
    	exceptions.add("/activate/{token}"); 
    	exceptions.add("/auth/reset-password");  
    	exceptions.add("/auth/forgot-password");
    	List<String> exceptionsWildcard = new ArrayList<>();
    	exceptionsWildcard.add("/public/.*");
    	exceptionsWildcard.add("/auth/login/.*");
    	exceptionsWildcard.add("/auth/register/.*");
        return SecurityContext
        		.builder()
        		.securityReferences(
        				defaultAuth())
        				.forPaths(path -> {
        					if (exceptions.contains(path)) {
        						return false;
        					} else {
        						for(String wildcard: exceptionsWildcard) {
        							if (path.matches(wildcard)) {
        								return false;
        							}
        						}
        						return true;
        					}
        				})
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }
}
