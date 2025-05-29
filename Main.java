package tubes;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    static void clearScreen() {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    static void waitForEnter(Scanner sc) {
        System.out.print("Hit Enter to continue...");
        sc.nextLine();
    }

    static void loadData(LinkedList patientList, Stack doctorStack, Queue appointmentQueue, BST patientBST) {
        patientList.load("patient.txt");
        doctorStack.load("doctor.txt");
        appointmentQueue.load("appointment.txt");
        LinkedList.Node current = patientList.head; // Pastikan head bisa diakses
        while (current != null) {
            patientBST.insert(current.patient); // Hapus casting karena tipe sudah sesuai
            current = current.next;
        }
    }

    static void addPatient(Scanner sc, LinkedList patientList, BST patientBST) {
        System.out.print("Enter ID (numbers only): ");
        String id = sc.nextLine();
        if (!id.matches("\\d+")) {
            System.out.println("Invalid ID. Must be a positive number.");
            return;
        }
        if (!patientList.isIdUnique(id)) {
            System.out.println("Error: ID P" + id + " already exists.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
            System.out.println("Error: Name must contain only letters and spaces.");
            return;
        }
        System.out.print("Enter Age: ");
        int age;
        try {
            age = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Must be a number.");
            return;
        }
        if (age < 0 || age > 100) {
            System.out.println("Error: Age must be between 0 and 100.");
            return;
        }
        System.out.print("Enter Address: ");
        String address = sc.nextLine();
        if (address == null || address.trim().isEmpty()) {
            System.out.println("Error: Address cannot be empty.");
            return;
        }
        System.out.print("Enter Phone Number: ");
        String phoneNumber = sc.nextLine();
        if (!phoneNumber.matches("\\d{10,13}")) {
            System.out.println("Error: Phone number must be 10-13 digits.");
            return;
        }
        Patient patient = new Patient(id, name, age, address, phoneNumber);
        patientList.addPatient(patient);
        patientBST.insert(patient);
        System.out.println("Patient added successfully:\n" + patient);
    }

    static void removePatient(Scanner sc, LinkedList patientList, BST patientBST) {
        System.out.print("Enter Patient ID (numbers only): ");
        String removeId = sc.nextLine();
        if (!removeId.matches("\\d+")) {
            System.out.println("Invalid ID. Must be a number.");
            return;
        }
        String patientId = "P" + removeId;
        if (patientList.removePatientById(patientId)) {
            patientBST.remove(patientId);
            System.out.println("Patient with id " + patientId + " removed successfully.");
        } else {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
    }

    static void searchPatientByName(Scanner sc, LinkedList patientList) {
        System.out.print("Enter name to search: ");
        String searchName = sc.nextLine();
        if (searchName.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        patientList.findPatientByName(searchName);
    }

    static void displayAllPatients(LinkedList patientList) {
        patientList.displayAllPatients();
    }

    static void registerDoctor(Scanner sc, Stack doctorStack) {
        System.out.print("Enter Doctor ID (numbers only): ");
        String docId = sc.nextLine();
        if (!docId.matches("\\d+")) {
            System.out.println("Invalid ID. Must be a number.");
            return;
        }
        String doctorId = "D" + docId;
        if (doctorStack.getDoctorById(doctorId) != null) {
            System.out.println("Error: Doctor ID " + doctorId + " already exists.");
            return;
        }
        System.out.print("Enter Doctor Name: ");
        String docName = sc.nextLine();
        if (docName == null || docName.trim().isEmpty() || !docName.matches("[a-zA-Z\\s]+")) {
            System.out.println("Error: Name must contain only letters and spaces.");
            return;
        }
        System.out.print("Enter Speciality: ");
        String speciality = sc.nextLine();
        if (speciality == null || speciality.trim().isEmpty()) {
            System.out.println("Error: Speciality cannot be empty.");
            return;
        }
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Error: Password cannot be empty.");
            return;
        }
        Doctor doctor = new Doctor(doctorId, docName, speciality, password, LocalDateTime.now());
        doctorStack.registerDoctor(doctor);
        System.out.println("Doctor registered successfully:\n" + doctor);
    }

    static void doctorLogin(Scanner sc, Stack doctorStack) {
        System.out.print("Enter Doctor ID (numbers only): ");
        String docId = sc.nextLine();
        if (!docId.matches("\\d+")) {
            System.out.println("Invalid ID. Must be a number.");
            return;
        }
        String doctorId = "D" + docId;
        System.out.print("Enter Doctor Name: ");
        String docName = sc.nextLine();
        if (docName == null || docName.trim().isEmpty() || !docName.matches("[a-zA-Z\\s]+")) {
            System.out.println("Error: Name must contain only letters and spaces.");
            return;
        }
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Error: Password cannot be empty.");
            return;
        }
        if (doctorStack.validateDoctor(doctorId, docName, password)) {
            Doctor doctor = doctorStack.getDoctorById(doctorId);
            doctorStack.push(doctor);
            System.out.println("Doctor logged in successfully:\n" + doctor);
        } else {
            System.out.println("Login failed: Invalid ID, name, or password.");
        }
    }

    static void doctorLogout(Stack doctorStack) {
        Doctor loggedOut = doctorStack.pop();
        if (loggedOut == null) {
            System.out.println("No doctors logged in.");
        } else {
            System.out.println("Doctor logged out:\n" + loggedOut);
        }
    }

    static void viewLastLoggedInDoctor(Stack doctorStack) {
        Doctor lastDoctor = doctorStack.peek();
        if (lastDoctor == null) {
            System.out.println("No doctors logged in.");
        } else {
            System.out.println("Last logged-in doctor:\n" + lastDoctor);
        }
    }

    static void scheduleAppointment(Scanner sc, Queue appointmentQueue, LinkedList patientList, Stack doctorStack) {
        System.out.print("Enter Appointment ID (numbers only): ");
        String apptId = sc.nextLine();
        if (!apptId.matches("\\d+")) {
            System.out.println("Invalid Appointment ID. Must be a number.");
            return;
        }
        if (!appointmentQueue.isAppointmentIdUnique(apptId)) {
            System.out.println("Error: Appointment ID A" + apptId + " already exists.");
            return;
        }
        System.out.print("Enter Patient ID (numbers only): ");
        String patientId = sc.nextLine();
        if (!patientId.matches("\\d+")) {
            System.out.println("Invalid Patient ID. Must be a number.");
            return;
        }
        Patient patientExists = patientList.getPatientById("P" + patientId); // Hapus casting
        if (patientExists == null) {
            System.out.println("Error: Patient with ID P" + patientId + " does not exist.");
            return;
        }
        System.out.print("Enter Doctor ID (numbers only): ");
        String doctorId = sc.nextLine();
        if (!doctorId.matches("\\d+")) {
            System.out.println("Invalid Doctor ID. Must be a number.");
            return;
        }
        Doctor doctorExists = doctorStack.getDoctorById("D" + doctorId); // Hapus casting
        if (doctorExists == null) {
            System.out.println("Error: Doctor with ID D" + doctorId + " does not exist");
            return;
        }
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();
        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            System.out.println("Error: Date must be in DD-MM-YYYY format.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate appointmentDate;
        try {
            appointmentDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();
            if (appointmentDate.isBefore(currentDate)) {
                System.out.println("Appointment date must be in the future!!");
                return;
            }
            int day = appointmentDate.getDayOfMonth();
            int month = appointmentDate.getMonthValue();
            int year = appointmentDate.getYear();
            if (day > YearMonth.of(year, month).lengthOfMonth()) {
                System.out.println("Invalid date: " + date + " (invalid day for month).");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Please use DD-MM-YYYY format with valid date values.");
            return;
        }
        Appointment appointment = new Appointment("A" + apptId, "P" + patientId, "D" + doctorId, date);
        appointmentQueue.enqueue(appointment);
        System.out.println("Appointment scheduled successfully:\n" + appointment.toString(patientList, doctorStack));
    }

    static void processAppointment(Scanner sc, Queue appointmentQueue) {
        Appointment processed = appointmentQueue.dequeue();
        if (processed == null) {
            System.out.println("No appointments to process.");
        } else {
            System.out.println("Processed appointment:\n" + processed);
        }
    }

    static void displayUpcomingAppointments(Queue appointmentQueue) {
        appointmentQueue.displayQueue();
    }

    static void searchPatientById(Scanner sc, BST patientBST) {
        System.out.print("Enter Patient ID (numbers only): ");
        String searchId = sc.nextLine();
        if (!searchId.matches("\\d+")) {
            System.out.println("Invalid ID. Must be a number.");
            return;
        }
        String patientId = "P" + searchId;
        Patient foundPatient = patientBST.searchById(patientId); // Hapus casting
        if (foundPatient == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
        } else {
            System.out.println("Found patient:\n" + foundPatient);
        }
    }

    static void displayAllPatientsBST(BST patientBST) {
        patientBST.inOrderDisplay();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList patientList = new LinkedList();
        Stack doctorStack = new Stack();
        Queue appointmentQueue = new Queue(patientList, doctorStack);
        BST patientBST = new BST();

        loadData(patientList, doctorStack, appointmentQueue, patientBST);

        while (true) {
            clearScreen();
            System.out.println("\n=== Data Manager Pro ===");
            System.out.println("1. Add New Patient");
            System.out.println("2. Remove Patient by ID");
            System.out.println("3. Search Patient by Name");
            System.out.println("4. Display All Patients");
            System.out.println("5. Register Doctor");
            System.out.println("6. Doctor Login");
            System.out.println("7. Doctor Logout");
            System.out.println("8. View Last Logged-in Doctor");
            System.out.println("9. Schedule Appointment");
            System.out.println("10. Process Appointment");
            System.out.println("11. Display Upcoming Appointments");
            System.out.println("12. Search Patient by ID (BST)");
            System.out.println("13. Display All Patients (BST Inorder)");
            System.out.println("14. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid option.");
                continue;
            }

            switch (choice) {
                case 1:
                    clearScreen();
                    addPatient(scanner, patientList, patientBST);
                    waitForEnter(scanner);
                    break;
                case 2:
                    clearScreen();
                    removePatient(scanner, patientList, patientBST);
                    waitForEnter(scanner);
                    break;
                case 3:
                    clearScreen();
                    searchPatientByName(scanner, patientList);
                    waitForEnter(scanner);
                    break;
                case 4:
                    clearScreen();
                    displayAllPatients(patientList);
                    waitForEnter(scanner);
                    break;
                case 5:
                    clearScreen();
                    registerDoctor(scanner, doctorStack);
                    waitForEnter(scanner);
                    break;
                case 6:
                    clearScreen();
                    doctorLogin(scanner, doctorStack);
                    waitForEnter(scanner);
                    break;
                case 7:
                    clearScreen();
                    doctorLogout(doctorStack);
                    waitForEnter(scanner);
                    break;
                case 8:
                    clearScreen();
                    viewLastLoggedInDoctor(doctorStack);
                    waitForEnter(scanner);
                    break;
                case 9:
                    clearScreen();
                    scheduleAppointment(scanner, appointmentQueue, patientList, doctorStack);
                    waitForEnter(scanner);
                    break;
                case 10:
                    clearScreen();
                    processAppointment(scanner, appointmentQueue);
                    waitForEnter(scanner);
                    break;
                case 11:
                    clearScreen();
                    displayUpcomingAppointments(appointmentQueue);
                    waitForEnter(scanner);
                    break;
                case 12:
                    clearScreen();
                    searchPatientById(scanner, patientBST);
                    waitForEnter(scanner);
                    break;
                case 13:
                    clearScreen();
                    displayAllPatientsBST(patientBST);
                    waitForEnter(scanner);
                    break;
                case 14:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}