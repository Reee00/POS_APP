package ui.views;

import dao.CustomerDAO;
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
import model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Customer management view with CRUD operations.
 */
public class CustomerView {

    private VBox view;
    private TableView<Customer> table;
    private ObservableList<Customer> data;
    private TextField searchField;
    private final CustomerDAO dao = new CustomerDAO();

    public CustomerView() {
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
        searchField.setPromptText("\uD83D\uDD0D Cari customer...");
        searchField.setOnKeyReleased(e -> searchData());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnAdd = new Button("+ Tambah Customer");
        btnAdd.getStyleClass().add("btn-primary");
        btnAdd.setOnAction(e -> showForm(null));

        topBar.getChildren().addAll(searchField, spacer, btnAdd);

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Customer, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeCustomer"));
        colKode.setMaxWidth(110);

        TableColumn<Customer, String> colNama = new TableColumn<>("Nama Customer");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaCustomer"));

        TableColumn<Customer, String> colTelepon = new TableColumn<>("Telepon");
        colTelepon.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        colTelepon.setMaxWidth(140);

        TableColumn<Customer, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setMaxWidth(200);

        TableColumn<Customer, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        TableColumn<Customer, Void> colAksi = new TableColumn<>("Aksi");
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
        Task<List<Customer>> task = new Task<>() {
            @Override protected List<Customer> call() throws Exception { return dao.getAll(); }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        Task<List<Customer>> task = new Task<>() {
            @Override protected List<Customer> call() throws Exception {
                return keyword.isEmpty() ? dao.getAll() : dao.search(keyword);
            }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
        });
        new Thread(task).start();
    }

    private void deleteItem(Customer c) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus customer: " + c.getNamaCustomer() + "?");
        alert.setContentText("Data yang dihapus tidak dapat dikembalikan.");
        styleAlert(alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Task<Boolean> task = new Task<>() {
                @Override protected Boolean call() throws Exception { return dao.delete(c.getIdCustomer()); }
            };
            task.setOnSucceeded(e -> loadData());
            task.setOnFailed(e -> showError("Gagal menghapus: " + task.getException().getMessage()));
            new Thread(task).start();
        }
    }

    private void showForm(Customer existing) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox formCard = new VBox(16);
        formCard.getStyleClass().add("form-card");
        formCard.setPadding(new Insets(32));
        formCard.setMinWidth(520);
        formCard.setStyle("-fx-background-color: #161B22; -fx-background-radius: 12; -fx-border-color: rgba(0,188,212,0.3); -fx-border-radius: 12; -fx-border-width: 1;");

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label(existing == null ? "\uD83D\uDC64  Tambah Customer" : "\u270F\uFE0F  Edit Customer");
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

        TextField tfKode = addField(grid, "Kode Customer", 0);
        TextField tfNama = addField(grid, "Nama Customer", 1);
        TextField tfTelepon = addField(grid, "Telepon", 2);
        TextField tfEmail = addField(grid, "Email", 3);
        TextField tfAlamat = addField(grid, "Alamat", 4);

        if (existing != null) {
            tfKode.setText(existing.getKodeCustomer());
            tfNama.setText(existing.getNamaCustomer());
            tfTelepon.setText(existing.getTelepon());
            tfEmail.setText(existing.getEmail());
            tfAlamat.setText(existing.getAlamat());
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
            Customer c = existing != null ? existing : new Customer();
            c.setKodeCustomer(tfKode.getText().trim());
            c.setNamaCustomer(tfNama.getText().trim());
            c.setTelepon(tfTelepon.getText().trim());
            c.setEmail(tfEmail.getText().trim());
            c.setAlamat(tfAlamat.getText().trim());

            if (c.getKodeCustomer().isEmpty() || c.getNamaCustomer().isEmpty()) {
                showError("Kode dan Nama Customer wajib diisi!");
                return;
            }

            Task<Boolean> task = new Task<>() {
                @Override protected Boolean call() throws Exception {
                    return existing == null ? dao.insert(c) : dao.update(c);
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
