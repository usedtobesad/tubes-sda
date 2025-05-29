import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    String id;
    String name;
    String speciality;
    String password;
    LocalDateTime LoginTime;

    public Doctor(String id, String name, String speciality, String password, LocalDateTime LoginTime) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.password = password;
        this.LoginTime = LoginTime;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpeciality() { return speciality; }
    public String getPassword() { return password; }
    public LocalDateTime getLoginTime() { return LoginTime; }

    @Override
    public String toString() {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("dd-MM-yyyy (HH:mm:ss)");
        return "Name: " + name + "[" + id + "]\n" + 
               "Speciality: " + speciality + "\n" +
               "Login Time: " + LoginTime.format(time);
    }
}
