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
		        .withClient("trusted").secret("secret")
		        .authorities("ROLE_TRUSTED_CLIENT")
		        .authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials", "refresh_token")
		        .scopes("read", "write", "trust")
		        .redirectUris("http://localhost:8282/client/")
		        .accessTokenValiditySeconds(60)
		        .refreshTokenValiditySeconds(15*60)
		        .autoApprove(false)
	        .and()
		        .withClient("client1").secret("secret")
		        .authorities("ROLE_TRUSTED_CLIENT")
		        .authorizedGrantTypes("authorization_code", "refresh_token")
		        .scopes("read")
		        .redirectUris("http://localhost:8282/client/")
		        .accessTokenValiditySeconds(60)
		        .refreshTokenValiditySeconds(15*60)
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
