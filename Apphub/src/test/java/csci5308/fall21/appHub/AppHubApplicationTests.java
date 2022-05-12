package csci5308.fall21.appHub;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppHubApplicationTests {

	AppHubApplication appHubApplication;

	@Test
	void contextLoads() {
		appHubApplication = new AppHubApplication();
		assertNotNull(appHubApplication);
	}

}
