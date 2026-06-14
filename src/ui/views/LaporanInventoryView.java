package ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import util.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory log report view showing all stock in/out movements.
 */
public class LaporanInventoryView {

    private VBox view;
    private TableView<InventoryLogEntry> table;
    private TextField searchField;

    public LaporanInventoryView() {
        buildView();
        loadData();
    }

    public Node getView() { return view; }

    private void buildView() {
        view = new VBox(16);

        // Top bar
        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.getStyleClass().add("search-field");
        searchField.setPromptText("\uD83D\uDD0D Cari log inventory...");
        searchField.setOnKeyReleased(e -> searchData());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label infoLabel = new Label("\uD83D\uDCCA  Log Pergerakan Stok");
        infoLabel.getStyleClass().add("section-title");

        topBar.getChildren().addAll(searchField, spacer, infoLabel);

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<InventoryLogEntry, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idLog"));
        colId.setMaxWidth(60);

        TableColumn<InventoryLogEntry, String> colBarang = new TableColumn<>("Nama Barang");
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));

        TableColumn<InventoryLogEntry, String> colJenis = new TableColumn<>("Jenis");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colJenis.setMaxWidth(100);
        colJenis.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                Label badge = new Label(item.toUpperCase());
                badge.getStyleClass().addAll("badge",
                    item.equalsIgnoreCase("masuk") ? "badge-success" : "badge-danger");
                setGraphic(badge);
            }
        });

        TableColumn<InventoryLogEntry, Integer> colJumlah = new TableColumn<>("Jumlah");
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colJumlah.setMaxWidth(80);

        TableColumn<InventoryLogEntry, String> colKeterangan = new TableColumn<>("Keterangan");
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        TableColumn<InventoryLogEntry, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalFormatted"));
        colTanggal.setMaxWidth(160);

        table.getColumns().addAll(colId, colBarang, colJenis, colJumlah, colKeterangan, colTanggal);

        view.getChildren().addAll(topBar, table);
    }

    private void loadData() {
        Task<List<InventoryLogEntry>> task = new Task<>() {
            @Override protected List<InventoryLogEntry> call() throws Exception {
                return fetchLogs("");
            }
        };
        task.setOnSucceeded(e -> {
            table.setItems(FXCollections.observableArrayList(task.getValue()));
        });
        new Thread(task).start();
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        Task<List<InventoryLogEntry>> task = new Task<>() {
            @Override protected List<InventoryLogEntry> call() throws Exception {
                return fetchLogs(keyword);
            }
        };
        task.setOnSucceeded(e -> {
            table.setItems(FXCollections.observableArrayList(task.getValue()));
        });
        new Thread(task).start();
    }

    private List<InventoryLogEntry> fetchLogs(String keyword) throws SQLException {
        List<InventoryLogEntry> list = new ArrayList<>();
        String sql;
        if (keyword.isEmpty()) {
            sql = "SELECT il.*, b.nama_barang FROM inventory_log il " +
                  "JOIN barang b ON il.id_barang = b.id_barang ORDER BY il.id_log DESC";
        } else {
            sql = "SELECT il.*, b.nama_barang FROM inventory_log il " +
                  "JOIN barang b ON il.id_barang = b.id_barang " +
                  "WHERE b.nama_barang LIKE ? OR il.keterangan LIKE ? OR il.jenis LIKE ? " +
                  "ORDER BY il.id_log DESC";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try (Connection conn = DBConnection.getConnection()) {
            if (keyword.isEmpty()) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        list.add(mapEntry(rs, sdf));
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    String kw = "%" + keyword + "%";
                    ps.setString(1, kw);
                    ps.setString(2, kw);
                    ps.setString(3, kw);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            list.add(mapEntry(rs, sdf));
                        }
                    }
                }
            }
        }
        return list;
    }

    private InventoryLogEntry mapEntry(ResultSet rs, SimpleDateFormat sdf) throws SQLException {
        InventoryLogEntry entry = new InventoryLogEntry();
        entry.setIdLog(rs.getInt("id_log"));
        entry.setNamaBarang(rs.getString("nama_barang"));
        entry.setJenis(rs.getString("jenis"));
        entry.setJumlah(rs.getInt("jumlah"));
        entry.setKeterangan(rs.getString("keterangan"));
        Timestamp ts = rs.getTimestamp("tanggal");
        entry.setTanggalFormatted(ts != null ? sdf.format(ts) : "-");
        return entry;
    }

    /**
     * Inner model class for inventory log display.
     */
    public static class InventoryLogEntry {
        private int idLog;
        private String namaBarang;
        private String jenis;
        private int jumlah;
        private String keterangan;
        private String tanggalFormatted;

        public int getIdLog() { return idLog; }
        public void setIdLog(int idLog) { this.idLog = idLog; }
        public String getNamaBarang() { return namaBarang; }
        public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
        public String getJenis() { return jenis; }
        public void setJenis(String jenis) { this.jenis = jenis; }
        public int getJumlah() { return jumlah; }
        public void setJumlah(int jumlah) { this.jumlah = jumlah; }
        public String getKeterangan() { return keterangan; }
        public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
        public String getTanggalFormatted() { return tanggalFormatted; }
        public void setTanggalFormatted(String tanggalFormatted) { this.tanggalFormatted = tanggalFormatted; }
    }
}
