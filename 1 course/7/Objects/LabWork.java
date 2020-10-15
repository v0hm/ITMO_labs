import java.io.Serializable;
import java.time.LocalDate;

public class LabWork extends Client implements Comparable<LabWork>, Serializable {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float minimalPoint; //Поле может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле не может быть null
    private Person author; //Поле может быть null

    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", difficulty=" + difficulty +
                ", author=" + author +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Float getMinimalPoint() {
        return minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Person getAuthor() {
        return author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setMinimalPoint(Float minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public int compareTo(LabWork labWork) {
        if (labWork.id == null) {
            if (id != null)
                return 1;
        }
        else
            if (id != null)
                return id.compareTo(labWork.id);

        if (labWork.name == null) {
            if (name != null)
                return 1;
        }
        else
            if (name != null && name.compareTo(labWork.name) != 0)
                return name.compareTo(labWork.name);

        if (labWork.coordinates == null) {
            if (coordinates != null)
                return 1;
        }
        else
            if (coordinates != null && coordinates.compareTo(labWork.coordinates) != 0)
                return coordinates.compareTo(labWork.coordinates);

        if (labWork.creationDate == null){
            if (creationDate != null)
                return 1;
            else
            {
                if (creationDate != null && creationDate.compareTo(labWork.creationDate) != 0)
                {
                    return creationDate.compareTo(labWork.creationDate);
                }
            }
        }
        if (labWork.minimalPoint == null){
            if (minimalPoint != null)
                return 1;
            else
            {
                if (minimalPoint != null && minimalPoint.compareTo(labWork.minimalPoint) != 0)
                {
                    return minimalPoint.compareTo(labWork.minimalPoint);
                }
            }
        }
        if (labWork.difficulty == null){
            if (difficulty != null)
                return 1;
            else
            {
                if (difficulty != null && difficulty.compareTo(labWork.difficulty) != 0)
                {
                    return difficulty.compareTo(labWork.difficulty);
                }
            }
        }
        if (labWork.author == null){
            if (author != null)
                return 1;
            else
            {
                if (author != null && author.compareTo(labWork.author) != 0)
                {
                    return author.compareTo(labWork.author);
                }
            }
        }
        return 0;
    }
}