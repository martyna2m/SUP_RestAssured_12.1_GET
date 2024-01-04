package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class WeatherApiTest extends TestBase {


    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/locations.csv", numLinesToSkip = 1)
    void shouldGetWeatherForecast(String city) {
        // System.out.println(Thread.currentThread());

        String appId = System.getProperty("appId");

        log.info("Getting coordinates for {}", city);

        Response coordinatesResponse = RestAssured
                .given()
                .baseUri("http://api.openweathermap.org")
                .param("appid", appId)
                .param("q", city)
                .get(System.getProperty("geoSuffix"))
                .then()
                .statusCode(200)
                .extract().response();

//powyższy kod nie został przeniesiony do oddzielnej metody, bo nabrałam wątpliwości czy mogę wykorzystywać Resta w metodach, które nie są testami


        String lon = coordinatesResponse.path("[0].lon").toString();
        String lat = coordinatesResponse.path("[0].lat").toString();
        log.info("City: " + city + ", lon: " + lon + ", lat: " + lat);

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
