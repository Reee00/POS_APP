package dao;

import model.Barang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Barang (Product/Item) operations.
 */
public class BarangDAO {

    /**
     * Retrieves all barang from the database, joined with supplier name.
     */
    public List<Barang> getAll() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.*, s.nama_supplier FROM barang b " +
                     "LEFT JOIN supplier s ON b.id_supplier = s.id_supplier " +
                     "ORDER BY b.id_barang DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Searches barang by keyword in kode, nama, or kategori.
     */
    public List<Barang> search(String keyword) throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.*, s.nama_supplier FROM barang b " +
                     "LEFT JOIN supplier s ON b.id_supplier = s.id_supplier " +
                     "WHERE b.kode_barang LIKE ? OR b.nama_barang LIKE ? OR b.kategori LIKE ? " +
                     "ORDER BY b.id_barang DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        }
        return list;
    }

    /**
     * Inserts a new barang record.
     */
    public boolean insert(Barang b) throws SQLException {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, kategori, harga_beli, harga_jual, stok, satuan, id_supplier) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setDouble(4, b.getHargaBeli());
            ps.setDouble(5, b.getHargaJual());
            ps.setInt(6, b.getStok());
            ps.setString(7, b.getSatuan());
            if (b.getIdSupplier() > 0) {
                ps.setInt(8, b.getIdSupplier());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing barang record.
     */
    public boolean update(Barang b) throws SQLException {
        String sql = "UPDATE barang SET kode_barang=?, nama_barang=?, kategori=?, harga_beli=?, " +
                     "harga_jual=?, stok=?, satuan=?, id_supplier=? WHERE id_barang=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setDouble(4, b.getHargaBeli());
            ps.setDouble(5, b.getHargaJual());
            ps.setInt(6, b.getStok());
            ps.setString(7, b.getSatuan());
            if (b.getIdSupplier() > 0) {
                ps.setInt(8, b.getIdSupplier());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.setInt(9, b.getIdBarang());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a barang record by ID.
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id_barang=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Gets count of total barang.
     */
    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM barang";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    /**
     * Gets count of barang with low stock (< 5).
     */
    public int getLowStockCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM barang WHERE stok < 5";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    /**
     * Gets all barang with low stock for critical display.
     */
    public List<Barang> getLowStock() throws SQLException {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.*, s.nama_supplier FROM barang b " +
                     "LEFT JOIN supplier s ON b.id_supplier = s.id_supplier " +
                     "WHERE b.stok < 5 ORDER BY b.stok ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Updates stock after a sale (decrements).
     */
    public boolean updateStock(int idBarang, int jumlahTerjual) throws SQLException {
        String sql = "UPDATE barang SET stok = stok - ? WHERE id_barang = ? AND stok >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlahTerjual);
            ps.setInt(2, idBarang);
            ps.setInt(3, jumlahTerjual);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Gets a single barang by ID.
     */
    public Barang getById(int id) throws SQLException {
        String sql = "SELECT b.*, s.nama_supplier FROM barang b " +
                     "LEFT JOIN supplier s ON b.id_supplier = s.id_supplier " +
                     "WHERE b.id_barang = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Maps a ResultSet row to a Barang object.
     */
    private Barang mapResultSet(ResultSet rs) throws SQLException {
        Barang b = new Barang();
        b.setIdBarang(rs.getInt("id_barang"));
        b.setKodeBarang(rs.getString("kode_barang"));
        b.setNamaBarang(rs.getString("nama_barang"));
        b.setKategori(rs.getString("kategori"));
        b.setHargaBeli(rs.getDouble("harga_beli"));
        b.setHargaJual(rs.getDouble("harga_jual"));
        b.setStok(rs.getInt("stok"));
        b.setSatuan(rs.getString("satuan"));
        b.setIdSupplier(rs.getInt("id_supplier"));
        b.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            b.setNamaSupplier(rs.getString("nama_supplier"));
        } catch (SQLException e) {
            b.setNamaSupplier("-");
        }
        return b;
    }
}
