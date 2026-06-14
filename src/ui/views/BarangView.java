package ui.views;

import dao.BarangDAO;
import dao.SupplierDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import model.Barang;
import model.Supplier;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Barang (Product) management view with CRUD operations.
 */
public class BarangView {

    private VBox view;
    private TableView<Barang> table;
    private ObservableList<Barang> data;
    private TextField searchField;
    private final BarangDAO dao = new BarangDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public BarangView() {
        buildView();
        loadData();
    }

    public Node getView() {
        return view;
    }

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

        Button btnAdd = new Button("+ Tambah Barang");
        btnAdd.getStyleClass().add("btn-primary");
        btnAdd.setOnAction(e -> showForm(null));

        topBar.getChildren().addAll(searchField, spacer, btnAdd);

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

        TableColumn<Barang, Double> colHargaBeli = new TableColumn<>("Harga Beli");
        colHargaBeli.setCellValueFactory(new PropertyValueFactory<>("hargaBeli"));
        colHargaBeli.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colHargaBeli.setMaxWidth(130);

        TableColumn<Barang, Double> colHargaJual = new TableColumn<>("Harga Jual");
        colHargaJual.setCellValueFactory(new PropertyValueFactory<>("hargaJual"));
        colHargaJual.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colHargaJual.setMaxWidth(130);

