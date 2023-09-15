package models;

public class Location {
    private Double x; //Поле не может быть null
    private float y;
    private long z;
    private String name; //Поле может быть null

    public Location(Double x, float y, long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * метод compareTo для сравнения параметров локации
     */
    public int compareTo (Location other){
        int xComparison = this.x.compareTo(other.x);
        if (xComparison != 0){
            return xComparison;
        }

        int yComparison = Float.compare(this.y, other.y);
        if (yComparison != 0){
            return yComparison;
        }

        int zComparison = Long.compare(this.z, other.z);
        if (zComparison != 0){
            return zComparison;
        }

        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Location (" +
                "x = " + x +
                ", y = " + y +
                ", z = " + z +
                ", name = " + name +
        ')';
    }
}