package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CoordinatesHelper {

    public Response shouldGetCoordinates(String city) {
        String appId = System.getProperty("appId");

        return RestAssured
                .given()
                .baseUri("http://api.openweathermap.org")
                .param("appid", appId)
                .param("q", city)
                .get(System.getProperty("geoSuffix"))
                .then()
                .statusCode(200)
                .extract().response();

    }

    public String getLongitude(String city){
        return shouldGetCoordinates(city).path("[0].lon").toString();
    }
    public String getLatitude(String city){
        return shouldGetCoordinates(city).path("[0].lat").toString();
    }
}
