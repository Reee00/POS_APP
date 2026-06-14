package ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.util.Duration;
import ui.views.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Main controller that manages the sidebar navigation, header bar,
 * and content switching for the POS application.
 */
public class MainController {

    private BorderPane root;
    private StackPane contentContainer;
    private Label pageTitle;
    private Label pageBreadcrumb;
    private Label datetimeLabel;
    private Button activeButton;
    private VBox sidebar;

    public MainController() {
        root = new BorderPane();
        buildSidebar();
        buildHeader();
        buildContent();
    }

    public BorderPane getRoot() {
        return root;
    }

    // ==================== SIDEBAR ====================
    private void buildSidebar() {
        sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(230);
        sidebar.setMaxWidth(230);

        // Brand row
        HBox brandRow = new HBox(12);
        brandRow.setAlignment(Pos.CENTER_LEFT);
        brandRow.setPadding(new Insets(20, 16, 16, 16));

        StackPane brandLogo = new StackPane();
        brandLogo.getStyleClass().add("brand-logo");
        Label logoLabel = new Label("AR");
        logoLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        brandLogo.getChildren().add(logoLabel);

        VBox brandText = new VBox(2);
        Label brandName = new Label("Ahmad Renaldy");
        brandName.getStyleClass().add("brand-name");
        Label brandSub = new Label("POS System");
        brandSub.getStyleClass().add("brand-sub");
        brandText.getChildren().addAll(brandName, brandSub);

        brandRow.getChildren().addAll(brandLogo, brandText);

        // Navigation
        Separator sep1 = new Separator();

        Label menuLabel = new Label("MENU UTAMA");
        menuLabel.getStyleClass().add("nav-section-label");

        Button btnDashboard = createNavButton("\uD83C\uDFE0  Dashboard", "Dashboard", "Ringkasan & Statistik");
        Button btnBarang = createNavButton("\uD83D\uDCE6  Barang", "Barang", "Menu Utama > Barang");
        Button btnCustomer = createNavButton("\uD83D\uDC64  Customer", "Customer", "Menu Utama > Customer");
        Button btnSupplier = createNavButton("\uD83C\uDFED  Supplier", "Supplier", "Menu Utama > Supplier");

        Separator sep2 = new Separator();

        Label transaksiLabel = new Label("TRANSAKSI");
        transaksiLabel.getStyleClass().add("nav-section-label");

        Button btnPenjualan = createNavButton("\uD83D\uDED2  Penjualan", "Penjualan", "Transaksi > Penjualan Baru");
        Button btnInventory = createNavButton("\uD83D\uDCCA  Inventory", "Inventory", "Transaksi > Stok Barang");

        Separator sep3 = new Separator();

        Label laporanLabel = new Label("LAPORAN");
        laporanLabel.getStyleClass().add("nav-section-label");

        Button btnLapTransaksi = createNavButton("\uD83D\uDCCB  Lap. Transaksi", "Laporan Transaksi", "Laporan > Transaksi");
        Button btnLapInventory = createNavButton("\uD83D\uDCE6  Lap. Inventory", "Laporan Inventory", "Laporan > Inventory");

        // Set actions
        btnDashboard.setOnAction(e -> { setActive(btnDashboard); showDashboard(); });
        btnBarang.setOnAction(e -> { setActive(btnBarang); showBarang(); });
        btnCustomer.setOnAction(e -> { setActive(btnCustomer); showCustomer(); });
        btnSupplier.setOnAction(e -> { setActive(btnSupplier); showSupplier(); });
        btnPenjualan.setOnAction(e -> { setActive(btnPenjualan); showTransaksi(); });
        btnInventory.setOnAction(e -> { setActive(btnInventory); showInventory(); });
        btnLapTransaksi.setOnAction(e -> { setActive(btnLapTransaksi); showLaporanTransaksi(); });
        btnLapInventory.setOnAction(e -> { setActive(btnLapInventory); showLaporanInventory(); });

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Footer
        VBox footer = new VBox(4);
        footer.setPadding(new Insets(16));
        footer.setAlignment(Pos.CENTER);
        Label version = new Label("v1.0.0");
        version.setStyle("-fx-font-size: 10px; -fx-text-fill: #4A5568;");
        Label copyright = new Label("© 2025 Ahmad Renaldy");
        copyright.setStyle("-fx-font-size: 10px; -fx-text-fill: #4A5568;");
        footer.getChildren().addAll(version, copyright);

        sidebar.getChildren().addAll(
            brandRow, sep1,
            menuLabel, btnDashboard, btnBarang, btnCustomer, btnSupplier,
            sep2, transaksiLabel, btnPenjualan, btnInventory,
            sep3, laporanLabel, btnLapTransaksi, btnLapInventory,
            spacer, footer
        );

        root.setLeft(sidebar);

        // Set default active
        activeButton = btnDashboard;
        setActive(btnDashboard);
    }

    private Button createNavButton(String text, String title, String breadcrumb) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setUserData(new String[]{title, breadcrumb});
        return btn;
    }

    private void setActive(Button btn) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("nav-button-active");
        }
        btn.getStyleClass().add("nav-button-active");
        activeButton = btn;

        // Update header
        String[] data = (String[]) btn.getUserData();
        if (data != null && pageTitle != null) {
            pageTitle.setText(data[0]);
            pageBreadcrumb.setText(data[1]);
        }
    }

    // ==================== HEADER ====================
    private void buildHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("header-bar");
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        pageTitle = new Label("Dashboard");
        pageTitle.getStyleClass().add("page-title");
        pageBreadcrumb = new Label("Ringkasan & Statistik");
        pageBreadcrumb.getStyleClass().add("page-breadcrumb");
        titleBox.getChildren().addAll(pageTitle, pageBreadcrumb);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        datetimeLabel = new Label();
        datetimeLabel.getStyleClass().add("datetime-label");
        updateDateTime();

        // Real-time clock
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        header.getChildren().addAll(titleBox, spacer, datetimeLabel);

        root.setTop(header);
    }

    private void updateDateTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy  •  HH:mm:ss");
        datetimeLabel.setText(LocalDateTime.now().format(fmt));
    }

    // ==================== CONTENT ====================
    private void buildContent() {
        contentContainer = new StackPane();
        contentContainer.getStyleClass().add("content-area");
        root.setCenter(contentContainer);
    }

    private void switchContent(Node newContent) {
        // Fade transition
        FadeTransition fadeOut = new FadeTransition(Duration.millis(80), contentContainer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            contentContainer.getChildren().setAll(newContent);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(150), contentContainer);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    // ==================== VIEW METHODS ====================
    public void showDashboard() {
        switchContent(new DashboardView().getView());
    }

    private void showBarang() {
        switchContent(new BarangView().getView());
    }

    private void showCustomer() {
        switchContent(new CustomerView().getView());
    }

    private void showSupplier() {
        switchContent(new SupplierView().getView());
    }

    private void showTransaksi() {
        switchContent(new TransaksiView().getView());
    }

    private void showInventory() {
        switchContent(new InventoryView().getView());
    }

    private void showLaporanTransaksi() {
        switchContent(new LaporanTransaksiView().getView());
    }

    private void showLaporanInventory() {
        switchContent(new LaporanInventoryView().getView());
    }
}
