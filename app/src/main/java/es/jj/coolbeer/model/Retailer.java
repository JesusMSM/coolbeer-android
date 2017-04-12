package es.jj.coolbeer.model;

/**
 * Created by Jesus on 11/04/2017.
 */
public class Retailer {

    String name;
    String country;
    String brandName;
    String lat;
    String lng;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Retailer(String name, String country, String brandName, String lat, String lng, String quantity) {

        this.name = name;
        this.country = country;
        this.brandName = brandName;
        this.lat = lat;
        this.lng = lng;
        this.quantity = quantity;
    }

    String quantity;

    public Retailer(String name, String country, String brandName, String lat, String lng) {
        super();
        this.name = name;
        this.country = country;
        this.brandName = brandName;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
