package tubes;

import java.io.*;

public class LinkedList {
    public class Node {
        Patient patient;
        Node next;

        Node(Patient patient) {
            this.patient = patient;
            this.next = null;
        }
    }

    public Node head;

    public LinkedList() {
        head = null;
    }

    public boolean isIdUnique(String id) {
        String fullId = id.startsWith("P") ? id : "P" + id;
        Node current = head;
        while (current != null) {
            if (current.patient.getId().equals(fullId)) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    public void addPatient(Patient patient) {
        Node newNode = new Node(patient);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        save("patient.txt");
    }

    public boolean removePatientById(String id) {
        if (head == null) {
            return false;
        }
        if (head.patient.getId().equals(id)) {
            head = head.next;
            save("patient.txt");
            return true;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.patient.getId().equals(id)) {
                current.next = current.next.next;
                save("patient.txt");
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void findPatientByName(String name) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.patient.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(current.patient);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No patient found with name: " + name);
        }
    }

    public Patient getPatientById(String id) {
        Node current = head;
        while (current != null) {
            if (current.patient.getId().equals(id)) {
                return current.patient;
            }
            current = current.next;
        }
        return null;
    }

    public void displayAllPatients() {
        if (head == null) {
            System.out.println("No patients registered.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println(current.patient);
            current = current.next;
        }
    }

    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                Patient p = current.patient;
                String line = String.format("%s|%s|%d|%s|%s", p.getId(), p.getName(), p.getAge(), p.getAddress(), p.getPhoneNumber());
                writer.write(line);
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving patients to TXT: " + e.getMessage());
        }
    }

    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            head = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String id = parts[0].substring(1); // Remove "P"
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String address = parts[3];
                    String phoneNumber = parts[4];
                    addPatient(new Patient(id, name, age, address, phoneNumber));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patients from TXT: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing patient data: " + e.getMessage());
        }
    }
}