package other.models;

public class Coordinates {
    private Double x; //Поле не может быть null
    private Long y; //Максимальное значение поля: 873, Поле не может быть null

    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    /**
     * метод compareTo для сравнения параметров координат
     */
    public int compareTo(Coordinates other) {
        int xComparison = this.x.compareTo(other.x);
        if (xComparison != 0) {
            return xComparison;
        }
        return this.y.compareTo(other.y);
    }

    @Override
    public String toString() {
        return "Coordinates (" +
                "x = " + x +
                ", y = " + y +
                ')';
    }
}
