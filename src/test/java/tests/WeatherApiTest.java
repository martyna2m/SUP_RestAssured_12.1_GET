package tests;

import helpers.CoordinatesHelper;
import io.restassured.RestAssured;
import models.Coordinates;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class WeatherApiTest extends TestBase {
    String appId;

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/locations.csv", numLinesToSkip = 1)
    void shouldGetWeatherForecast(String city) {
        appId = System.getProperty("appId");
        Coordinates coordinates = CoordinatesHelper.getCoordinates(city);

        RestAssured
                .given()
                .param("appid", appId)
                .param("lat", coordinates.getLattitude())
                .param("lon", coordinates.getLongitude())
                .when()
                .get(System.getProperty("weatherSuffix"))
                .then()
                .statusCode(200);

    }

}
