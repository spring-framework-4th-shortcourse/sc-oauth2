package org.sc.oauth2.client.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfiguration {

	@Autowired
	private OAuth2ClientContext oAuth2ClientContext;

	@Bean
	public OAuth2RestTemplate oauthRestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceOWnerPasswordCredentials(), oAuth2ClientContext);
		return template;
	}

	// Resource Owner Password Credentials Grant | Support refresh_token
	@Bean
	public OAuth2ProtectedResourceDetails resourceOWnerPasswordCredentials() {
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setClientId("trusted");
		details.setClientSecret("secret");
		details.setUsername("admin");
		details.setPassword("admin");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setScope(Arrays.asList("trust"));
		return details;
	}
	
	// Authorization Code Grant | Support refresh_token
	@Bean
	public OAuth2ProtectedResourceDetails authorizationCode() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setClientId("client-a");
		details.setClientSecret("secret");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		details.setPreEstablishedRedirectUri("http://localhost:8282/client/home");
		details.setScope(Arrays.asList("public_profile", "email", "date_of_birth", "place_of_birth"));
		return details;
	}

	// Client Credentials Grant
	@Bean
	public OAuth2ProtectedResourceDetails clientCredentials() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setClientId("client-a");
		details.setClientSecret("secret");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setScope(Arrays.asList("public_profile", "email", "date_of_birth", "place_of_birth"));
		return details;
	}

	// Implicit Grant (for browser-based application or single page application)
	@Bean
	public OAuth2ProtectedResourceDetails implicit() {
		ImplicitResourceDetails details = new ImplicitResourceDetails();
		details.setClientId("client-a");
		details.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		details.setScope(Arrays.asList("public_profile", "email", "date_of_birth", "place_of_birth"));
		return details;
	}

}
