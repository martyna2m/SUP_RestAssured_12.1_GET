package models;

import lombok.Getter;

@Getter
public class Coordinates {

    private String longitude;
    private String lattitude;

    public Coordinates(String longitude, String lattitude) {
        this.longitude = longitude;
        this.lattitude = lattitude;
    }


}
