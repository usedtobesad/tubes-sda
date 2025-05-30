

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Stack {
    private class Node {
        Doctor doctor;
        Node next;

        Node(Doctor doctor) {
            this.doctor = doctor;
            this.next = null;
        }
    }

    private class DoctorNode {
        String id;
        Doctor doctor;
        DoctorNode next;

        DoctorNode(String id, Doctor doctor) {
            this.id = id;
            this.doctor = doctor;
            this.next = null;
        }
    }

    Node top;
    DoctorNode registeredDoctorsHead;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Stack() {
        this.top = null;
        this.registeredDoctorsHead = null;
    }

    public void registerDoctor(Doctor doctor) {
        DoctorNode newNode = new DoctorNode(doctor.getId(), doctor);
        newNode.next = registeredDoctorsHead;
        registeredDoctorsHead = newNode;
        save("doctor.txt");
    }

    public void push(Doctor doctor) {
        Node newNode = new Node(doctor);
        newNode.next = top;
        top = newNode;
        save("doctor.txt");
    }

    public Doctor pop() {
        if (isEmpty()) {
            return null;
        }
        Doctor doctor = top.doctor;
        top = top.next;
        save("doctor.txt");
        return doctor;
    }

    public Doctor peek() {
        if (isEmpty()) {
            return null;
        }
        return top.doctor;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public Doctor getDoctorById(String id) {
        Node current = top;
        while (current != null) {
            if (current.doctor.getId().equals(id)) {
                return current.doctor;
            }
            current = current.next;
        }
        return getRegisteredDoctor(id);
    }

    public boolean validateDoctor(String id, String name, String password) {
        Doctor doctor = getRegisteredDoctor(id);
        if (doctor == null) {
            return false;
        }
        return doctor.getName().equals(name) && doctor.getPassword().equals(password);
    }

    public Doctor getRegisteredDoctor(String id) {
        DoctorNode current = registeredDoctorsHead;
        while (current != null) {
            if (current.id.equals(id)) {
                return current.doctor;
            }
            current = current.next;
        }
        return null;
    }

    public void save(String filename) {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            DoctorNode regCurrent = registeredDoctorsHead;
            while (regCurrent != null) {
                Doctor doctor = regCurrent.doctor;
                String line = String.format("%s|%s|%s|%s|%s|registered", doctor.getId(), "Dr. " + doctor.getName(), doctor.getSpeciality(), doctor.getPassword(), doctor.getLoginTime().format(time));
                writer.write(line);
                writer.newLine();
                regCurrent = regCurrent.next;
            }
            Node current = top;
            while (current != null) {
                Doctor doctor = current.doctor;
                String line = String.format("%s|%s|%s|%s|%s|loggedIn", doctor.getId(), "Dr. " + doctor.getName(), doctor.getSpeciality(), doctor.getPassword(), doctor.getLoginTime().format(time));
                writer.write(line);
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors to TXT: " + e.getMessage());
        }
    }

    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            top = null;
            registeredDoctorsHead = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String id = parts[0];
                    String name = parts[1];
                    if (name.startsWith("Dr. ")) {
                        name = name.substring(4);
                    }
                    String speciality = parts[2];
                    String password = parts[3];
                    String loginTimeStr = parts[4];
                    LocalDateTime loginTime = loginTimeStr.equals("Not logged in") ? LocalDateTime.now() : LocalDateTime.parse(loginTimeStr, TIME_FORMATTER);
                    String type = parts[5];
                    Doctor doctor = new Doctor(id, name, speciality, password, loginTime);
                    if (type.equals("registered")) {
                        registerDoctor(doctor);
                    } else if (type.equals("loggedIn")) {
                        push(doctor);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors from TXT: " + e.getMessage());
        }
    }
}