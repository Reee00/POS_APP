package dao;

import model.Supplier;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Supplier operations.
 */
public class SupplierDAO {

    public List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier ORDER BY id_supplier DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public List<Supplier> search(String keyword) throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE kode_supplier LIKE ? OR nama_supplier LIKE ? " +
                     "OR telepon LIKE ? OR email LIKE ? ORDER BY id_supplier DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        }
        return list;
    }

    public boolean insert(Supplier s) throws SQLException {
        String sql = "INSERT INTO supplier (kode_supplier, nama_supplier, telepon, alamat, email) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getKodeSupplier());
            ps.setString(2, s.getNamaSupplier());
            ps.setString(3, s.getTelepon());
            ps.setString(4, s.getAlamat());
            ps.setString(5, s.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Supplier s) throws SQLException {
        String sql = "UPDATE supplier SET kode_supplier=?, nama_supplier=?, telepon=?, " +
                     "alamat=?, email=? WHERE id_supplier=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getKodeSupplier());
            ps.setString(2, s.getNamaSupplier());
            ps.setString(3, s.getTelepon());
            ps.setString(4, s.getAlamat());
            ps.setString(5, s.getEmail());
            ps.setInt(6, s.getIdSupplier());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE id_supplier=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM supplier";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Supplier mapResultSet(ResultSet rs) throws SQLException {
        Supplier s = new Supplier();
        s.setIdSupplier(rs.getInt("id_supplier"));
        s.setKodeSupplier(rs.getString("kode_supplier"));
        s.setNamaSupplier(rs.getString("nama_supplier"));
        s.setTelepon(rs.getString("telepon"));
        s.setAlamat(rs.getString("alamat"));
        s.setEmail(rs.getString("email"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }
}
