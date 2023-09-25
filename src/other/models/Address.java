package other.models;

public class Address {
    private String street; //Поле не может быть null
    private Location town; //Поле не может быть null

    public Address(String street, Location town) {
        this.street = street;
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Location getTown() {
        return town;
    }

    public void setTown(Location town) {
        this.town = town;
    }

    /**
     * метод compareTo для сравнения параметров адреса
     */
    public int compareTo(Address other){
        int streetComparison = this.street.compareTo(other.street);
        if (streetComparison != 0) {
            return streetComparison;
        }
        return this.town.compareTo(other.town);
    }

    @Override
    public String toString() {
        return "Address (" +
                "street = " + street +
                ", town = " + town +
                ')';
    }
}