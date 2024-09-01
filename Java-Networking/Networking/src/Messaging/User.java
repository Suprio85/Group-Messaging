package Messaging;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    int id;
    String name;

    User(String clientName) {
        this.id = generateId();
        this.name = clientName;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    private int generateId() {
        do {
            this.id = (int) (Math.floor(Math.random() * Math.random() * 100000));
        } while (this.id == 0);

        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;

        User user = (User) o;
        return this.id == user.id && this.name == user.name;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "User id: " + this.id + " and " + "Name: " + this.name; 
    }

}
