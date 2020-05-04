package sk.fri.uniza.api;

public class Location {
    private String id;
    private String Address;
    private String Town;
    private String Country;
    private String GPS;

    public Location(String id, String address, String town, String country, String GPS) {
        this.id = id;
        Address = address;
        Town = town;
        Country = country;
        this.GPS = GPS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

}
