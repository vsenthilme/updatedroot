package com.tekclover.wms.api.transaction.config.security;

import com.tekclover.wms.api.transaction.config.dBConfig.DataSourceContextHolder;
import com.tekclover.wms.api.transaction.config.dBConfig.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@SuppressWarnings("deprecation")
@Configuration
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

	private String clientId = "pixeltrice";
	private String clientSecret = "pixeltrice-secret-key";
	
	@Autowired
	PasswordEncoder passwordEncoder;

	private final DataSourceContextHolder dataSourceContextHolder;

	public OAuthConfig(DataSourceContextHolder dataSourceContextHolder) {this.dataSourceContextHolder = dataSourceContextHolder;}

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

    @Bean
	public JwtAccessTokenConverter tokenEnhancer () {
		dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
		JwtAccessTokenConverter jwtConverter = new JwtAccessTokenConverter();
		return jwtConverter;
	}
	
	@Bean
	public JwtTokenStore tokenStore () {
		dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
		return new JwtTokenStore(tokenEnhancer());
	}
	
	@Override
	public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(tokenStore())
			.accessTokenConverter(tokenEnhancer());
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
		clients
			.inMemory()
			.withClient(clientId)
			.secret(passwordEncoder.encode(clientSecret))
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(20000)
			.refreshTokenValiditySeconds(20000);
	}
}