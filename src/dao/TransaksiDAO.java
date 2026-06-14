package dao;

import model.Transaksi;
import model.DetailTransaksi;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaksi and DetailTransaksi operations.
 */
public class TransaksiDAO {

    public List<Transaksi> getAll() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama_customer FROM transaksi t " +
                     "LEFT JOIN customer c ON t.id_customer = c.id_customer " +
                     "ORDER BY t.id_transaksi DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapTransaksi(rs));
            }
        }
        return list;
    }

    public List<Transaksi> getRecent(int limit) throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama_customer FROM transaksi t " +
                     "LEFT JOIN customer c ON t.id_customer = c.id_customer " +
                     "ORDER BY t.tanggal DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTransaksi(rs));
                }
            }
        }
        return list;
    }

    public List<Transaksi> search(String keyword) throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama_customer FROM transaksi t " +
                     "LEFT JOIN customer c ON t.id_customer = c.id_customer " +
                     "WHERE t.no_transaksi LIKE ? OR c.nama_customer LIKE ? " +
                     "ORDER BY t.id_transaksi DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTransaksi(rs));
                }
            }
        }
        return list;
    }

    public List<Transaksi> getByDateRange(String startDate, String endDate) throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama_customer FROM transaksi t " +
                     "LEFT JOIN customer c ON t.id_customer = c.id_customer " +
                     "WHERE DATE(t.tanggal) BETWEEN ? AND ? " +
                     "ORDER BY t.tanggal DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTransaksi(rs));
                }
            }
        }
        return list;
    }

    public int getTodayCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE DATE(tanggal) = CURDATE()";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public double getTodayRevenue() throws SQLException {
        String sql = "SELECT COALESCE(SUM(grand_total), 0) FROM transaksi WHERE DATE(tanggal) = CURDATE()";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }

    public String generateNoTransaksi() throws SQLException {
        String sql = "SELECT no_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String last = rs.getString("no_transaksi");
                // Format: TRX-00001
                String numStr = last.replace("TRX-", "");
                int num = Integer.parseInt(numStr) + 1;
                return String.format("TRX-%05d", num);
            }
        }
        return "TRX-00001";
    }

    /**
     * Saves a complete transaction with details.
     * Uses transaction to ensure atomicity.
     */
    public boolean saveTransaction(Transaksi t) throws SQLException {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Insert transaksi header
            String sqlHeader = "INSERT INTO transaksi (no_transaksi, id_customer, tanggal, " +
                               "total_harga, diskon, grand_total, status, catatan) " +
                               "VALUES (?, ?, NOW(), ?, ?, ?, ?, ?)";
            int transId;
            try (PreparedStatement ps = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, t.getNoTransaksi());
                if (t.getIdCustomer() > 0) {
                    ps.setInt(2, t.getIdCustomer());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setDouble(3, t.getTotalHarga());
                ps.setDouble(4, t.getDiskon());
                ps.setDouble(5, t.getGrandTotal());
                ps.setString(6, t.getStatus() != null ? t.getStatus() : "lunas");
                ps.setString(7, t.getCatatan());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        transId = keys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get transaction ID");
                    }
                }
            }

            // Insert detail items
            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah, harga_satuan, subtotal) " +
                               "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                for (DetailTransaksi d : t.getDetails()) {
                    ps.setInt(1, transId);
                    ps.setInt(2, d.getIdBarang());
                    ps.setInt(3, d.getJumlah());
                    ps.setDouble(4, d.getHargaSatuan());
                    ps.setDouble(5, d.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Update stock and log inventory
            BarangDAO barangDAO = new BarangDAO();
            String sqlLog = "INSERT INTO inventory_log (id_barang, jenis, jumlah, keterangan) " +
                            "VALUES (?, 'keluar', ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlLog)) {
                for (DetailTransaksi d : t.getDetails()) {
                    // Decrement stock
                    String sqlStock = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
                    try (PreparedStatement psStock = conn.prepareStatement(sqlStock)) {
                        psStock.setInt(1, d.getJumlah());
                        psStock.setInt(2, d.getIdBarang());
                        psStock.executeUpdate();
                    }
                    // Log
                    ps.setInt(1, d.getIdBarang());
                    ps.setInt(2, d.getJumlah());
                    ps.setString(3, "Penjualan " + t.getNoTransaksi());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean delete(int id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM detail_transaksi WHERE id_transaksi=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM transaksi WHERE id_transaksi=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<DetailTransaksi> getDetails(int idTransaksi) throws SQLException {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT dt.*, b.nama_barang FROM detail_transaksi dt " +
                     "JOIN barang b ON dt.id_barang = b.id_barang " +
                     "WHERE dt.id_transaksi = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailTransaksi d = new DetailTransaksi();
                    d.setIdDetail(rs.getInt("id_detail"));
                    d.setIdTransaksi(rs.getInt("id_transaksi"));
                    d.setIdBarang(rs.getInt("id_barang"));
                    d.setNamaBarang(rs.getString("nama_barang"));
                    d.setJumlah(rs.getInt("jumlah"));
                    d.setHargaSatuan(rs.getDouble("harga_satuan"));
                    d.setSubtotal(rs.getDouble("subtotal"));
                    list.add(d);
                }
            }
        }
        return list;
    }

    private Transaksi mapTransaksi(ResultSet rs) throws SQLException {
        Transaksi t = new Transaksi();
        t.setIdTransaksi(rs.getInt("id_transaksi"));
        t.setNoTransaksi(rs.getString("no_transaksi"));
        t.setIdCustomer(rs.getInt("id_customer"));
        t.setTanggal(rs.getTimestamp("tanggal"));
        t.setTotalHarga(rs.getDouble("total_harga"));
        t.setDiskon(rs.getDouble("diskon"));
        t.setGrandTotal(rs.getDouble("grand_total"));
        t.setStatus(rs.getString("status"));
        t.setCatatan(rs.getString("catatan"));
        try {
            t.setNamaCustomer(rs.getString("nama_customer"));
        } catch (SQLException e) {
            t.setNamaCustomer("-");
        }
        return t;
    }
}
