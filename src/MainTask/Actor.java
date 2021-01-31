package MainTask;

import java.util.Date;

public class Actor {
    private String name;
    private Date dateBirthday;

    public Actor(String name, Date dateBirthday) {
        this.name = name;
        this.dateBirthday = dateBirthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", dateBirthday=" + dateBirthday +
                '}';
    }
}
