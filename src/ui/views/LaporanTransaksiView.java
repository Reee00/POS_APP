package ui.views;

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
import model.Transaksi;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Transaction report view with date range filtering and summary.
 */
public class LaporanTransaksiView {

    private VBox view;
    private TableView<Transaksi> table;
    private ObservableList<Transaksi> data;
    private DatePicker dpStart, dpEnd;
    private Label lblTotalTransaksi, lblTotalPendapatan;
    private final TransaksiDAO dao = new TransaksiDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public LaporanTransaksiView() {
        buildView();
        loadData();
    }

    public Node getView() { return view; }

    private void buildView() {
        view = new VBox(16);

        // Filter card
        VBox filterCard = new VBox(12);
        filterCard.getStyleClass().add("form-card");

        Label filterTitle = new Label("\uD83D\uDCC5  Filter Laporan Transaksi");
        filterTitle.getStyleClass().add("section-title");

        HBox filterRow = new HBox(16);
        filterRow.setAlignment(Pos.CENTER_LEFT);

        VBox startBox = new VBox(4);
        Label lblStart = new Label("Tanggal Mulai");
        lblStart.getStyleClass().add("form-label");
        dpStart = new DatePicker(LocalDate.now().minusMonths(1));
        startBox.getChildren().addAll(lblStart, dpStart);

        VBox endBox = new VBox(4);
        Label lblEnd = new Label("Tanggal Akhir");
        lblEnd.getStyleClass().add("form-label");
        dpEnd = new DatePicker(LocalDate.now());
        endBox.getChildren().addAll(lblEnd, dpEnd);

        Button btnFilter = new Button("\uD83D\uDD0D  Filter");
        btnFilter.getStyleClass().add("btn-primary");
        btnFilter.setOnAction(e -> filterData());

        Button btnReset = new Button("Reset");
        btnReset.getStyleClass().add("btn-ghost");
        btnReset.setOnAction(e -> {
            dpStart.setValue(LocalDate.now().minusMonths(1));
            dpEnd.setValue(LocalDate.now());
            loadData();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Summary labels
        VBox summaryBox = new VBox(4);
        summaryBox.setAlignment(Pos.CENTER_RIGHT);
        lblTotalTransaksi = new Label("Total Transaksi: 0");
        lblTotalTransaksi.setStyle("-fx-font-size: 12px; -fx-text-fill: #94A3B8;");
        lblTotalPendapatan = new Label("Total Pendapatan: Rp 0");
        lblTotalPendapatan.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");
        summaryBox.getChildren().addAll(lblTotalTransaksi, lblTotalPendapatan);

        filterRow.getChildren().addAll(startBox, endBox, btnFilter, btnReset, spacer, summaryBox);
        filterCard.getChildren().addAll(filterTitle, filterRow);

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Transaksi, String> colNo = new TableColumn<>("No. Transaksi");
        colNo.setCellValueFactory(new PropertyValueFactory<>("noTransaksi"));
        colNo.setMinWidth(120);

        TableColumn<Transaksi, Timestamp> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colTanggal.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : dateFormat.format(item));
            }
        });
        colTanggal.setMaxWidth(150);

        TableColumn<Transaksi, String> colCustomer = new TableColumn<>("Customer");
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("namaCustomer"));

        TableColumn<Transaksi, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colTotal.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });
        colTotal.setMaxWidth(140);

        TableColumn<Transaksi, Double> colDiskon = new TableColumn<>("Diskon %");
        colDiskon.setCellValueFactory(new PropertyValueFactory<>("diskon"));
        colDiskon.setMaxWidth(80);

        TableColumn<Transaksi, Double> colGrand = new TableColumn<>("Grand Total");
        colGrand.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));
        colGrand.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(""); return; }
                setText(currencyFormat.format(item));
                setStyle("-fx-font-weight: bold;");
            }
        });
        colGrand.setMaxWidth(150);

        TableColumn<Transaksi, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setMaxWidth(100);
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                Label badge = new Label(item.toUpperCase());
                badge.getStyleClass().addAll("badge",
                    item.equalsIgnoreCase("lunas") ? "badge-success" : "badge-warning");
                setGraphic(badge);
            }
        });

        table.getColumns().addAll(colNo, colTanggal, colCustomer, colTotal, colDiskon, colGrand, colStatus);

        view.getChildren().addAll(filterCard, table);
    }

    private void loadData() {
        Task<List<Transaksi>> task = new Task<>() {
            @Override protected List<Transaksi> call() throws Exception { return dao.getAll(); }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
            updateSummary();
        });
        new Thread(task).start();
    }

    private void filterData() {
        if (dpStart.getValue() == null || dpEnd.getValue() == null) return;
        String start = dpStart.getValue().toString();
        String end = dpEnd.getValue().toString();

        Task<List<Transaksi>> task = new Task<>() {
            @Override protected List<Transaksi> call() throws Exception {
                return dao.getByDateRange(start, end);
            }
        };
        task.setOnSucceeded(e -> {
            data = FXCollections.observableArrayList(task.getValue());
            table.setItems(data);
            updateSummary();
        });
        new Thread(task).start();
    }

    private void updateSummary() {
        if (data == null) return;
        int count = data.size();
        double total = data.stream().mapToDouble(Transaksi::getGrandTotal).sum();
        lblTotalTransaksi.setText("Total Transaksi: " + count);
        lblTotalPendapatan.setText("Total Pendapatan: " + currencyFormat.format(total));
    }
}
