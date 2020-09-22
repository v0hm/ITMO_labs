import java.util.Objects;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Поле не может быть null

    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public Long getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public int compareTo(Person person) {
        if (person != null) {
            if (person.name == null) {
                if (name != null)
                    return 1;
            } else if (name != null && name.compareTo(person.name) != 0)
                return name.compareTo(person.name);

            if (person.height == null) {
                if (height != null)
                    return 1;
            } else if (height != null && height.compareTo(person.height) != 0)
                return height.compareTo(person.height);

            if (person.weight == null) {
                if (weight != null)
                    return 1;
            } else if (weight != null && weight.compareTo(person.weight) != 0)
                return weight.compareTo(person.weight);

            if (person.passportID == null) {
                if (passportID != null)
                    return 1;
            } else if (passportID != null && passportID.compareTo(person.passportID) != 0)
                return passportID.compareTo(person.passportID);
        }
        else
            return 1;
        return 0;
    }
}
