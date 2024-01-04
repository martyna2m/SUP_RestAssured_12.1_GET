package tests;

import helpers.CoordinatesHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class WeatherApiTest extends TestBase {
    CoordinatesHelper coordinatesHelper = new CoordinatesHelper();
    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/locations.csv", numLinesToSkip = 1)
    void shouldGetWeatherForecast(String city) {

        String appId = System.getProperty("appId");
        String lon = coordinatesHelper.getLongitude(city);
        String lat = coordinatesHelper.getLatitude(city);

        log.info("Getting weather for {}", city);
        RestAssured
                .given()
                .param("appid", appId)
                .param("lat", lat)
                .param("lon", lon)
                .when()
                .get(System.getProperty("weatherSuffix"))
                .then()
                .statusCode(200);

    }

}
