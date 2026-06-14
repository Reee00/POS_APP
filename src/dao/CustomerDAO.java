package dao;

import model.Customer;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Customer operations.
 */
public class CustomerDAO {

    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY id_customer DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public List<Customer> search(String keyword) throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE kode_customer LIKE ? OR nama_customer LIKE ? " +
                     "OR telepon LIKE ? OR email LIKE ? ORDER BY id_customer DESC";
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

    public boolean insert(Customer c) throws SQLException {
        String sql = "INSERT INTO customer (kode_customer, nama_customer, telepon, alamat, email) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getKodeCustomer());
            ps.setString(2, c.getNamaCustomer());
            ps.setString(3, c.getTelepon());
            ps.setString(4, c.getAlamat());
            ps.setString(5, c.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Customer c) throws SQLException {
        String sql = "UPDATE customer SET kode_customer=?, nama_customer=?, telepon=?, " +
                     "alamat=?, email=? WHERE id_customer=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getKodeCustomer());
            ps.setString(2, c.getNamaCustomer());
            ps.setString(3, c.getTelepon());
            ps.setString(4, c.getAlamat());
            ps.setString(5, c.getEmail());
            ps.setInt(6, c.getIdCustomer());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id_customer=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Customer mapResultSet(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setIdCustomer(rs.getInt("id_customer"));
        c.setKodeCustomer(rs.getString("kode_customer"));
        c.setNamaCustomer(rs.getString("nama_customer"));
        c.setTelepon(rs.getString("telepon"));
        c.setAlamat(rs.getString("alamat"));
        c.setEmail(rs.getString("email"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        return c;
    }
}
