package svc.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import svc.SimpleCORSFilter;
import svc.security.RestAuthenticationEntryPoint;
import svc.security.auth.login.LoginAuthenticationProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String AUTHENTICATION_URL = "/googleSignin";
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	SimpleCORSFilter corsFilter;
	
/*	private LoginAuthenticationProcessingFilter loginFilter() {
		LoginAuthenticationProcessingFilter filter = new LoginAuthenticationProcessingFilter(AUTHENTICATION_URL, objectMapper);
		
		return filter;
	}
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(
				WebSecurityConfig.AUTHENTICATION_URL
	        );
		
		http
			.csrf().disable()
			.exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint)

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll()
            .and()
            	.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
            	//.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
            
            
		
		super.configure(http);
	}

}
