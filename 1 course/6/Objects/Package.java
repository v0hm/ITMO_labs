import java.io.Serializable;
import java.util.Arrays;

public class Package implements Serializable{
    private String command;
    private String message;
    private Long id;
    private int key;
    private Difficulty difficulty;
    private LabWork element;

    @Override
    public String toString() {
        return "Package{" +
                "command='" + command + '\'' +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", key=" + key +
                ", difficulty=" + difficulty +
                ", element=" + element +
                '}';
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public void setElement(LabWork element) {
        this.element = element;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCommand() {
        return command;
    }
    public Long getId() {
        return id;
    }
    public int getKey() {
        return key;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public LabWork getElement() {
        return element;
    }
    public String getMessage() {
        return message;
    }
}