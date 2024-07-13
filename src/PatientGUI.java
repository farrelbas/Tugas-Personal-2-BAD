/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JIC
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

public class PatientGUI extends javax.swing.JFrame {

    /**
     * Creates new form PatientGUI
     */
    private JTextField txtName;
    private JTextField txtDOB;
    private JTextField txtAddress;
    private JTextField txtNIK;
    private JTextField txtGender;
    private JTable tablePatients;
    private JDatePickerImpl datePickerDOB;
    private JRadioButton rbMale;
    private JRadioButton rbFemale;
    private ButtonGroup genderGroup;

    public PatientGUI() {
        setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(40, 20, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(120, 20, 280, 25);
        add(txtName);

        JLabel lblDOB = new JLabel("DOB:");
        lblDOB.setBounds(40, 60, 100, 25);
        add(lblDOB);

        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerDOB = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerDOB.setBounds(120, 60, 280, 25);
        add(datePickerDOB);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(40, 100, 100, 25);
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(120, 100, 280, 25);
        add(txtAddress);

        JLabel lblNIK = new JLabel("NIK:");
        lblNIK.setBounds(40, 140, 100, 25);
        add(lblNIK);
        txtNIK = new JTextField();
        txtNIK.setBounds(120, 140, 280, 25);
        add(txtNIK);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(40, 180, 100, 25);
        add(lblGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(120, 180, 70, 25);
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(200, 180, 80, 25);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(40, 220, 100, 25);
        add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(170, 220, 100, 25);
        add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 220, 100, 25);
        add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(170, 480, 120, 25);
        add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        tablePatients = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablePatients);
        scrollPane.setBounds(40, 260, 380, 180);
        add(scrollPane);

        loadPatientData();

        tablePatients.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablePatients.getSelectedRow();
                if (selectedRow != -1) {
                    txtName.setText((String) tablePatients.getValueAt(selectedRow, 1));
                    datePickerDOB.getModel().setDate(
                            Integer.parseInt(((String) tablePatients.getValueAt(selectedRow, 2)).substring(0, 4)),
                            Integer.parseInt(((String) tablePatients.getValueAt(selectedRow, 2)).substring(5, 7)) - 1,
                            Integer.parseInt(((String) tablePatients.getValueAt(selectedRow, 2)).substring(8, 10))
                    );
                    datePickerDOB.getModel().setSelected(true);
                    txtAddress.setText((String) tablePatients.getValueAt(selectedRow, 3));
                    txtNIK.setText((String) tablePatients.getValueAt(selectedRow, 4));

                    String gender = String.valueOf(tablePatients.getValueAt(selectedRow, 5));
                    if (gender.equals("1")) {
                        rbMale.setSelected(true);
                    } else if (gender.equals("0")) {
                        rbFemale.setSelected(true);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addPatient() {
        try {
            // Validasi apakah NIK sudah ada di database
            if (isNIKExists(txtNIK.getText())) {
                JOptionPane.showMessageDialog(this, "NIK already exists in the database.", "Duplicate NIK", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Data pasien baru
            Patient patient = new Patient();
            patient.setName(txtName.getText());
            patient.setDob((Date) datePickerDOB.getModel().getValue());
            patient.setAddress(txtAddress.getText());
            patient.setNik(txtNIK.getText());

            if (rbMale.isSelected()) {
                patient.setGender("1");
            } else if (rbFemale.isSelected()) {
                patient.setGender("2");
            }

            patient.setInsertedAt(new Date());
            patient.setUpdatedAt(new Date());

            PatientController patient_controller = new PatientController();
            patient_controller.addPatient(patient);
            loadPatientData();

            JOptionPane.showMessageDialog(this, "Patient added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private void updatePatient() {
        try {
            int selectedRow = tablePatients.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer) tablePatients.getValueAt(selectedRow, 0);
                if (id != null) {
                    if (isNIKExists(txtNIK.getText(), id)) {
                        JOptionPane.showMessageDialog(this, "NIK already exists in the database.", "Duplicate NIK", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Data pasien yang akan diupdate
                    Patient patient = new Patient();
                    patient.setId(id);
                    patient.setName(txtName.getText());
                    patient.setDob((Date) datePickerDOB.getModel().getValue());
                    patient.setAddress(txtAddress.getText());
                    patient.setNik(txtNIK.getText());

                    if (rbMale.isSelected()) {
                        patient.setGender("1");
                    } else if (rbFemale.isSelected()) {
                        patient.setGender("2");
                    }

                    patient.setUpdatedAt(new Date());

                    PatientController patient_controller = new PatientController();
                    patient_controller.updatePatient(patient);
                    loadPatientData();

                    JOptionPane.showMessageDialog(this, "Patient updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid patient ID.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to update.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private boolean isNIKExists(String nik) {
        try {
            PatientController patient_controller = new PatientController();
            return patient_controller.isNIKExists(nik);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while checking NIK existence: " + ex.getMessage());
            return false;
        }
    }

    private boolean isNIKExists(String nik, int id) {
        try {
            PatientController patient_controller = new PatientController();
            return patient_controller.isNIKExists(nik, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while checking NIK existence: " + ex.getMessage());
            return false;
        }
    }

    private void deletePatient() {
        try {
            int selectedRow = tablePatients.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer) tablePatients.getValueAt(selectedRow, 0);
                if (id != null) {
                    int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        PatientController patient_controller = new PatientController();
                        patient_controller.deletePatient(id);
                        loadPatientData();
                        JOptionPane.showMessageDialog(this, "Patient deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid patient ID.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private void loadPatientData() {
        try {
            PatientController patient_controller = new PatientController();
            List<Patient> patients = patient_controller.getAllPatients();

            List<Patient> activePatients = new ArrayList<>();
            for (Patient patient : patients) {
                if (!patient.isSoftDel()) {
                    activePatients.add(patient);
                } else {
                }
            }

            String[] columnNames = {"ID Patient", "Name", "DOB", "Address", "NIK", "Gender"};
            Object[][] data = new Object[activePatients.size()][6];
            for (int i = 0; i < activePatients.size(); i++) {
                Patient patient = activePatients.get(i);
                data[i][0] = patient.getId();
                data[i][1] = patient.getName();
                data[i][2] = patient.getDob().toString();
                data[i][3] = patient.getAddress();
                data[i][4] = patient.getNik();
                data[i][5] = patient.getGender().equals("1") ? "Male" : "Female";
            }
            tablePatients.setModel(new DefaultTableModel(data, columnNames));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading patient data: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientGUI gui = new PatientGUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(480, 700);
            gui.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
