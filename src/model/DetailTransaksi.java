package model;

/**
 * Model class representing a Transaction Detail line item.
 */
public class DetailTransaksi {

    private int idDetail;
    private int idTransaksi;
    private int idBarang;
    private String namaBarang; // For display purposes
    private int jumlah;
    private double hargaSatuan;
    private double subtotal;

    public DetailTransaksi() {}

    public DetailTransaksi(int idBarang, String namaBarang, int jumlah, double hargaSatuan) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }

    // Getters and Setters
    public int getIdDetail() { return idDetail; }
    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = jumlah * hargaSatuan;
    }

    public double getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return namaBarang + " x" + jumlah;
    }
}
