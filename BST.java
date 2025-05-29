package tubes;

public class BST {
    private class Node {
        Patient patient;
        Node left, right;

        Node(Patient patient) {
            this.patient = patient;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BST() {
        this.root = null;
    }

    public void insert(Patient patient) {
        root = insertRec(root, patient);
    }

    private Node insertRec(Node root, Patient patient) {
        if (root == null) {
            return new Node(patient);
        }
        if (patient.getId().compareTo(root.patient.getId()) < 0) {
            root.left = insertRec(root.left, patient);
        } else if (patient.getId().compareTo(root.patient.getId()) > 0) {
            root.right = insertRec(root.right, patient);
        }
        return root;
    }

    public void remove(String id) {
        root = removeRec(root, id);
    }

    private Node removeRec(Node root, String id) {
        if (root == null) return null;
        if (id.compareTo(root.patient.getId()) < 0) {
            root.left = removeRec(root.left, id);
        } else if (id.compareTo(root.patient.getId()) > 0) {
            root.right = removeRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            root.patient = minValue(root.right);
            root.right = removeRec(root.right, root.patient.getId());
        }
        return root;
    }

    private Patient minValue(Node root) {
        Patient min = root.patient;
        while (root.left != null) {
            min = root.left.patient;
            root = root.left;
        }
        return min;
    }

    public Patient searchById(String id) {
        return searchRec(root, id);
    }

    private Patient searchRec(Node root, String id) {
        if (root == null || root.patient.getId().equals(id)) {
            return root != null ? root.patient : null;
        }
        if (id.compareTo(root.patient.getId()) < 0) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }

    public void inOrderDisplay() {
        inOrderRec(root);
        if (root == null) {
            System.out.println("No patients in BST.");
        }
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.patient);
            inOrderRec(root.right);
        }
    }
}