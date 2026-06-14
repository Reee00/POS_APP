package ui.views;

import dao.BarangDAO;
import dao.CustomerDAO;
import dao.TransaksiDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Barang;
import model.Customer;
import model.DetailTransaksi;
import model.Transaksi;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Transaction/Sales view with cart system, customer selection,
 * and order summary with discount and grand total.
 */
public class TransaksiView {

    private HBox view;
    private Label lblNoTransaksi, lblSubtotal, lblGrandTotal;
    private ComboBox<Customer> cbCustomer;
    private ComboBox<Barang> cbBarang;
    private TextField tfJumlah, tfDiskon;
    private TextArea taNote;
    private TableView<DetailTransaksi> cartTable;
    private ObservableList<DetailTransaksi> cartItems;
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();
    private final BarangDAO barangDAO = new BarangDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public TransaksiView() {
        cartItems = FXCollections.observableArrayList();
        buildView();
        loadReferenceData();
    }

    public Node getView() { return view; }

    private void buildView() {
        view = new HBox(16);

        // ============ LEFT: Cart Section (60%) ============
        VBox leftSection = new VBox(16);
        HBox.setHgrow(leftSection, Priority.ALWAYS);

        // Header form card
        VBox headerCard = new VBox(12);
        headerCard.getStyleClass().add("form-card");

        Label headerTitle = new Label("\uD83D\uDED2  Transaksi Penjualan Baru");
        headerTitle.getStyleClass().add("section-title");

        HBox headerFields = new HBox(16);
        headerFields.setAlignment(Pos.CENTER_LEFT);

        VBox noBox = new VBox(4);
        Label lblNoLabel = new Label("No. Transaksi");
        lblNoLabel.getStyleClass().add("form-label");
        lblNoTransaksi = new Label("TRX-00001");
        lblNoTransaksi.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");
        noBox.getChildren().addAll(lblNoLabel, lblNoTransaksi);

        VBox custBox = new VBox(4);
        HBox.setHgrow(custBox, Priority.ALWAYS);
        Label lblCust = new Label("Customer");
        lblCust.getStyleClass().add("form-label");
        cbCustomer = new ComboBox<>();
        cbCustomer.setPromptText("Pilih Customer (opsional)");
        cbCustomer.setMaxWidth(Double.MAX_VALUE);
        custBox.getChildren().addAll(lblCust, cbCustomer);

        headerFields.getChildren().addAll(noBox, custBox);
        headerCard.getChildren().addAll(headerTitle, headerFields);

        // Add item row
        VBox addItemCard = new VBox(12);
        addItemCard.getStyleClass().add("form-card");

        Label addTitle = new Label("Tambah Item ke Keranjang");
        addTitle.getStyleClass().add("form-section-title");

        HBox addRow = new HBox(12);
        addRow.setAlignment(Pos.BOTTOM_LEFT);

        VBox barangBox = new VBox(4);
        HBox.setHgrow(barangBox, Priority.ALWAYS);
        Label lblBarang = new Label("Barang");
        lblBarang.getStyleClass().add("form-label");
        cbBarang = new ComboBox<>();
        cbBarang.setPromptText("Pilih Barang");
        cbBarang.setMaxWidth(Double.MAX_VALUE);
        barangBox.getChildren().addAll(lblBarang, cbBarang);

        VBox qtyBox = new VBox(4);
        Label lblQty = new Label("Jumlah");
        lblQty.getStyleClass().add("form-label");
        tfJumlah = new TextField("1");
        tfJumlah.setPrefWidth(80);
        qtyBox.getChildren().addAll(lblQty, tfJumlah);

        Button btnAddItem = new Button("+ Tambah");
        btnAddItem.getStyleClass().add("btn-secondary");
        btnAddItem.setOnAction(e -> addItemToCart());

        addRow.getChildren().addAll(barangBox, qtyBox, btnAddItem);
        addItemCard.getChildren().addAll(addTitle, addRow);

        // Cart table
        VBox cartCard = new VBox(12);
        cartCard.getStyleClass().add("form-card");
        VBox.setVgrow(cartCard, Priority.ALWAYS);

        Label cartTitle = new Label("\uD83D\uDCCB  Keranjang Belanja");
        cartTitle.getStyleClass().add("section-title");

        cartTable = new TableView<>();
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cartTable.setItems(cartItems);
        VBox.setVgrow(cartTable, Priority.ALWAYS);

        TableColumn<DetailTransaksi, String> colNama = new TableColumn<>("Nama Barang");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));

        TableColumn<DetailTransaksi, Integer> colJumlah = new TableColumn<>("Qty");
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colJumlah.setMaxWidth(70);

        TableColumn<DetailTransaksi, Double> colHarga = new TableColumn<>("Harga");
        colHarga.setCellValueFactory(new PropertyValueFactory<>("hargaSatuan"));
        colHarga.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colHarga.setMaxWidth(130);

        TableColumn<DetailTransaksi, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        colSubtotal.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colSubtotal.setMaxWidth(140);

        TableColumn<DetailTransaksi, Void> colHapus = new TableColumn<>("Hapus");
        colHapus.setMaxWidth(80);
        colHapus.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("\uD83D\uDDD1");
            {
                btn.getStyleClass().addAll("btn-danger", "btn-sm");
                btn.setOnAction(e -> {
                    cartItems.remove(getIndex());
                    updateTotals();
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        cartTable.getColumns().addAll(colNama, colJumlah, colHarga, colSubtotal, colHapus);
        cartCard.getChildren().addAll(cartTitle, cartTable);

        leftSection.getChildren().addAll(headerCard, addItemCard, cartCard);

        // ============ RIGHT: Summary Section (40%) ============
        VBox rightSection = new VBox(16);
        rightSection.setMinWidth(320);
        rightSection.setMaxWidth(380);

        VBox summaryCard = new VBox(16);
        summaryCard.getStyleClass().add("form-card");

        Label summaryTitle = new Label("\uD83D\uDCCB  Ringkasan Pesanan");
        summaryTitle.getStyleClass().add("section-title");

        // Subtotal
        HBox subtotalRow = new HBox();
        subtotalRow.setAlignment(Pos.CENTER_LEFT);
        Label lblSubLabel = new Label("Subtotal");
        lblSubLabel.getStyleClass().add("summary-label");
        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);
        lblSubtotal = new Label("Rp 0");
        lblSubtotal.getStyleClass().add("summary-value");
        subtotalRow.getChildren().addAll(lblSubLabel, sp1, lblSubtotal);

        // Diskon
        HBox diskonRow = new HBox(8);
        diskonRow.setAlignment(Pos.CENTER_LEFT);
        Label lblDisLabel = new Label("Diskon (%)");
        lblDisLabel.getStyleClass().add("summary-label");
        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);
        tfDiskon = new TextField("0");
        tfDiskon.setPrefWidth(80);
        tfDiskon.setStyle("-fx-alignment: CENTER_RIGHT;");
        tfDiskon.setOnKeyReleased(e -> updateTotals());
        diskonRow.getChildren().addAll(lblDisLabel, sp2, tfDiskon);

        Separator sep = new Separator();

        // Grand Total
        HBox grandTotalRow = new HBox();
        grandTotalRow.setAlignment(Pos.CENTER_LEFT);
        Label lblGrandLabel = new Label("Grand Total");
        lblGrandLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F0F6FC;");
        Region sp3 = new Region();
        HBox.setHgrow(sp3, Priority.ALWAYS);
        lblGrandTotal = new Label("Rp 0");
        lblGrandTotal.getStyleClass().add("grand-total-value");
        grandTotalRow.getChildren().addAll(lblGrandLabel, sp3, lblGrandTotal);

        Separator sep2 = new Separator();

        // Notes
        Label lblNote = new Label("Catatan");
        lblNote.getStyleClass().add("form-label");
        taNote = new TextArea();
        taNote.setPromptText("Catatan tambahan...");
        taNote.setPrefRowCount(3);

        // Save button
        Button btnSave = new Button("\uD83D\uDCBE  Simpan Transaksi");
        btnSave.getStyleClass().add("btn-primary");
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnSave.setPrefHeight(44);
        btnSave.setOnAction(e -> saveTransaction());

        // Clear button
        Button btnClear = new Button("\uD83D\uDD04  Reset");
        btnClear.getStyleClass().add("btn-ghost");
        btnClear.setMaxWidth(Double.MAX_VALUE);
        btnClear.setOnAction(e -> resetForm());

        summaryCard.getChildren().addAll(
            summaryTitle, subtotalRow, diskonRow, sep, grandTotalRow, sep2,
            lblNote, taNote, btnSave, btnClear
        );

        rightSection.getChildren().add(summaryCard);

        view.getChildren().addAll(leftSection, rightSection);
    }

    private void loadReferenceData() {
        // Load customers
        Task<Void> task = new Task<>() {
            @Override protected Void call() throws Exception {
                var customers = customerDAO.getAll();
                var barangs = barangDAO.getAll();
                String noTrx = transaksiDAO.generateNoTransaksi();
                javafx.application.Platform.runLater(() -> {
                    cbCustomer.setItems(FXCollections.observableArrayList(customers));
                    cbBarang.setItems(FXCollections.observableArrayList(barangs));
                    lblNoTransaksi.setText(noTrx);
                });
                return null;
            }
        };
        task.setOnFailed(e -> task.getException().printStackTrace());
        new Thread(task).start();
    }

    private void addItemToCart() {
        Barang selected = cbBarang.getValue();
        if (selected == null) {
            showError("Pilih barang terlebih dahulu!");
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(tfJumlah.getText().trim());
            if (jumlah <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Jumlah harus berupa angka positif!");
            return;
        }

        if (jumlah > selected.getStok()) {
            showError("Stok tidak mencukupi! Stok tersedia: " + selected.getStok());
            return;
        }

        // Check if item already in cart
        for (DetailTransaksi d : cartItems) {
            if (d.getIdBarang() == selected.getIdBarang()) {
                d.setJumlah(d.getJumlah() + jumlah);
                cartTable.refresh();
                updateTotals();
                return;
            }
        }

        DetailTransaksi detail = new DetailTransaksi(
            selected.getIdBarang(), selected.getNamaBarang(),
            jumlah, selected.getHargaJual()
        );
        cartItems.add(detail);
        updateTotals();

        // Reset
        cbBarang.setValue(null);
        tfJumlah.setText("1");
    }

    private void updateTotals() {
        double subtotal = 0;
        for (DetailTransaksi d : cartItems) {
            subtotal += d.getSubtotal();
        }
        lblSubtotal.setText(currencyFormat.format(subtotal));

        double diskon = 0;
        try {
            diskon = Double.parseDouble(tfDiskon.getText().trim());
        } catch (NumberFormatException ignored) {}

        double grandTotal = subtotal - (subtotal * diskon / 100);
        lblGrandTotal.setText(currencyFormat.format(grandTotal));
    }

    private void saveTransaction() {
        if (cartItems.isEmpty()) {
            showError("Keranjang belanja masih kosong!");
            return;
        }

        double subtotal = 0;
        for (DetailTransaksi d : cartItems) {
            subtotal += d.getSubtotal();
        }
        double diskon = 0;
        try {
            diskon = Double.parseDouble(tfDiskon.getText().trim());
        } catch (NumberFormatException ignored) {}
        double grandTotal = subtotal - (subtotal * diskon / 100);

        Transaksi t = new Transaksi();
        t.setNoTransaksi(lblNoTransaksi.getText());
        if (cbCustomer.getValue() != null) {
            t.setIdCustomer(cbCustomer.getValue().getIdCustomer());
        }
        t.setTotalHarga(subtotal);
        t.setDiskon(diskon);
        t.setGrandTotal(grandTotal);
        t.setStatus("lunas");
        t.setCatatan(taNote.getText().trim());
        t.setDetails(new java.util.ArrayList<>(cartItems));

        Task<Boolean> task = new Task<>() {
            @Override protected Boolean call() throws Exception {
                return transaksiDAO.saveTransaction(t);
            }
        };
        task.setOnSucceeded(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setHeaderText("Transaksi berhasil disimpan!");
            alert.setContentText("No. Transaksi: " + t.getNoTransaksi() +
                "\nGrand Total: " + currencyFormat.format(grandTotal));
            styleAlert(alert);
            alert.showAndWait();
            resetForm();
            loadReferenceData();
        });
        task.setOnFailed(e -> showError("Gagal menyimpan transaksi: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    private void resetForm() {
        cartItems.clear();
        cbCustomer.setValue(null);
        cbBarang.setValue(null);
        tfJumlah.setText("1");
        tfDiskon.setText("0");
        taNote.clear();
        updateTotals();
        loadReferenceData();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        styleAlert(alert);
        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/util/renaldy.css").toExternalForm());
        alert.getDialogPane().setStyle("-fx-background-color: #161B22;");
    }
}
