package ui.views;

import dao.SupplierDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * Supplier management view with CRUD operations.
 */
public class SupplierView {

    private VBox view;
    private TableView<Supplier> table;
    private ObservableList<Supplier> data;
    private TextField searchField;
    private final SupplierDAO dao = new SupplierDAO();

    public SupplierView() {
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
        searchField.setPromptText("\uD83D\uDD0D Cari supplier...");
        searchField.setOnKeyReleased(e -> searchData());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnAdd = new Button("+ Tambah Supplier");
        btnAdd.getStyleClass().add("btn-primary");
        btnAdd.setOnAction(e -> showForm(null));

        topBar.getChildren().addAll(searchField, spacer, btnAdd);

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Supplier, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeSupplier"));
        colKode.setMaxWidth(110);

        TableColumn<Supplier, String> colNama = new TableColumn<>("Nama Supplier");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));

        TableColumn<Supplier, String> colTelepon = new TableColumn<>("Telepon");
        colTelepon.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        colTelepon.setMaxWidth(140);

        TableColumn<Supplier, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setMaxWidth(200);

        TableColumn<Supplier, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        TableColumn<Supplier, Void> colAksi = new TableColumn<>("Aksi");
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

        table.getColumns().addAll(colKode, colNama, colTelepon, colEmail, colAlamat, colAksi);

        view.getChildren().addAll(topBar, table);
    }

    private void loadData() {
        Task<List<Supplier>> task = new Task<>() {
            @Override protected List<Supplier> call() throws Exception { return dao.getAll(); }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        Task<List<Supplier>> task = new Task<>() {
            @Override protected List<Supplier> call() throws Exception {
                return keyword.isEmpty() ? dao.getAll() : dao.search(keyword);
            }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }

    private void deleteItem(Supplier s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus supplier: " + s.getNamaSupplier() + "?");
        alert.setContentText("Data yang dihapus tidak dapat dikembalikan.");
        styleAlert(alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Task<Boolean> task = new Task<>() {
                @Override protected Boolean call() throws Exception { return dao.delete(s.getIdSupplier()); }
            };
            task.setOnSucceeded(e -> loadData());
            task.setOnFailed(e -> showError("Gagal menghapus: " + task.getException().getMessage()));
            new Thread(task).start();
        }
    }

    private void showForm(Supplier existing) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox formCard = new VBox(16);
        formCard.getStyleClass().add("form-card");
        formCard.setPadding(new Insets(32));
        formCard.setMinWidth(520);
        formCard.setStyle("-fx-background-color: #161B22; -fx-background-radius: 12; -fx-border-color: rgba(0,188,212,0.3); -fx-border-radius: 12; -fx-border-width: 1;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label(existing == null ? "\uD83C\uDFED  Tambah Supplier" : "\u270F\uFE0F  Edit Supplier");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button btnClose = new Button("\u2715");
        btnClose.getStyleClass().add("btn-ghost");
        btnClose.setOnAction(e -> dialog.close());
        header.getChildren().addAll(title, sp, btnClose);

        Separator sep = new Separator();

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(12);

        TextField tfKode = addField(grid, "Kode Supplier", 0);
        TextField tfNama = addField(grid, "Nama Supplier", 1);
        TextField tfTelepon = addField(grid, "Telepon", 2);
        TextField tfEmail = addField(grid, "Email", 3);
        TextField tfAlamat = addField(grid, "Alamat", 4);

        if (existing != null) {
            tfKode.setText(existing.getKodeSupplier());
            tfNama.setText(existing.getNamaSupplier());
            tfTelepon.setText(existing.getTelepon());
            tfEmail.setText(existing.getEmail());
            tfAlamat.setText(existing.getAlamat());
        }

        Separator sep2 = new Separator();

        HBox buttons = new HBox(8);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button btnCancel = new Button("Batal");
        btnCancel.getStyleClass().add("btn-ghost");
        btnCancel.setOnAction(e -> dialog.close());
        Button btnSave = new Button("\uD83D\uDCBE  Simpan");
        btnSave.getStyleClass().add("btn-primary");

        btnSave.setOnAction(e -> {
            Supplier s = existing != null ? existing : new Supplier();
            s.setKodeSupplier(tfKode.getText().trim());
            s.setNamaSupplier(tfNama.getText().trim());
            s.setTelepon(tfTelepon.getText().trim());
            s.setEmail(tfEmail.getText().trim());
            s.setAlamat(tfAlamat.getText().trim());

            if (s.getKodeSupplier().isEmpty() || s.getNamaSupplier().isEmpty()) {
                showError("Kode dan Nama Supplier wajib diisi!");
                return;
            }

            Task<Boolean> task = new Task<>() {
                @Override protected Boolean call() throws Exception {
                    return existing == null ? dao.insert(s) : dao.update(s);
                }
            };
            task.setOnSucceeded(ev -> { dialog.close(); loadData(); });
            task.setOnFailed(ev -> showError("Gagal menyimpan: " + task.getException().getMessage()));
            new Thread(task).start();
        });

        buttons.getChildren().addAll(btnCancel, btnSave);
        formCard.getChildren().addAll(header, sep, grid, sep2, buttons);

        StackPane overlay = new StackPane(formCard);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        overlay.setAlignment(Pos.CENTER);

        Scene scene = new Scene(overlay, 580, 460);
        scene.getStylesheets().add(getClass().getResource("/util/renaldy.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private TextField addField(GridPane grid, String label, int row) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("form-label");
        TextField tf = new TextField();
        tf.setPromptText(label);
        tf.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(tf, Priority.ALWAYS);
        grid.add(lbl, 0, row);
        grid.add(tf, 1, row);
        return tf;
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setContentText(msg);
        styleAlert(a);
        a.showAndWait();
    }

    private void styleAlert(Alert alert) {
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/util/renaldy.css").toExternalForm());
        alert.getDialogPane().setStyle("-fx-background-color: #161B22;");
    }
}
