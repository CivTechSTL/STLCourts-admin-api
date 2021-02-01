package svc.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class JwtRequestMatcherTest {
	
	@Test
	public void jwtRequestMatcherReturnsTrue() {
		List<String> pathsToSkip = new ArrayList<String>();
		pathsToSkip.add("/skipMe");
		String processingPath = "/**";
		JwtRequestMatcher jwtRequestMatcher = new JwtRequestMatcher(pathsToSkip, processingPath);
		
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("GET", "http://yourstlcourts.com/processMe");
		
		Boolean result = jwtRequestMatcher.matches(mockHttpServletRequest);
		assertThat(result, is(true));
	}
	
	@Test
	public void jwtRequestMatcherReturnsFalse() {
		List<String> pathsToSkip = new ArrayList<String>();
		pathsToSkip.add("/skipMe");
		String processingPath = "/**";
		JwtRequestMatcher jwtRequestMatcher = new JwtRequestMatcher(pathsToSkip, processingPath);
		
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("GET", "http://yourstlcourts.com/skipMe");
		mockHttpServletRequest.setServletPath("/skipMe");
		
		Boolean result = jwtRequestMatcher.matches(mockHttpServletRequest);
		assertThat(result, is(false));
	}

}
