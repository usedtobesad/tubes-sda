public class Appointment {
    String appointmentId;
    String patientId;
    String doctorId;
    String date;

    public Appointment(String appointmentId, String patientId, String doctorId, String date) {
        this.appointmentId = appointmentId.startsWith("A") ? appointmentId : "A" + appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    public String getPatientName(LinkedList patientList) {
        if (patientList == null) return "Patient list unavailable";
        Patient patient = patientList.getPatientById(patientId);
        return patient != null ? patient.getName() : "No patient found with ID: " + patientId;
    }

    public String getDoctorName(Stack doctorStack) {
        if (doctorStack == null) return "Doctor stack unavailable";
        Doctor doctor = doctorStack.getDoctorById(doctorId);
        return doctor != null ? doctor.getName() : "No doctor found with ID: " + doctorId;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDate() { return date; }

    public String toString(LinkedList patientList, Stack doctorStack) {
        return "Appointment ID: " + appointmentId + "\n" +
               "Doctor: " + getDoctorName(doctorStack) + " (" + doctorId + ")" + "\n" +
               "Patient: " + getPatientName(patientList) + " (" + patientId + ")" + "\n" +
               "Date: " + date;
    }
}
