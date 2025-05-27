public class Patient {
    String id;
    String name;
    int age;
    String address;
    String phoneNumber;
}

    public Patient (String id, String name, int age, String address, String PhoneNumber){
        this.id = "P" + id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public int getAge() {return age;}
    public String getAddress() {return address;}
    public String getPhoneNumber() {return phoneNumber;}
    
    @Override
    public String toString () {
        return "name: " + name + " [ID: " + id "]\n" +
               "age: " + age + "\n" +
               "Phone Number: " + phoneNumber + "\n"
               "address: " + address;
    }