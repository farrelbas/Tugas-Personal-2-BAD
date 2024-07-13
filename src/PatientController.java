/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JIC
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientController {

    public void addPatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO tb_patient (patient_name, patient_dob, patient_address, patient_nik, patient_gender, patient_inserted_at, patient_last_updated_at, patient_softdel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getName());
            stmt.setDate(2, new java.sql.Date(patient.getDob().getTime()));
            stmt.setString(3, patient.getAddress());
            stmt.setString(4, patient.getNik());
            stmt.setString(5, patient.getGender());
            stmt.setTimestamp(6, new java.sql.Timestamp(patient.getInsertedAt().getTime()));
            stmt.setTimestamp(7, new java.sql.Timestamp(patient.getUpdatedAt().getTime()));
            stmt.setInt(8, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tb_patient SET patient_name = ?, patient_dob = ?, patient_address = ?, patient_nik = ?, patient_gender = ?, patient_last_updated_at = ? WHERE id_patient = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getName());
            stmt.setDate(2, new java.sql.Date(patient.getDob().getTime()));
            stmt.setString(3, patient.getAddress());
            stmt.setString(4, patient.getNik());
            stmt.setString(5, patient.getGender());
            stmt.setTimestamp(6, new java.sql.Timestamp(patient.getUpdatedAt().getTime()));
            stmt.setInt(7, patient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tb_patient SET patient_softdel = 1 WHERE id_patient = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNIKExists(String nik) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT COUNT(*) AS count FROM tb_patient WHERE patient_nik = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nik);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isNIKExists(String nik, int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT COUNT(*) AS count FROM tb_patient WHERE patient_nik = ? AND id_patient <> ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nik);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM tb_patient";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("patient_name"));
                    patient.setDob(rs.getDate("patient_dob"));
                    patient.setAddress(rs.getString("patient_address"));
                    patient.setNik(rs.getString("patient_nik"));
                    patient.setGender(rs.getString("patient_gender"));
                    patient.setInsertedAt(rs.getDate("patient_inserted_at"));
                    patient.setUpdatedAt(rs.getDate("patient_last_updated_at"));
                    patient.setSoftDel(rs.getBoolean("patient_softdel"));
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
