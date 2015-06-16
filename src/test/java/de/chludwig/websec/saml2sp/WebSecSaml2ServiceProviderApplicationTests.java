package de.chludwig.websec.saml2sp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebSecSaml2ServiceProviderApplication.class)
@WebAppConfiguration
public class WebSecSaml2ServiceProviderApplicationTests {

	@Test
	public void contextLoads() {
	}

}
