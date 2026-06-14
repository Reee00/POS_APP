# Ahmad Renaldy POS v1 — Dokumentasi Teknis
> Versi: 1.0.0 | Tanggal: 11 Mei 2026 | Penulis: Ahmad Renaldy

---

## DAFTAR ISI
- [BAB 1 — GAMBARAN UMUM PROYEK](#bab-1--gambaran-umum-proyek)
  - [1.1 Deskripsi Aplikasi](#11-deskripsi-aplikasi)
  - [1.2 Latar Belakang](#12-latar-belakang)
  - [1.3 Tujuan Pengembangan](#13-tujuan-pengembangan)
  - [1.4 Ruang Lingkup](#14-ruang-lingkup)
  - [1.5 Target Pengguna](#15-target-pengguna)
- [BAB 2 — SPESIFIKASI TEKNIS](#bab-2--spesifikasi-teknis)
  - [2.1 Tech Stack](#21-tech-stack)
  - [2.2 Library & Dependencies](#22-library--dependencies)
  - [2.3 Spesifikasi Minimum Hardware](#23-spesifikasi-minimum-hardware)
  - [2.4 Arsitektur Sistem](#24-arsitektur-sistem)
  - [2.5 Design Pattern yang Digunakan](#25-design-pattern-yang-digunakan)
- [BAB 3 — STRUKTUR PROYEK](#bab-3--struktur-proyek)
  - [3.1 Folder Structure](#31-folder-structure)
  - [3.2 Penjelasan Per Package/Folder](#32-penjelasan-per-packagefolder)
  - [3.3 Deskripsi File Penting](#33-deskripsi-file-penting)
- [BAB 4 — DATABASE](#bab-4--database)
  - [4.1 Konfigurasi Koneksi](#41-konfigurasi-koneksi)
  - [4.2 Entity Relationship Diagram (ERD)](#42-entity-relationship-diagram-erd)
  - [4.3 Skema Database Lengkap](#43-skema-database-lengkap)
  - [4.4 Relasi Antar Tabel](#44-relasi-antar-tabel)
  - [4.5 Sample Data](#45-sample-data)
- [BAB 5 — FITUR & MODUL](#bab-5--fitur--modul)
  - [5.1 Daftar Fitur](#51-daftar-fitur)
  - [5.2 Detail Per Modul](#52-detail-per-modul)
- [BAB 6 — PANDUAN INSTALASI & SETUP](#bab-6--panduan-instalasi--setup)
  - [6.1 Persiapan Environment](#61-persiapan-environment)
  - [6.2 Setup Database](#62-setup-database)
  - [6.3 Setup Project di NetBeans](#63-setup-project-di-netbeans)
  - [6.4 Konfigurasi Koneksi Database](#64-konfigurasi-koneksi-database)
- [BAB 7 — PANDUAN PENGGUNAAN](#bab-7--panduan-penggunaan)
  - [7.1 Alur Penggunaan Sistem](#71-alur-penggunaan-sistem)
  - [7.2 Per Layar/Screen](#72-per-layarscreen)
- [BAB 8 — PANDUAN INSTALLER](#bab-8--panduan-installer)
  - [8.1 Tools yang Dibutuhkan](#81-tools-yang-dibutuhkan)
  - [8.2 Langkah Build ke EXE](#82-langkah-build-ke-exe)
  - [8.3 Struktur Folder Installer Final](#83-struktur-folder-installer-final)
  - [8.4 Isi Installer](#84-isi-installer)
- [BAB 9 — PENANGANAN ERROR UMUM](#bab-9--penanganan-error-umum)
- [BAB 10 — PENUTUP](#bab-10--penutup)
  - [10.1 Kesimpulan](#101-kesimpulan)
  - [10.2 Rencana Pengembangan Selanjutnya](#102-rencana-pengembangan-selanjutnya)
  - [10.3 Referensi](#103-referensi)

---

## BAB 1 — GAMBARAN UMUM PROYEK

### 1.1 Deskripsi Aplikasi
Ahmad Renaldy POS v1 adalah aplikasi desktop Point of Sale (POS) yang dirancang untuk mengelola operasi penjualan ritel. Aplikasi ini memungkinkan pengguna untuk melakukan transaksi penjualan, mengelola inventori barang, data pelanggan, dan pemasok, serta menghasilkan laporan untuk analisis bisnis. Aplikasi ini dibangun menggunakan JavaFX untuk antarmuka pengguna yang modern dan responsif, dengan database MySQL untuk penyimpanan data yang handal.

### 1.2 Latar Belakang
Dalam dunia bisnis ritel, pengelolaan inventori, transaksi, dan data pelanggan secara manual sering kali menyebabkan kesalahan, inefisiensi, dan kesulitan dalam pelaporan. Ahmad Renaldy POS v1 dikembangkan untuk mengatasi masalah tersebut dengan menyediakan solusi digital yang terintegrasi. Aplikasi ini memungkinkan pemilik bisnis untuk melacak stok barang secara real-time, memproses transaksi dengan cepat, dan menghasilkan laporan akurat untuk pengambilan keputusan.

### 1.3 Tujuan Pengembangan
1. Meningkatkan efisiensi operasional bisnis ritel melalui otomasi proses penjualan dan inventori.
2. Mengurangi kesalahan manusia dalam pencatatan transaksi dan pengelolaan stok.
3. Menyediakan antarmuka pengguna yang intuitif dan mudah digunakan untuk berbagai tingkat pengguna.
4. Memfasilitasi analisis bisnis melalui fitur laporan yang komprehensif.

### 1.4 Ruang Lingkup
Aplikasi ini mencakup fitur CRUD (Create, Read, Update, Delete) untuk barang, pelanggan, dan pemasok; pemrosesan transaksi penjualan; pengelolaan inventori; serta pembuatan laporan transaksi dan inventori. Aplikasi tidak mencakup fitur online ordering, integrasi dengan perangkat keras POS seperti scanner barcode, atau dukungan multi-cabang.

### 1.5 Target Pengguna
Aplikasi ini ditujukan untuk pemilik usaha kecil dan menengah di bidang ritel, seperti toko kelontong, minimarket, atau bisnis penjualan produk konsumen. Pengguna dapat berupa pemilik bisnis, kasir, atau manajer inventori yang membutuhkan alat untuk mengelola operasi harian dengan efisien.

---

## BAB 2 — SPESIFIKASI TEKNIS

### 2.1 Tech Stack

| Komponen | Teknologi | Versi |
|----------|-----------|-------|
| Bahasa   | Java      | 21    |
| UI Framework | JavaFX | 21    |
| Database | MySQL     | 8.0+  |
| IDE      | Apache NetBeans | 20+   |
| OS Target| Windows/Linux/Mac | 10+/Ubuntu 20+/macOS 12+ |

### 2.2 Library & Dependencies

| Library | Versi | Fungsi |
|---------|-------|--------|
| mysql-connector-j | 8.x | Konektor JDBC untuk MySQL |
| javafx-sdk | 21 | Framework UI untuk JavaFX |
| renaldy.css | Custom | Styling tema dark developer portfolio |

### 2.3 Spesifikasi Minimum Hardware

| Komponen | Minimum | Rekomendasi |
|----------|---------|-------------|
| OS       | Windows 10/Linux Ubuntu 20.04 | Windows 11/macOS 12+ |
| RAM      | 4 GB    | 8 GB        |
| Storage  | 500 MB  | 1 GB SSD    |
| Prosesor | Intel Core i3/AMD Ryzen 3 | Intel Core i5/AMD Ryzen 5 |

### 2.4 Arsitektur Sistem
```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  UI Layer   │────▶│  DAO Layer  │────▶│  Database   │
│  (Views)    │     │  (Queries)  │     │  (MySQL)    │
└─────────────┘     └─────────────┘     └─────────────┘
```

- **UI Layer (Views)**: Bertanggung jawab untuk menampilkan antarmuka pengguna dan menangani interaksi pengguna. Menggunakan JavaFX untuk komponen UI.
- **DAO Layer (Queries)**: Menangani semua operasi database seperti insert, update, delete, dan select. Menggunakan JDBC untuk koneksi ke MySQL.
- **Database Layer (MySQL)**: Menyimpan semua data aplikasi dalam struktur relasional, termasuk tabel untuk barang, pelanggan, transaksi, dll.

### 2.5 Design Pattern yang Digunakan
- **DAO Pattern**: Memisahkan logika akses data dari logika bisnis, memungkinkan perubahan database tanpa mempengaruhi kode aplikasi.
- **MVC (Model-View-Controller)**: Memisahkan model data (model), tampilan (view), dan logika kontrol (controller) untuk struktur kode yang lebih terorganisir.

---

## BAB 3 — STRUKTUR PROYEK

### 3.1 Folder Structure
AhmadRenaldyPOS/
├── src/
│   ├── dao/
│   │   ├── BarangDAO.java
│   │   ├── CustomerDAO.java
│   │   ├── SupplierDAO.java
│   │   └── TransaksiDAO.java
│   ├── main/
│   │   ├── Launcher.java
│   │   └── MainApp.java
│   ├── model/
│   │   ├── Barang.java
│   │   ├── Customer.java
│   │   ├── DetailTransaksi.java
│   │   ├── Supplier.java
│   │   └── Transaksi.java
│   ├── ui/
│   │   ├── MainController.java
│   │   └── views/
│   │       ├── BarangView.java
│   │       ├── CustomerView.java
│   │       ├── DashboardView.java
│   │       ├── InventoryView.java
│   │       ├── LaporanInventoryView.java
│   │       ├── LaporanTransaksiView.java
│   │       ├── SupplierView.java
│   │       └── TransaksiView.java
│   └── util/
│       ├── DBConnection.java
│       └── renaldy.css
├── sql/
│   └── renaldypos.sql
├── installer/
│   └── build-installer-notes.txt
└── README.txt

### 3.2 Penjelasan Per Package/Folder

| Package | Isi | Fungsi |
|---------|-----|--------|
| main    | MainApp.java, Launcher.java | Entry point aplikasi dan launcher JavaFX |
| ui/views | *.java | Semua tampilan layar dan komponen UI |
| dao     | *DAO.java | Query database dan operasi CRUD |
| model   | *.java | Representasi data dan objek bisnis |
| util    | *.java, *.css | Helper, konfigurasi, dan styling |

### 3.3 Deskripsi File Penting

**MainApp.java**
- Fungsi: Kelas utama aplikasi yang menginisialisasi JavaFX dan memuat scene utama.
- Class utama: MainApp
- Method penting: start(), main()

**Launcher.java**
- Fungsi: Launcher untuk JavaFX modular.
- Class utama: Launcher
- Method penting: main()

**DBConnection.java**
- Fungsi: Mengelola koneksi ke database MySQL.
- Class utama: DBConnection
- Method penting: getConnection(), closeConnection()

**BarangDAO.java**
- Fungsi: Menangani operasi database untuk entitas Barang.
- Class utama: BarangDAO
- Method penting: insertBarang(), updateBarang(), deleteBarang(), getAllBarang()

**BarangView.java**
- Fungsi: Tampilan UI untuk mengelola data barang.
- Class utama: BarangView
- Method penting: initialize(), handleAdd(), handleEdit()

**Barang.java**
- Fungsi: Model data untuk entitas Barang.
- Class utama: Barang
- Method penting: getter/setter untuk properti seperti id, nama, harga, dll.

---

## BAB 4 — DATABASE

### 4.1 Konfigurasi Koneksi
```java
// DBConnection.java
host     = "localhost"
port     = "3306"
database = "renaldypos"
user     = "root"
password = ""
```

### 4.2 Entity Relationship Diagram (ERD)
```
SUPPLIER ────┐
│ (1 to many)
▼
BARANG ──────────────── DETAIL_TRANSAKSI
│
│ (many to 1)
▼
CUSTOMER ──────────── TRANSAKSI
```

### 4.3 Skema Database Lengkap

#### Tabel: users
**Fungsi:** Menyimpan data pengguna aplikasi.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| username | VARCHAR(50) | UNIQUE, NOT NULL | Nama pengguna |
| password | VARCHAR(255) | NOT NULL | Kata sandi terenkripsi |

```sql
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);
```

#### Tabel: supplier
**Fungsi:** Menyimpan data pemasok barang.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| nama  | VARCHAR(100) | NOT NULL | Nama pemasok |
| alamat | TEXT      |            | Alamat pemasok |
| telepon | VARCHAR(20) |          | Nomor telepon |

```sql
CREATE TABLE supplier (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nama VARCHAR(100) NOT NULL,
  alamat TEXT,
  telepon VARCHAR(20)
);
```

#### Tabel: barang
**Fungsi:** Menyimpan data barang yang dijual.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| nama  | VARCHAR(100) | NOT NULL | Nama barang |
| harga | DECIMAL(10,2) | NOT NULL | Harga jual |
| stok  | INT       | NOT NULL | Jumlah stok |
| supplier_id | INT   | FK         | Foreign key ke supplier |

```sql
CREATE TABLE barang (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nama VARCHAR(100) NOT NULL,
  harga DECIMAL(10,2) NOT NULL,
  stok INT NOT NULL,
  supplier_id INT,
  FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);
```

#### Tabel: customer
**Fungsi:** Menyimpan data pelanggan.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| nama  | VARCHAR(100) | NOT NULL | Nama pelanggan |
| alamat | TEXT      |            | Alamat pelanggan |
| telepon | VARCHAR(20) |          | Nomor telepon |

```sql
CREATE TABLE customer (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nama VARCHAR(100) NOT NULL,
  alamat TEXT,
  telepon VARCHAR(20)
);
```

#### Tabel: transaksi
**Fungsi:** Menyimpan data transaksi penjualan.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| tanggal | DATETIME | NOT NULL | Tanggal transaksi |
| total | DECIMAL(10,2) | NOT NULL | Total harga |
| customer_id | INT   | FK         | Foreign key ke customer |

```sql
CREATE TABLE transaksi (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tanggal DATETIME NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  customer_id INT,
  FOREIGN KEY (customer_id) REFERENCES customer(id)
);
```

#### Tabel: detail_transaksi
**Fungsi:** Menyimpan detail item dalam transaksi.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| transaksi_id | INT | FK, NOT NULL | Foreign key ke transaksi |
| barang_id | INT   | FK, NOT NULL | Foreign key ke barang |
| jumlah | INT      | NOT NULL | Jumlah item |
| harga  | DECIMAL(10,2) | NOT NULL | Harga per item |

```sql
CREATE TABLE detail_transaksi (
  id INT PRIMARY KEY AUTO_INCREMENT,
  transaksi_id INT NOT NULL,
  barang_id INT NOT NULL,
  jumlah INT NOT NULL,
  harga DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (transaksi_id) REFERENCES transaksi(id),
  FOREIGN KEY (barang_id) REFERENCES barang(id)
);
```

#### Tabel: inventory_log
**Fungsi:** Menyimpan log perubahan inventori.

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|------------|------------|
| id    | INT       | PK, AUTO   | Primary key |
| barang_id | INT   | FK, NOT NULL | Foreign key ke barang |
| perubahan | INT   | NOT NULL | Perubahan stok (+/-) |
| tanggal | DATETIME | NOT NULL | Tanggal perubahan |
| keterangan | VARCHAR(255) |          | Keterangan perubahan |

```sql
CREATE TABLE inventory_log (
  id INT PRIMARY KEY AUTO_INCREMENT,
  barang_id INT NOT NULL,
  perubahan INT NOT NULL,
  tanggal DATETIME NOT NULL,
  keterangan VARCHAR(255),
  FOREIGN KEY (barang_id) REFERENCES barang(id)
);
```

### 4.4 Relasi Antar Tabel
- Supplier memiliki relasi one-to-many dengan Barang (satu supplier dapat menyediakan banyak barang).
- Customer memiliki relasi one-to-many dengan Transaksi (satu customer dapat melakukan banyak transaksi).
- Transaksi memiliki relasi one-to-many dengan DetailTransaksi (satu transaksi dapat memiliki banyak detail item).
- Barang memiliki relasi one-to-many dengan DetailTransaksi (satu barang dapat muncul di banyak detail transaksi).
- Barang memiliki relasi one-to-many dengan InventoryLog (satu barang dapat memiliki banyak log perubahan).

### 4.5 Sample Data
```sql
-- Insert sample data per tabel
INSERT INTO users (username, password) VALUES ('admin', 'password123');
INSERT INTO supplier (nama, alamat, telepon) VALUES ('PT. Supplier A', 'Jakarta', '021-123456');
INSERT INTO barang (nama, harga, stok, supplier_id) VALUES ('Barang A', 10000.00, 50, 1);
INSERT INTO customer (nama, alamat, telepon) VALUES ('Customer A', 'Bandung', '022-654321');
INSERT INTO transaksi (tanggal, total, customer_id) VALUES ('2023-01-01 10:00:00', 50000.00, 1);
INSERT INTO detail_transaksi (transaksi_id, barang_id, jumlah, harga) VALUES (1, 1, 5, 10000.00);
INSERT INTO inventory_log (barang_id, perubahan, tanggal, keterangan) VALUES (1, -5, '2023-01-01 10:00:00', 'Penjualan');
```

---

## BAB 5 — FITUR & MODUL

### 5.1 Daftar Fitur

| No | Fitur | Deskripsi | Status |
|----|-------|-----------|--------|
| 1  | CRUD Barang | Tambah, lihat, edit, hapus data barang | ✅ Done |
| 2  | CRUD Customer | Tambah, lihat, edit, hapus data pelanggan | ✅ Done |
| 3  | CRUD Supplier | Tambah, lihat, edit, hapus data pemasok | ✅ Done |
| 4  | Transaksi Penjualan | Proses transaksi penjualan dengan detail item | ✅ Done |
| 5  | Inventory Management | Pantau dan update stok barang | ✅ Done |
| 6  | Laporan Transaksi | Generate laporan penjualan | ✅ Done |
| 7  | Laporan Inventory | Generate laporan inventori | ✅ Done |
| 8  | Dashboard | Tampilan statistik dan navigasi utama | ✅ Done |
| 9  | Installer EXE | Build aplikasi menjadi installer executable | ✅ Done |

### 5.2 Detail Per Modul

#### Modul: Dashboard
**Fungsi:** Halaman utama aplikasi yang menampilkan statistik bisnis dan navigasi ke modul lain.

**Fitur yang tersedia:**
- Kartu statistik (total penjualan, stok rendah, dll.)
- Navigasi cepat ke modul lain

**Alur kerja (flow):**
1. Aplikasi dimuat dan menampilkan dashboard
2. Pengguna melihat statistik terkini
3. Pengguna klik menu untuk masuk ke modul tertentu

**Query utama yang digunakan:**
```sql
SELECT COUNT(*) as total_transaksi, SUM(total) as total_penjualan FROM transaksi WHERE DATE(tanggal) = CURDATE();
```

> 📸 [SCREENSHOT: tampilan dashboard dengan stat cards]

#### Modul: Barang
**Fungsi:** Mengelola data barang yang dijual.

**Fitur yang tersedia:**
- Tambah barang baru
- Edit data barang
- Hapus barang
- Cari dan filter barang

**Alur kerja (flow):**
1. Pengguna buka modul Barang
2. Tampilkan daftar barang
3. Pengguna pilih aksi (tambah/edit/hapus)
4. Simpan perubahan ke database

**Query utama yang digunakan:**
```sql
SELECT * FROM barang;
INSERT INTO barang (nama, harga, stok, supplier_id) VALUES (?, ?, ?, ?);
```

> 📸 [SCREENSHOT: tampilan modul barang]

#### Modul: Transaksi
**Fungsi:** Memproses transaksi penjualan.

**Fitur yang tersedia:**
- Pilih pelanggan
- Tambah item ke keranjang
- Hitung total
- Simpan transaksi

**Alur kerja (flow):**
1. Pilih pelanggan
2. Cari dan tambah barang ke keranjang
3. Masukkan jumlah
4. Hitung total dan simpan transaksi

**Query utama yang digunakan:**
```sql
INSERT INTO transaksi (tanggal, total, customer_id) VALUES (?, ?, ?);
INSERT INTO detail_transaksi (transaksi_id, barang_id, jumlah, harga) VALUES (?, ?, ?, ?);
UPDATE barang SET stok = stok - ? WHERE id = ?;
```

> 📸 [SCREENSHOT: tampilan modul transaksi]

#### Modul: Laporan
**Fungsi:** Menghasilkan laporan untuk analisis bisnis.

**Fitur yang tersedia:**
- Laporan transaksi berdasarkan periode
- Laporan inventori

**Alur kerja (flow):**
1. Pilih jenis laporan
2. Pilih periode tanggal
3. Generate dan tampilkan laporan

**Query utama yang digunakan:**
```sql
SELECT t.*, c.nama as customer_nama FROM transaksi t JOIN customer c ON t.customer_id = c.id WHERE t.tanggal BETWEEN ? AND ?;
```

> 📸 [SCREENSHOT: tampilan laporan transaksi]

---

## BAB 6 — PANDUAN INSTALASI & SETUP

### 6.1 Persiapan Environment
1. Install Java JDK 21 dari oracle.com
2. Install MySQL Server 8.0+ dari mysql.com
3. Install Apache NetBeans 20+ dari netbeans.apache.org
4. Pastikan JavaFX SDK 21 terinstall

### 6.2 Setup Database
```sql
-- Langkah 1: Buat database
CREATE DATABASE renaldypos;
USE renaldypos;

-- Langkah 2: Jalankan file SQL
-- File: sql/renaldypos.sql
-- Jalankan perintah: mysql -u root -p renaldypos < sql/renaldypos.sql
```

### 6.3 Setup Project di NetBeans
1. Buka NetBeans
2. File → Open Project → pilih folder AhmadRenaldyPOS
3. Klik kanan project → Properties → Libraries
4. Add JAR: mysql-connector-j-8.x.jar, javafx-sdk-21/lib/*.jar
5. Clean and Build (Shift+F11)
6. Run (F6)

### 6.4 Konfigurasi Koneksi Database
Jika MySQL menggunakan konfigurasi berbeda,
edit file `src/util/DBConnection.java`:
```java
// Ubah nilai berikut sesuai setup MySQL kamu:
private static final String HOST = "localhost";
private static final String PORT = "3306";
private static final String DB   = "renaldypos";
private static final String USER = "root";
private static final String PASS = "";
```

---

## BAB 7 — PANDUAN PENGGUNAAN

### 7.1 Alur Penggunaan Sistem
```
Login ──▶ Dashboard ──▶ Input Data ──▶ Transaksi
│              │
▼              ▼
Master Data    Laporan
```

### 7.2 Per Layar/Screen

#### Dashboard
> 📸 [SCREENSHOT: layar dashboard]

**Cara menggunakan:**
1. Lihat statistik di kartu atas
2. Klik menu di sidebar untuk navigasi

**Tombol & fungsinya:**
| Tombol | Fungsi |
|--------|--------|
| Barang | Buka modul Barang |
| Transaksi | Buka modul Transaksi |
| Laporan | Buka modul Laporan |

#### Barang
> 📸 [SCREENSHOT: layar barang]

**Cara menggunakan:**
1. Klik "Tambah" untuk barang baru
2. Isi form dan simpan
3. Klik "Edit" pada tabel untuk mengubah
4. Klik "Hapus" untuk menghapus

**Tombol & fungsinya:**
| Tombol | Fungsi |
|--------|--------|
| Tambah | Buka form tambah barang |
| Edit | Edit barang terpilih |
| Hapus | Hapus barang terpilih |

#### Transaksi
> 📸 [SCREENSHOT: layar transaksi]

**Cara menggunakan:**
1. Pilih pelanggan dari dropdown
2. Cari dan tambah barang ke keranjang
3. Masukkan jumlah
4. Klik "Simpan Transaksi"

**Tombol & fungsinya:**
| Tombol | Fungsi |
|--------|--------|
| Tambah ke Keranjang | Tambah item ke transaksi |
| Simpan Transaksi | Simpan transaksi ke database |

---

## BAB 8 — PANDUAN INSTALLER

### 8.1 Tools yang Dibutuhkan
| Tool | Link Download | Fungsi |
|------|--------------|--------|
| Launch4j | launch4j.sf.net | JAR → EXE |
| Inno Setup 6 | jrsoftware.org | EXE → Installer |

### 8.2 Langkah Build ke EXE

**Step 1 — Export JAR dari NetBeans:**
1. Klik kanan project → Clean and Build
2. JAR tersimpan di folder `dist/`
3. Pastikan folder `dist/lib/` berisi semua dependency

**Step 2 — Launch4j (JAR → EXE):**
1. Buka Launch4j
2. Load file `installer/launch4j-config.xml`
3. Klik tombol Build (gear icon)
4. Output: `installer/renaldy-pos.exe`

**Step 3 — Inno Setup (EXE → Installer):**
1. Buka Inno Setup
2. Load file `installer/renaldy-pos-setup.iss`
3. Build → Compile (Ctrl+F9)
4. Output: `installer/output/renaldy-pos-Setup.exe`

### 8.3 Struktur Folder Installer Final
installer/
├── launch4j-config.xml
├── renaldy-pos.exe              ← hasil Launch4j
├── renaldy-pos-setup.iss
└── output/
└── renaldy-pos-Setup.exe    ← FINAL ✅

### 8.4 Isi Installer
File yang di-bundle dalam installer:
- renaldy-pos.exe (aplikasi utama)
- lib/ (semua .jar dependency)
- jre/ (Java Runtime bundled)
- sql/ (script database)
- README.txt

---

## BAB 9 — PENANGANAN ERROR UMUM

| Error | Penyebab | Solusi |
|-------|----------|--------|
| Cannot connect to DB | MySQL tidak jalan | Start MySQL service |
| ClassNotFoundException | JAR tidak ada di lib/ | Tambah JAR ke project |
| SQLException | Query salah | Periksa syntax SQL |
| JavaFX runtime error | JavaFX tidak terinstall | Install JavaFX SDK |

---

## BAB 10 — PENUTUP

### 10.1 Kesimpulan
Ahmad Renaldy POS v1 adalah aplikasi desktop yang efektif untuk mengelola operasi POS di bisnis ritel. Dengan fitur lengkap CRUD, transaksi, dan laporan, aplikasi ini membantu meningkatkan efisiensi dan akurasi dalam pengelolaan bisnis. Dibangun dengan JavaFX dan MySQL, aplikasi ini menawarkan antarmuka modern dan penyimpanan data yang handal.

### 10.2 Rencana Pengembangan Selanjutnya
- [ ] Integrasi scanner barcode
- [ ] Dukungan multi-user dengan role
- [ ] Fitur online backup ke cloud
- [ ] Mobile app companion

### 10.3 Referensi
1. Dokumentasi JavaFX: openjfx.io
2. Dokumentasi MySQL: dev.mysql.com
3. Apache NetBeans: netbeans.apache.org

---

*Dokumentasi ini dibuat untuk keperluan
tugas mata kuliah Pemrograman 2*
*Ahmad Renaldy — [NIM] — Universitas Pamulang*