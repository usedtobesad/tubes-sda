import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    String id;
    String name;
    String speciality;
    LocalDateTime LoginTime;
}

    public Doctor (String id, String name, String speciality, LocalDateTime LoginTime) {
        this.id = "D" + id;
        this.name = name;
        this.speciality = speciality;
        this.LoginTime = LoginTime;
    }

    public String getId () {return id;}
    public String getName () {return name;}
    public String getSpeciality () {return speciality;}
    public String getLoginTime () {return LoginTime;}

    @Override
    public String toString () {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("dd-MM-yyyy (HH:mm:ss)");
        return "Name: " + name + "[" + id + "]\n" + 
                "Speciality: " + speciality + "\n"
                "Login Time: " + loginTime.format(time);
    }