        TableColumn<Barang, Integer> colStok = new TableColumn<>("Stok");
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStok.setMaxWidth(80);
        colStok.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                Label badge = new Label(String.valueOf(item));
                if (item == 0) badge.getStyleClass().addAll("badge", "badge-danger");
                else if (item < 5) badge.getStyleClass().addAll("badge", "badge-warning");
                else badge.getStyleClass().addAll("badge", "badge-success");
                setGraphic(badge);
            }
        });

        TableColumn<Barang, String> colSatuan = new TableColumn<>("Satuan");
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colSatuan.setMaxWidth(80);

        TableColumn<Barang, String> colSupplier = new TableColumn<>("Supplier");
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colSupplier.setMaxWidth(140);

        TableColumn<Barang, Void> colAksi = new TableColumn<>("Aksi");
        colAksi.setMaxWidth(160);
        colAksi.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnHapus = new Button("Hapus");
            private final HBox box = new HBox(6, btnEdit, btnHapus);
            {
                btnEdit.getStyleClass().addAll("btn-secondary", "btn-sm");
                btnHapus.getStyleClass().addAll("btn-danger", "btn-sm");
                box.setAlignment(Pos.CENTER);
                btnEdit.setOnAction(e -> showForm(getTableView().getItems().get(getIndex())));
                btnHapus.setOnAction(e -> deleteItem(getTableView().getItems().get(getIndex())));
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(colKode, colNama, colKategori, colHargaBeli, colHargaJual, colStok, colSatuan, colSupplier, colAksi);

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
        task.setOnFailed(e -> task.getException().printStackTrace());
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

    private void deleteItem(Barang b) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus barang: " + b.getNamaBarang() + "?");
        alert.setContentText("Data yang dihapus tidak dapat dikembalikan.");
        styleAlert(alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Task<Boolean> task = new Task<>() {
                @Override protected Boolean call() throws Exception { return dao.delete(b.getIdBarang()); }
            };
            task.setOnSucceeded(e -> loadData());
            task.setOnFailed(e -> showError("Gagal menghapus data: " + task.getException().getMessage()));
            new Thread(task).start();
        }
    }

    private void showForm(Barang existing) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);

        VBox formCard = new VBox(16);
        formCard.getStyleClass().add("form-card");
        formCard.setPadding(new Insets(32));
        formCard.setMinWidth(520);
        formCard.setStyle("-fx-background-color: #161B22; -fx-background-radius: 12; -fx-border-color: rgba(0,188,212,0.3); -fx-border-radius: 12; -fx-border-width: 1;");

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label(existing == null ? "\uD83D\uDCE6  Tambah Barang" : "\u270F\uFE0F  Edit Barang");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button btnClose = new Button("\u2715");
        btnClose.getStyleClass().add("btn-ghost");
        btnClose.setOnAction(e -> dialog.close());
        header.getChildren().addAll(title, sp, btnClose);

        Separator sep = new Separator();

        // Form fields
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(12);

        TextField tfKode = createField("Kode Barang", grid, 0, 0);
        TextField tfNama = createField("Nama Barang", grid, 0, 1);
        TextField tfKategori = createField("Kategori", grid, 1, 0);
        TextField tfHargaBeli = createField("Harga Beli", grid, 1, 1);
        TextField tfHargaJual = createField("Harga Jual", grid, 2, 0);
        TextField tfStok = createField("Stok", grid, 2, 1);
        TextField tfSatuan = createField("Satuan", grid, 3, 0);

        // Supplier combobox
        Label lblSupplier = new Label("Supplier");
        lblSupplier.getStyleClass().add("form-label");
        ComboBox<Supplier> cbSupplier = new ComboBox<>();
        cbSupplier.setMaxWidth(Double.MAX_VALUE);
        cbSupplier.setPromptText("Pilih Supplier");
        GridPane.setHgrow(cbSupplier, Priority.ALWAYS);
        grid.add(lblSupplier, 2, 6);
        grid.add(cbSupplier, 3, 6);

        // Load suppliers
        try {
            List<Supplier> suppliers = new SupplierDAO().getAll();
            cbSupplier.setItems(FXCollections.observableArrayList(suppliers));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Fill data if editing
        if (existing != null) {
            tfKode.setText(existing.getKodeBarang());
            tfNama.setText(existing.getNamaBarang());
            tfKategori.setText(existing.getKategori());
            tfHargaBeli.setText(String.valueOf(existing.getHargaBeli()));
            tfHargaJual.setText(String.valueOf(existing.getHargaJual()));
            tfStok.setText(String.valueOf(existing.getStok()));
            tfSatuan.setText(existing.getSatuan());
            for (Supplier s : cbSupplier.getItems()) {
                if (s.getIdSupplier() == existing.getIdSupplier()) {
                    cbSupplier.setValue(s);
                    break;
                }
            }
        }

        Separator sep2 = new Separator();

        // Buttons
        HBox buttons = new HBox(8);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button btnCancel = new Button("Batal");
        btnCancel.getStyleClass().add("btn-ghost");
        btnCancel.setOnAction(e -> dialog.close());
        Button btnSave = new Button("\uD83D\uDCBE  Simpan");
        btnSave.getStyleClass().add("btn-primary");

        btnSave.setOnAction(e -> {
            try {
                Barang b = existing != null ? existing : new Barang();
                b.setKodeBarang(tfKode.getText().trim());
                b.setNamaBarang(tfNama.getText().trim());
                b.setKategori(tfKategori.getText().trim());
                b.setHargaBeli(Double.parseDouble(tfHargaBeli.getText().trim()));
                b.setHargaJual(Double.parseDouble(tfHargaJual.getText().trim()));
                b.setStok(Integer.parseInt(tfStok.getText().trim()));
                b.setSatuan(tfSatuan.getText().trim());
                if (cbSupplier.getValue() != null) {
                    b.setIdSupplier(cbSupplier.getValue().getIdSupplier());
                }

                Task<Boolean> task = new Task<>() {
                    @Override protected Boolean call() throws Exception {
                        return existing == null ? dao.insert(b) : dao.update(b);
                    }
                };
                task.setOnSucceeded(ev -> { dialog.close(); loadData(); });
                task.setOnFailed(ev -> showError("Gagal menyimpan: " + task.getException().getMessage()));
                new Thread(task).start();
            } catch (NumberFormatException ex) {
                showError("Format angka tidak valid!");
            }
        });

        buttons.getChildren().addAll(btnCancel, btnSave);

        formCard.getChildren().addAll(header, sep, grid, sep2, buttons);

        StackPane overlay = new StackPane(formCard);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        overlay.setAlignment(Pos.CENTER);

        Scene scene = new Scene(overlay, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/util/renaldy.css").toExternalForm());
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private TextField createField(String label, GridPane grid, int colPair, int row) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("form-label");
        TextField tf = new TextField();
        tf.setPromptText(label);
        GridPane.setHgrow(tf, Priority.ALWAYS);
        grid.add(lbl, colPair * 2, row * 2);
        grid.add(tf, colPair * 2 + 1, row * 2);
        return tf;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        styleAlert(alert);
        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {
        DialogPane dp = alert.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/util/renaldy.css").toExternalForm());
        dp.setStyle("-fx-background-color: #161B22;");
    }
}
