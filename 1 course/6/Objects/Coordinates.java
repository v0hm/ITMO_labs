public class Coordinates implements Comparable<Coordinates>{
    private Double x; //Значение поля должно быть больше -628
    private Long y; //Поле не может быть null

    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public int compareTo(Coordinates coordinates) {
        if (coordinates != null) {
            if (coordinates.x == null) {
                if (x != null)
                    return 1;
            } else if (x != null)
                return x.compareTo(coordinates.x);

            if (coordinates.y == null) {
                if (y != null)
                    return 1;
            } else if (y != null) {
                return y.compareTo(coordinates.y);
            }
        }
        else
            return 1;

        return 0;
    }
}
