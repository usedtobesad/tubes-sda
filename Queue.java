package tubes;

import java.io.*;

public class Queue {
    public class Node {
        Appointment appointment;
        Node next;

        Node(Appointment appointment) {
            this.appointment = appointment;
            this.next = null;
        }
    }

    private Node front, rear;
    private LinkedList patientList;
    private Stack doctorStack;

    public Queue(LinkedList patientList, Stack doctorStack) {
        this.front = null;
        this.rear = null;
        this.patientList = patientList;
        this.doctorStack = doctorStack;
    }

    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            front = rear = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String appointmentId = parts[0];
                    String patientId = parts[1];
                    String doctorId = parts[2];
                    String date = parts[3];
                    Appointment appointment = new Appointment(appointmentId, patientId, doctorId, date);
                    enqueue(appointment);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments from TXT: " + e.getMessage());
        }
    }

    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = front;
            while (current != null) {
                Appointment appointment = current.appointment;
                String line = String.format("%s|%s|%s|%s",
                        appointment.getAppointmentId(), appointment.getPatientId(),
                        appointment.getDoctorId(), appointment.getDate());
                writer.write(line);
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments to TXT: " + e.getMessage());
        }
    }

    public void enqueue(Appointment appointment) {
        Node newNode = new Node(appointment);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        save("appointment.txt");
    }

    public Appointment dequeue() {
        if (front == null) return null;
        Appointment appointment = front.appointment;
        front = front.next;
        if (front == null) rear = null;
        save("appointment.txt");
        return appointment;
    }

    public boolean isAppointmentIdUnique(String id) {
        String fullId = id.startsWith("A") ? id : "A" + id;
        Node current = front;
        while (current != null) {
            if (current.appointment.getAppointmentId().equals(fullId)) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    public void displayQueue() {
        if (front == null) {
            System.out.println("No appointments available.");
            return;
        }
        Node current = front;
        while (current != null) {
            System.out.println(current.appointment.toString(patientList, doctorStack));
            current = current.next;
        }
    }
}