package tests;

import configuration.Config;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    private Config config;
    protected WebDriver driver;
    protected Logger log = LoggerFactory.getLogger(tests.TestBase.class);

    @BeforeEach
    void setUp() {
        config = Config.getInstance();
        driver = config.getDriver();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = System.getProperty("baseUrl");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }
}


