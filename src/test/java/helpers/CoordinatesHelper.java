package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Coordinates;

public class CoordinatesHelper {

    public static Coordinates getCoordinates(String city) {
        return new Coordinates(getLongitude(city), getLatitude(city));
    }

    private static Response shouldGetCoordinatesResponse(String city) {
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

    private static String getLongitude(String city) {
        return shouldGetCoordinatesResponse(city).path("[0].lon").toString();
    }

    private static String getLatitude(String city) {
        return shouldGetCoordinatesResponse(city).path("[0].lat").toString();
    }


}
