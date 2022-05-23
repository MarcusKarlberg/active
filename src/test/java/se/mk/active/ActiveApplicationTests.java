package se.mk.active;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.mk.active.controller.UserController;
import se.mk.active.init.DataInitializer;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;

@SpringBootTest
class ActiveApplicationTests {

	//MockBeans for Spring Security
	@MockBean
	private JwtBuilderParserImpl jwtBuilderParser;

	@MockBean
	private SecurityConstants securityConstants;

	@MockBean
	private UserController userController;

	@MockBean
	private UserDetailsService userDetailsService;

	@MockBean
	private BCryptPasswordEncoder encoder;

	@MockBean
	private DataInitializer dataInitializer;

	@Test
	void contextLoads() {
	}

}
