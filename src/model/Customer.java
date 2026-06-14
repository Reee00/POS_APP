package model;

import java.sql.Timestamp;

/**
 * Model class representing a Customer.
 */
public class Customer {

    private int idCustomer;
    private String kodeCustomer;
    private String namaCustomer;
    private String telepon;
    private String alamat;
    private String email;
    private Timestamp createdAt;

    public Customer() {}

    public Customer(int idCustomer, String kodeCustomer, String namaCustomer,
                    String telepon, String alamat, String email) {
        this.idCustomer = idCustomer;
        this.kodeCustomer = kodeCustomer;
        this.namaCustomer = namaCustomer;
        this.telepon = telepon;
        this.alamat = alamat;
        this.email = email;
    }

    // Getters and Setters
    public int getIdCustomer() { return idCustomer; }
    public void setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }

    public String getKodeCustomer() { return kodeCustomer; }
    public void setKodeCustomer(String kodeCustomer) { this.kodeCustomer = kodeCustomer; }

    public String getNamaCustomer() { return namaCustomer; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }

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
        return kodeCustomer + " - " + namaCustomer;
    }
}
