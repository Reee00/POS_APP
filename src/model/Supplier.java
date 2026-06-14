package model;

import java.sql.Timestamp;

/**
 * Model class representing a Supplier.
 */
public class Supplier {

    private int idSupplier;
    private String kodeSupplier;
    private String namaSupplier;
    private String telepon;
    private String alamat;
    private String email;
    private Timestamp createdAt;

    public Supplier() {}

    public Supplier(int idSupplier, String kodeSupplier, String namaSupplier,
                    String telepon, String alamat, String email) {
        this.idSupplier = idSupplier;
        this.kodeSupplier = kodeSupplier;
        this.namaSupplier = namaSupplier;
        this.telepon = telepon;
        this.alamat = alamat;
        this.email = email;
    }

    // Getters and Setters
    public int getIdSupplier() { return idSupplier; }
    public void setIdSupplier(int idSupplier) { this.idSupplier = idSupplier; }

    public String getKodeSupplier() { return kodeSupplier; }
    public void setKodeSupplier(String kodeSupplier) { this.kodeSupplier = kodeSupplier; }

    public String getNamaSupplier() { return namaSupplier; }
    public void setNamaSupplier(String namaSupplier) { this.namaSupplier = namaSupplier; }

    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return kodeSupplier + " - " + namaSupplier;
    }
}
