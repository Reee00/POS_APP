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
import model.Transaksi;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Dashboard view showing summary cards, recent transactions, and low stock items.
 */
public class DashboardView {

    private VBox view;
    private Label totalBarangValue, totalCustomerValue, transaksiHariIniValue, stokMenipisValue;
    private TableView<Transaksi> recentTable;
    private TableView<Barang> lowStockTable;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public DashboardView() {
        buildView();
        loadData();
    }

    public Node getView() {
        return view;
    }

    private void buildView() {
        view = new VBox(20);
        view.setPadding(new Insets(0));

        // Stat Cards Row
        HBox statsRow = new HBox(16);

        VBox cardBarang = createStatCard("\uD83D\uDCE6", "Total Barang", "0", "stat-icon-box");
        totalBarangValue = (Label) cardBarang.lookup(".stat-value");

        VBox cardCustomer = createStatCard("\uD83D\uDC64", "Total Customer", "0", "stat-icon-box-success");
        totalCustomerValue = (Label) cardCustomer.lookup(".stat-value");

        VBox cardTransaksi = createStatCard("\uD83D\uDED2", "Transaksi Hari Ini", "0", "stat-icon-box");
        transaksiHariIniValue = (Label) cardTransaksi.lookup(".stat-value");

        VBox cardStok = createStatCard("\u26A0\uFE0F", "Stok Menipis", "0", "stat-icon-box-warning");
        stokMenipisValue = (Label) cardStok.lookup(".stat-value");

        HBox.setHgrow(cardBarang, Priority.ALWAYS);
        HBox.setHgrow(cardCustomer, Priority.ALWAYS);
        HBox.setHgrow(cardTransaksi, Priority.ALWAYS);
        HBox.setHgrow(cardStok, Priority.ALWAYS);

        statsRow.getChildren().addAll(cardBarang, cardCustomer, cardTransaksi, cardStok);

        // Bottom section
        HBox bottomRow = new HBox(16);
        VBox.setVgrow(bottomRow, Priority.ALWAYS);

        // Recent Transactions
        VBox recentBox = new VBox(12);
        recentBox.getStyleClass().add("form-card");
        HBox.setHgrow(recentBox, Priority.ALWAYS);
        recentBox.setMaxWidth(Double.MAX_VALUE);

        Label recentTitle = new Label("\uD83D\uDCCB  Transaksi Terakhir");
        recentTitle.getStyleClass().add("section-title");

        recentTable = new TableView<>();
        recentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(recentTable, Priority.ALWAYS);

        TableColumn<Transaksi, String> colNo = new TableColumn<>("No. Transaksi");
        colNo.setCellValueFactory(new PropertyValueFactory<>("noTransaksi"));
        colNo.setMinWidth(120);

        TableColumn<Transaksi, String> colCustomer = new TableColumn<>("Customer");
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("namaCustomer"));

        TableColumn<Transaksi, Double> colTotal = new TableColumn<>("Grand Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));
        colTotal.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : currencyFormat.format(item));
            }
        });

        TableColumn<Transaksi, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label badge = new Label(item.toUpperCase());
                    badge.getStyleClass().addAll("badge",
                        item.equalsIgnoreCase("lunas") ? "badge-success" : "badge-warning");
                    setGraphic(badge);
                }
            }
        });
        colStatus.setMaxWidth(120);

        recentTable.getColumns().addAll(colNo, colCustomer, colTotal, colStatus);
        recentBox.getChildren().addAll(recentTitle, recentTable);

        // Low Stock
        VBox lowStockBox = new VBox(12);
        lowStockBox.getStyleClass().add("form-card");
        lowStockBox.setMinWidth(320);
        lowStockBox.setMaxWidth(400);

        Label lowStockTitle = new Label("\u26A0\uFE0F  Produk Stok Kritis");
        lowStockTitle.getStyleClass().add("section-title");

        lowStockTable = new TableView<>();
        lowStockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(lowStockTable, Priority.ALWAYS);

        TableColumn<Barang, String> colNama = new TableColumn<>("Nama Barang");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));

        TableColumn<Barang, Integer> colStok = new TableColumn<>("Stok");
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStok.setMaxWidth(80);
        colStok.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label badge = new Label(String.valueOf(item));
                    badge.getStyleClass().addAll("badge",
                        item == 0 ? "badge-danger" : "badge-warning");
                    setGraphic(badge);
                }
            }
        });

        lowStockTable.getColumns().addAll(colNama, colStok);
        lowStockBox.getChildren().addAll(lowStockTitle, lowStockTable);

        bottomRow.getChildren().addAll(recentBox, lowStockBox);

        view.getChildren().addAll(statsRow, bottomRow);
    }

    private VBox createStatCard(String icon, String label, String value, String iconBoxClass) {
        VBox card = new VBox(8);
        card.getStyleClass().add("stat-card");
        card.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox textBox = new VBox(4);
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("stat-label");
        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("stat-value");
        textBox.getChildren().addAll(labelNode, valueNode);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StackPane iconBox = new StackPane();
        iconBox.getStyleClass().add(iconBoxClass);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 20px;");
        iconBox.getChildren().add(iconLabel);

        topRow.getChildren().addAll(textBox, spacer, iconBox);
        card.getChildren().add(topRow);

        return card;
    }

    private void loadData() {
        Task<Void> task = new Task<>() {
            int barangCount, customerCount, todayCount, lowStockCount;
            List<Transaksi> recentList;
            List<Barang> lowStockList;

            @Override
            protected Void call() throws Exception {
                BarangDAO barangDAO = new BarangDAO();
                CustomerDAO customerDAO = new CustomerDAO();
                TransaksiDAO transaksiDAO = new TransaksiDAO();

                barangCount = barangDAO.getCount();
                customerCount = customerDAO.getCount();
                todayCount = transaksiDAO.getTodayCount();
                lowStockCount = barangDAO.getLowStockCount();
                recentList = transaksiDAO.getRecent(10);
                lowStockList = barangDAO.getLowStock();
                return null;
            }

            @Override
            protected void succeeded() {
                totalBarangValue.setText(String.valueOf(barangCount));
                totalCustomerValue.setText(String.valueOf(customerCount));
                transaksiHariIniValue.setText(String.valueOf(todayCount));
                stokMenipisValue.setText(String.valueOf(lowStockCount));

                ObservableList<Transaksi> recentData = FXCollections.observableArrayList(recentList);
                recentTable.setItems(recentData);

                ObservableList<Barang> lowStockData = FXCollections.observableArrayList(lowStockList);
                lowStockTable.setItems(lowStockData);
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
            }
        };
        new Thread(task).start();
    }
}
