package ui.views;

import dao.BarangDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Barang;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Inventory view showing all products with stock status badges.
 */
public class InventoryView {

    private VBox view;
    private TableView<Barang> table;
    private ObservableList<Barang> data;
    private TextField searchField;
    private final BarangDAO dao = new BarangDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public InventoryView() {
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
        searchField.setPromptText("\uD83D\uDD0D Cari barang...");
        searchField.setOnKeyReleased(e -> searchData());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label infoLabel = new Label("\uD83D\uDCCA  Monitoring Stok Barang");
        infoLabel.getStyleClass().add("section-title");

        topBar.getChildren().addAll(searchField, spacer, infoLabel);

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Barang, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeBarang"));
        colKode.setMaxWidth(100);

        TableColumn<Barang, String> colNama = new TableColumn<>("Nama Barang");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));

        TableColumn<Barang, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colKategori.setMaxWidth(120);

        TableColumn<Barang, Integer> colStok = new TableColumn<>("Stok");
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStok.setMaxWidth(100);
        colStok.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                Label badge = new Label(String.valueOf(item));
                if (item == 0) {
                    badge.getStyleClass().addAll("badge", "badge-danger");
                } else if (item < 5) {
                    badge.getStyleClass().addAll("badge", "badge-warning");
                } else {
                    badge.getStyleClass().addAll("badge", "badge-success");
                }
                setGraphic(badge);
            }
        });

        TableColumn<Barang, String> colSatuan = new TableColumn<>("Satuan");
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colSatuan.setMaxWidth(80);

        TableColumn<Barang, Double> colHargaJual = new TableColumn<>("Harga Jual");
        colHargaJual.setCellValueFactory(new PropertyValueFactory<>("hargaJual"));
        colHargaJual.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colHargaJual.setMaxWidth(140);

        TableColumn<Barang, String> colSupplier = new TableColumn<>("Supplier");
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colSupplier.setMaxWidth(150);

        TableColumn<Barang, Integer> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStatus.setMaxWidth(120);
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                Label badge;
                if (item == 0) {
                    badge = new Label("HABIS");
                    badge.getStyleClass().addAll("badge", "badge-danger");
                } else if (item < 5) {
                    badge = new Label("KRITIS");
                    badge.getStyleClass().addAll("badge", "badge-warning");
                } else {
                    badge = new Label("TERSEDIA");
                    badge.getStyleClass().addAll("badge", "badge-success");
                }
                setGraphic(badge);
            }
        });

        table.getColumns().addAll(colKode, colNama, colKategori, colStok, colSatuan, colHargaJual, colSupplier, colStatus);

        view.getChildren().addAll(topBar, table);
    }

    private void loadData() {
        Task<List<Barang>> task = new Task<>() {
            @Override protected List<Barang> call() throws Exception { return dao.getAll(); }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        Task<List<Barang>> task = new Task<>() {
            @Override protected List<Barang> call() throws Exception {
                return keyword.isEmpty() ? dao.getAll() : dao.search(keyword);
            }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }
}
