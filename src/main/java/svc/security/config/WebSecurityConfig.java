package svc.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import svc.CustomCorsFilter;

import svc.models.User;
import svc.security.JwtRequestMatcher;
import svc.security.RestAuthenticationEntryPoint;
import svc.security.jwt.JwtAuthenticationManager;
import svc.security.jwt.JwtTokenAuthenticationProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String AUTHENTICATION_URL = "/googleSignin";
	public static final String REFRESH_TOKEN_URL = "/refreshToken";
	public static final String API_ROOT_URL = "/**";
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationManager authenticationManager;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${spring.allowedOrigin}")
	public String allowedOrigin;
	
	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
		JwtRequestMatcher requestMatcher = new JwtRequestMatcher(pathsToSkip, pattern);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(requestMatcher);
	    filter.setAuthenticationManager(this.authenticationManager);
	    return filter;
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(
				AUTHENTICATION_URL,
				REFRESH_TOKEN_URL
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
        	.authorizeRequests()    
        	.antMatchers("/admin/**")
            .hasAuthority(User.ROLES.ADMIN.toString())
         .and()
         	.authorizeRequests()
            .antMatchers("/**")
            .hasAnyAuthority(User.ROLES.USER.toString(), User.ROLES.ADMIN.toString())
        .and()
        	.addFilterBefore(new CustomCorsFilter(allowedOrigin), UsernamePasswordAuthenticationFilter.class)
        	.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
		
		super.configure(http);
	}

}