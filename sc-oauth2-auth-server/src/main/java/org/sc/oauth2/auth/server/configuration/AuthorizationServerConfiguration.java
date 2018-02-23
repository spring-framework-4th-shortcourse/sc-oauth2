package org.sc.oauth2.auth.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public TokenStore tokenStore(){
		return new InMemoryTokenStore();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
				.withClient("check_token").secret("secret") //this client is used by resource server for check_token endpoint only
		        .authorities("ROLE_CHECK_TOKEN")
		        
		   .and()
		        .withClient("trusted").secret("secret")
		        .authorities("ROLE_TRUSTED_CLIENT")
		        .authorizedGrantTypes("password", "refresh_token")
		        .scopes("trust")
		        .redirectUris("http://localhost:8282/client/")
		        .accessTokenValiditySeconds(60)
		        .refreshTokenValiditySeconds(15*60)
		        .autoApprove(false)
		        
		   .and()
		        .withClient("client-a").secret("secret")
		        .authorities("ROLE_CLIENT_A")
		        .authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials", "refresh_token")
		        .scopes("public_profile", "email", "date_of_birth", "place_of_birth")
		        .redirectUris("http://localhost:8282/client/")
		        .accessTokenValiditySeconds(60)
		        .refreshTokenValiditySeconds(15*60)
		        
	        .and() 
		        .withClient("client-b").secret("secret")
		        .authorities("ROLE_CLIENT_B")
		        .authorizedGrantTypes("authorization_code", "refresh_token")
		        .scopes("public_profile")
		        .redirectUris("http://localhost:8282/client/")
		    ;		
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.checkTokenAccess("isAuthenticated()");
			//.checkTokenAccess("permitAll()");
			//.checkTokenAccess("denyAll()"); //default is denyAll()
	}
	
}
