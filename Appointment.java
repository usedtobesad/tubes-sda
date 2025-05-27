public class Appointment {
    String appointmentId;
    String patientId;
    String doctorId;
    String date;
    LinkedList patientList;
    Stack doctorStack; 

    public Appointment(String appointmentId, String patientId, String doctorId, String date, LinkedList patient, String patientList, Stack doctor) {
        this.appointmentId = "A" + appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = patientId;
        this.patientList = patientList;
        this.doctorStack = doctorStack;
    }

    public String getPatientId() {
        LinkedList.Node current = patientList.head;
        while (current != null) {
            if (current.patient.getId().equals(patientId)) {
                return current.patient.getName();
            }
            current = current.next;
        }
        return "There's no patient with this ID";
    }

    public String getDoctorName() {
        Stack.Node current = doctorStack.top;
        while (current != null) {
            if (current.doctor.getId().equals(doctorId)) {
                return current.doctor.getName();
            }
            current = current.next;
        }
        return "There's no doctor with this ID";
    }

    public String getAppointmentId() {return appointmentId}
    public String getPatientId() {return patientId;}
    public String getDoctorId() {return doctorId;}
    public String getDate() {return date;}

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\n" +
               "Doctor: " + getDoctorName() + " (" + doctorId + ")" + "\n" +
               "Patient: " + getPatientName() + " (" + patientId + ")" + "\n"
               "date: " + date;
    }
}