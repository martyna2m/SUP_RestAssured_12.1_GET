package tests;

import configuration.Config;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    private Config config;
    protected Logger log = LoggerFactory.getLogger(tests.TestBase.class);

    @BeforeEach
    void setUp() {
        config = Config.getInstance();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = System.getProperty("baseUrl");
    }

}


