# PEMBUATAN APLIKASI SISTEM INFORMASI PENJUALAN BARANG BERBASIS JAVAFX

**Dosen Pengampu: Bambang Hadi Purnomo S.Kom.,M.T.I.,CFE**

---

**LAPORAN PEMROGRAMAN 2**

Disusun oleh:
1. Ahmad Renaldy

**PROGRAM STUDI TEKNIK INFORMATIKA**
**FAKULTAS TEKNIK UNIVERSITAS PAMULANG**
**TAHUN AKADEMIK 2024/2025**

---

## KATA PENGANTAR

Puji syukur penulis panjatkan kepada Allah SWT atas segala berkah, rahmat, dan karunia-Nya sehingga penulis dapat menyelesaikan laporan ini dengan baik. Laporan yang berjudul "Pembuatan Aplikasi Sistem Informasi Penjualan Barang Berbasis JavaFX" ini disusun sebagai salah satu tugas mata kuliah Pemrograman 2 yang diampu oleh Ibu/Bapak Bambang Hadi Purnomo S.Kom.,M.T.I.,CFE.

Penulis mengucapkan terima kasih yang sebesar-besarnya kepada:

1. **Allah SWT** yang telah memberikan kesehatan dan kemudahan dalam menjalani perkuliahan ini
2. **Ketua Yayasan Mumtaz** atas dukungan dan fasilitas yang telah diberikan
3. **Rektor Universitas Pamulang** atas kepemimpinan yang visioner
4. **Kaprodi Teknik Informatika** atas arahan dan kebijakan akademik yang mendukung pembelajaran
5. **Dosen Pengampu Ibu/Bapak Bambang Hadi Purnomo S.Kom.,M.T.I.,CFE** yang telah memberikan bimbingan, pengarahan, dan masukan berharga selama proses pembelajaran
6. **Orang tua** yang telah memberikan dukungan moral dan material
7. **Rekan-rekan mahasiswa** di Program Studi Teknik Informatika yang telah saling membantu dan berbagi ilmu

Penulis menyadari bahwa laporan ini masih jauh dari sempurna. Oleh karena itu, penulis dengan lapang dada menerima saran, masukan, dan kritik yang bersifat membangun untuk perbaikan laporan ini di masa mendatang.

Semoga laporan ini dapat bermanfaat bagi penulis khususnya dan pembaca pada umumnya dalam memperdalam pemahaman mengenai pengembangan aplikasi berbasis GUI dan pengelolaan basis data relasional menggunakan teknologi Java.

**Pamulang, 13 Mei 2026**

**Ahmad Renaldy**

---

## DAFTAR ISI

| Halaman |
|---------|
| Kata Pengantar | i |
| Daftar Isi | ii |
| Daftar Gambar | iii |
| Daftar Tabel | iv |
| **BAB I KEBUTUHAN PENGGUNA (SRS)** | 1 |
| 1.1 Pendahuluan | 1 |
| 1.1.1 Tujuan Dokumen | 1 |
| 1.1.2 Ruang Lingkup | 1 |
| 1.1.3 Definisi dan Singkatan | 2 |
| 1.2 Gambaran Umum Sistem | 3 |
| 1.3 Kebutuhan Fungsional | 4 |
| 1.4 Kebutuhan Non-Fungsional | 8 |
| 1.5 Batasan Sistem | 9 |
| **BAB II RANCANGAN DESAIN** | 11 |
| 2.1 Arsitektur Sistem | 11 |
| 2.2 Struktur Database (ERD) | 12 |
| 2.3 Relasi Antar Tabel | 15 |
| 2.4 Desain Antarmuka Pengguna | 16 |
| 2.5 Desain Laporan | 19 |
| **BAB III SCREENSHOT PROJECT** | 21 |
| 3.1 Halaman Dashboard | 21 |
| 3.2 Halaman Data Barang | 22 |
| 3.3 Halaman Data Customer | 23 |
| 3.4 Halaman Data Supplier | 24 |
| 3.5 Halaman Transaksi Penjualan | 25 |
| 3.6 Halaman Laporan Transaksi | 26 |
| 3.7 Halaman Laporan Inventory | 27 |
| **BAB IV CARA INSTALASI** | 28 |
| 4.1 Kebutuhan Sistem | 28 |
| 4.2 Langkah Instalasi | 29 |
| 4.3 Troubleshooting | 32 |
| **BAB V MANUAL BOOK** | 34 |
| 5.1 Memulai Aplikasi | 34 |
| 5.2 Navigasi Utama | 34 |
| 5.3 Mengelola Data Barang | 35 |
| 5.4 Mengelola Data Customer | 36 |
| 5.5 Mengelola Data Supplier | 36 |
| 5.6 Proses Transaksi Penjualan | 37 |
| 5.7 Melihat Laporan Transaksi | 38 |
| 5.8 Melihat Laporan Inventory | 39 |
| 5.9 Tips Penggunaan | 40 |

---

## DAFTAR GAMBAR

| | |
|---|---|
| Gambar 3.1 | Halaman Dashboard | hal 21 |
| Gambar 3.2 | Halaman Data Barang | hal 22 |
| Gambar 3.3 | Halaman Data Customer | hal 23 |
| Gambar 3.4 | Halaman Data Supplier | hal 24 |
| Gambar 3.5 | Halaman Transaksi Penjualan | hal 25 |
| Gambar 3.6 | Halaman Laporan Transaksi | hal 26 |
| Gambar 3.7 | Halaman Laporan Inventory | hal 27 |

---

## DAFTAR TABEL

| | |
|---|---|
| Tabel 1.1 | Definisi dan Singkatan | hal 2 |
| Tabel 1.2 | Aktor Sistem | hal 3 |
| Tabel 1.3 | Manajemen Data Barang | hal 4 |
| Tabel 1.4 | Manajemen Data Customer | hal 5 |
| Tabel 1.5 | Manajemen Data Supplier | hal 6 |
| Tabel 1.6 | Proses Transaksi Penjualan | hal 7 |
| Tabel 1.7 | Laporan | hal 7 |
| Tabel 1.8 | Dashboard | hal 8 |
| Tabel 1.9 | Kebutuhan Non-Fungsional | hal 8 |
| Tabel 2.1 | Arsitektur Sistem | hal 11 |
| Tabel 2.2 | Tabel: supplier | hal 12 |
| Tabel 2.3 | Tabel: barang | hal 12 |
| Tabel 2.4 | Tabel: customer | hal 13 |
| Tabel 2.5 | Tabel: transaksi | hal 13 |
| Tabel 2.6 | Tabel: detail_transaksi | hal 14 |
| Tabel 2.7 | Tabel: inventory_log | hal 14 |
| Tabel 2.8 | Relasi Antar Tabel | hal 15 |
| Tabel 2.9 | Tema Warna Aplikasi | hal 16 |
| Tabel 2.10 | Laporan Transaksi Penjualan | hal 19 |
| Tabel 2.11 | Laporan Inventory | hal 20 |
| Tabel 4.1 | Kebutuhan Sistem (Prerequisites) | hal 28 |
| Tabel 4.2 | Konfigurasi Koneksi Database | hal 30 |
| Tabel 4.3 | Troubleshooting | hal 32 |
| Tabel 5.1 | Navigasi Utama | hal 34 |
| Tabel 5.2 | Validasi Otomatis Sistem | hal 37 |
| Tabel 5.3 | Indikator Warna Baris Laporan | hal 39 |
| Tabel 5.4 | Tips Penggunaan | hal 40 |

---

# BAB I KEBUTUHAN PENGGUNA (SRS)

## 1.1 Pendahuluan

### 1.1.1 Tujuan Dokumen

Dokumen Software Requirements Specification (SRS) ini bertujuan untuk mengidentifikasi, menganalisis, dan mendokumentasikan seluruh kebutuhan fungsional dan non-fungsional aplikasi Sistem Informasi Penjualan Barang Berbasis JavaFX. Aplikasi ini dibangun dengan menggunakan bahasa pemrograman Java, framework JavaFX untuk antarmuka pengguna, dan sistem manajemen basis data MySQL untuk penyimpanan data persisten. Dokumen ini menjadi panduan teknis bagi pengembang dalam mengimplementasikan fitur-fitur sistem serta memastikan kualitas produk akhir sesuai dengan standar yang diharapkan.

### 1.1.2 Ruang Lingkup

Aplikasi Sistem Informasi Penjualan Barang (Ahmad Renaldy POS v1) dirancang untuk mengelola seluruh aspek penjualan retail di toko dengan fokus pada manajemen inventaris, data pelanggan, data supplier, dan proses transaksi. Sistem ini berjalan secara lokal (desktop application) dengan arsitektur Model-View-Controller berbasis JDBC untuk komunikasi dengan database MySQL. Ruang lingkup aplikasi mencakup enam modul utama: Dashboard, Manajemen Data Barang, Manajemen Data Customer, Manajemen Data Supplier, Proses Transaksi Penjualan, serta Laporan Transaksi dan Laporan Inventory. Sistem tidak melayani multi-user concurrent dan tidak menyediakan fitur backup database otomatis.

### 1.1.3 Definisi dan Singkatan

| Istilah | Keterangan |
|---------|-----------|
| SRS | Software Requirements Specification — dokumen spesifikasi kebutuhan perangkat lunak |
| CRUD | Create, Read, Update, Delete — operasi dasar terhadap data |
| GUI | Graphical User Interface — antarmuka berbasis grafis |
| JDBC | Java Database Connectivity — API Java untuk koneksi database |
| JavaFX | Framework GUI modern untuk aplikasi desktop Java |
| DAO | Data Access Object — pola desain untuk akses data ke database |
| MVC | Model-View-Controller — pola arsitektur aplikasi |
| MySQL | Sistem manajemen basis data relasional |
| NetBeans | IDE (Integrated Development Environment) untuk pengembangan Java |
| POS | Point of Sale — sistem transaksi penjualan di retail |
| Supplier | Pemasok/penyedia barang |
| Customer/Pelanggan | Pembeli produk |
| Transaksi | Proses jual-beli antara toko dan pelanggan |
| Inventory | Persediaan barang/stok |
| Invoice | Bukti transaksi/kwitansi penjualan |
| Validasi | Proses verifikasi data sebelum disimpan |
| Cascading | Penghapusan atau pembaruan data terkait secara otomatis |

Tabel 1.1 Definisi dan Singkatan

## 1.2 Gambaran Umum Sistem

Aplikasi Ahmad Renaldy POS v1 adalah sebuah sistem informasi yang dirancang untuk membantu manajemen toko retail dalam mengelola penjualan barang, inventaris, data pelanggan, dan supplier. Dalam konteks bisnis, aplikasi ini bertujuan untuk meningkatkan efisiensi operasional melalui otomasi proses transaksi, pencatatan stok secara real-time, dan penyediaan laporan penjualan yang akurat. Entitas utama yang dikelola adalah barang (produk), pelanggan (customer), supplier (pemasok), dan transaksi penjualan beserta detail item yang dijual.

Sistem ini menerapkan pola arsitektur Model-View-Controller dengan pemisahan logika bisnis (DAO layer) dari antarmuka pengguna (JavaFX View). Basis data terdiri dari tabel relasional yang saling terhubung dengan constraints foreign key untuk memastikan integritas referensial data.

**Aktor Sistem:**

| No | Aktor | Peran |
|----|-------|-------|
| 1 | Kasir/Pengguna Aplikasi | Melakukan input transaksi penjualan, mengelola data barang, pelanggan, dan supplier serta melihat laporan penjualan dan inventory |
| 2 | Administrator/Owner | Mengelola semua data sistem dan melihat laporan bisnis untuk pengambilan keputusan |
| 3 | Sistem MySQL Database | Menyimpan data persisten dan menjamin integritas data melalui constraints dan relasi |

Tabel 1.2 Aktor Sistem

## 1.3 Kebutuhan Fungsional

### 1. FR-01: Manajemen Data Barang

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-01.1 | Sistem memungkinkan pengguna untuk menambahkan barang baru dengan atribut: kode barang, nama barang, kategori, harga beli, harga jual, stok awal, dan satuan | Tinggi |
| FR-01.2 | Sistem menghasilkan ID barang otomatis dengan format BRG-001, BRG-002, dst | Tinggi |
| FR-01.3 | Sistem memungkinkan pengguna untuk memilih supplier sebagai sumber barang | Tinggi |
| FR-01.4 | Sistem menampilkan daftar seluruh barang dalam bentuk tabel dengan fitur pencarian berdasarkan nama barang atau kode barang | Tinggi |
| FR-01.5 | Sistem memungkinkan pengguna untuk mengubah data barang yang sudah ada (update) | Tinggi |
| FR-01.6 | Sistem memungkinkan pengguna untuk menghapus data barang | Sedang |
| FR-01.7 | Sistem menampilkan margin keuntungan (harga jual — harga beli) untuk setiap barang | Sedang |
| FR-01.8 | Sistem melakukan validasi duplikasi kode barang sebelum penyimpanan | Tinggi |

Tabel 1.3 Manajemen Data Barang

### 2. FR-02: Manajemen Data Customer

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-02.1 | Sistem memungkinkan pengguna untuk menambahkan customer baru dengan atribut: kode customer, nama customer, telepon, alamat, email | Tinggi |
| FR-02.2 | Sistem menghasilkan ID customer otomatis dengan format CST-001, CST-002, dst | Tinggi |
| FR-02.3 | Sistem menampilkan daftar seluruh customer dalam bentuk tabel dengan fitur pencarian | Tinggi |
| FR-02.4 | Sistem memungkinkan pengguna untuk mengubah data customer | Tinggi |
| FR-02.5 | Sistem memungkinkan pengguna untuk menghapus data customer | Sedang |
| FR-02.6 | Sistem mencatat waktu pembuatan customer (created_at) secara otomatis | Sedang |

Tabel 1.4 Manajemen Data Customer

### 3. FR-03: Manajemen Data Supplier

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-03.1 | Sistem memungkinkan pengguna untuk menambahkan supplier baru dengan atribut: kode supplier, nama supplier, telepon, alamat, email | Tinggi |
| FR-03.2 | Sistem menghasilkan ID supplier otomatis dengan format SUP-001, SUP-002, dst | Tinggi |
| FR-03.3 | Sistem menampilkan daftar seluruh supplier dalam bentuk tabel | Tinggi |
| FR-03.4 | Sistem memungkinkan pengguna untuk mengubah data supplier | Tinggi |
| FR-03.5 | Sistem memungkinkan pengguna untuk menghapus data supplier | Sedang |
| FR-03.6 | Sistem mencegah penghapusan supplier yang masih mempunyai barang terkait (constraint) | Tinggi |

Tabel 1.5 Manajemen Data Supplier

### 4. FR-04: Proses Transaksi Penjualan

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-04.1 | Sistem memungkinkan pengguna untuk membuat transaksi penjualan baru dengan memilih customer, tanggal, dan keterangan | Tinggi |
| FR-04.2 | Sistem menghasilkan nomor transaksi otomatis dengan format TRX-00001, TRX-00002, dst | Tinggi |
| FR-04.3 | Sistem memungkinkan pengguna untuk menambahkan barang ke keranjang penjualan dengan spesifikasi: kode barang, jumlah pembelian, harga satuan | Tinggi |
| FR-04.4 | Sistem secara otomatis menghitung subtotal (jumlah × harga satuan) untuk setiap item | Tinggi |
| FR-04.5 | Sistem menampilkan total harga, diskon, dan grand total secara real-time di interface | Tinggi |
| FR-04.6 | Sistem memvalidasi stok barang — jika stok kurang dari jumlah pembelian, tampilkan pesan error | Tinggi |
| FR-04.7 | Sistem memungkinkan pengguna untuk mengubah jumlah pembelian atau menghapus item dari keranjang | Tinggi |
| FR-04.8 | Sistem memungkinkan pengguna untuk memberikan diskon pada transaksi (dalam persen atau nominal) | Sedang |
| FR-04.9 | Sistem memungkinkan pengguna untuk menginput jumlah bayar dan secara otomatis menghitung kembalian | Tinggi |
| FR-04.10 | Sistem memvalidasi bahwa jumlah bayar ≥ grand total — jika tidak, tampilkan pesan error | Tinggi |
| FR-04.11 | Sistem mengurangi stok barang secara otomatis ketika transaksi diselesaikan (commit) | Tinggi |
| FR-04.12 | Sistem menyimpan transaksi dan detail item ke database (atomicity — semua atau gagal semua) | Tinggi |
| FR-04.13 | Sistem menampilkan riwayat transaksi di bagian bawah interface transaksi | Sedang |
| FR-04.14 | Sistem memvalidasi bahwa keranjang tidak kosong sebelum proses transaksi | Tinggi |

Tabel 1.6 Proses Transaksi Penjualan

### 5. FR-05: Laporan

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-05.1 | Sistem menyediakan laporan transaksi penjualan yang menampilkan: ID transaksi, tanggal, nama customer, total harga, diskon, dan grand total | Tinggi |
| FR-05.2 | Sistem menyediakan fitur filter laporan transaksi berdasarkan rentang tanggal (dari — sampai) | Tinggi |
| FR-05.3 | Sistem menampilkan summary: total transaksi, total penjualan (grand total), jumlah item terjual dalam periode laporan | Sedang |
| FR-05.4 | Sistem menyediakan laporan inventory yang menampilkan: kode barang, nama barang, stok, harga beli, harga jual, margin | Tinggi |
| FR-05.5 | Sistem menampilkan indikator warna pada laporan inventory: merah untuk stok 0, kuning untuk stok ≤ 5, putih untuk stok aman (> 5) | Sedang |
| FR-05.6 | Sistem menyediakan fitur refresh/reload untuk memperbarui data laporan | Sedang |

Tabel 1.7 Laporan

### 6. FR-06: Dashboard

| ID | Kebutuhan Fungsional | Prioritas |
|----|----------------------|-----------|
| FR-06.1 | Sistem menampilkan dashboard halaman utama dengan ringkasan statistik: total barang, total customer, total supplier, jumlah transaksi hari ini | Tinggi |
| FR-06.2 | Sistem menampilkan kartu statistik (card) dengan warna visual yang menarik dan mudah dibaca | Sedang |
| FR-06.3 | Sistem menampilkan sidebar navigasi dengan daftar menu: Dashboard, Data Barang, Data Customer, Data Supplier, Penjualan, Lap. Transaksi, Lap. Inventory | Tinggi |

Tabel 1.8 Dashboard

## 1.4 Kebutuhan Non-Fungsional

| ID | Kategori | Kebutuhan | Target |
|----|----------|-----------|--------|
| NFR-01 | Performa | Waktu respon operasi CRUD pada dataset < 1000 record | < 2 detik |
| NFR-02 | Keandalan | Integritas data transaksi dengan dukungan commit dan rollback | Konsisten 100% |
| NFR-03 | Keamanan | Validasi input untuk mencegah SQL injection dan serangan umum | 100% field validasi |
| NFR-04 | Usabilitas | Antarmuka pengguna dalam bahasa Indonesia | Semua label dan menu |
| NFR-05 | Portabilitas | Aplikasi dapat berjalan di Windows, Linux, macOS (Java cross-platform) | Multi-platform |
| NFR-06 | Kompatibilitas | Kompatibel dengan JDK 11 atau lebih baru dan MySQL 8.0 atau lebih baru | JDK 11+, MySQL 8.0+ |
| NFR-07 | Pemeliharaan | Struktur kode terpisah antara Model, View, dan Controller (MVC) serta DAO terpisah | 100% implementasi |
| NFR-08 | Akurasi Kalkulasi | Perhitungan harga, diskon, dan kembalian tanpa rounding error | Presisi 2 desimal (rupiah) |
| NFR-09 | Konsistensi | Stok barang konsisten antara tabel barang dan detail transaksi | Sinkronisasi real-time |

Tabel 1.9 Kebutuhan Non-Fungsional

## 1.5 Batasan Sistem

1. Aplikasi hanya berjalan sebagai desktop application lokal, tidak berbasis web
2. Sistem tidak mendukung multi-user concurrent — hanya satu pengguna pada satu waktu
3. Database harus diinstall terlebih dahulu di mesin lokal (tidak cloud-based)
4. Aplikasi tidak menyediakan fitur backup database otomatis — pengguna harus melakukan backup manual
5. Sistem tidak memiliki fitur autentikasi/login — siapa saja yang membuka aplikasi dapat mengakses semua data
6. Tidak ada fitur sync data dengan server atau perangkat lain
7. Laporan hanya ditampilkan di layar (view mode) — tidak ada fitur export ke file PDF atau Excel
8. Sistem tidak mendukung multiple warehouse/cabang — hanya untuk satu lokasi/toko
9. Validasi input dilakukan di sisi aplikasi — tidak ada validasi di sisi database layer yang ketat
10. Desain antarmuka mengikuti satu tema warna yang konsisten tanpa opsi personalisasi user

---

# BAB II RANCANGAN DESAIN

## 2.1 Arsitektur Sistem

Aplikasi Ahmad Renaldy POS v1 menerapkan pola arsitektur Model-View-Controller (MVC) dengan pemisahan yang jelas antara tiga layer utama. Layer Model mewakili struktur data dan entity bisnis yang abstrak dari mekanisme penyimpanan. Layer View menangani seluruh presentasi dan interaksi dengan pengguna melalui interface JavaFX. Layer Controller (DAO) mengatur logika bisnis dan komunikasi dengan database melalui JDBC, memastikan bahwa operasi data selalu valid dan konsisten. Pemisahan concern ini memudahkan maintenance, testing, dan pengembangan fitur baru tanpa mengganggu komponen lain.

| Layer | Komponen | Deskripsi |
|-------|----------|-----------|
| Model | Barang, Customer, Supplier, Transaksi, DetailTransaksi | Kelas-kelas yang merepresentasikan entitas domain bisnis dengan properties getter/setter |
| View | MainController, BarangView, CustomerView, SupplierView, TransaksiView, DashboardView, LaporanTransaksiView, LaporanInventoryView | Komponen JavaFX yang menangani tampilan antarmuka pengguna, input form, tabel, dan event handling |
| Controller (DAO) | BarangDAO, CustomerDAO, SupplierDAO, TransaksiDAO | Kelas yang mengelola operasi database (CRUD), query execution, dan business logic |
| Database | DBConnection + MySQL | Utility koneksi JDBC dan MySQL server untuk penyimpanan data persisten |
| Utility | Formatter, Validator, Helper | Fungsi bantu: formatting rupiah, generating ID otomatis, validasi input |

Tabel 2.1 Arsitektur Sistem

## 2.2 Struktur Database (ERD)

Sistem database menggunakan 6 tabel relasional dengan relationship yang jelas. Tabel supplier menyimpan data pemasok. Tabel barang menyimpan data produk dan referensi ke supplier. Tabel customer menyimpan data pelanggan. Tabel transaksi menyimpan header transaksi penjualan. Tabel detail_transaksi menyimpan baris-baris item dalam transaksi. Tabel inventory_log mencatat setiap perubahan stok barang untuk audit trail.

### 1. Tabel: supplier

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_supplier | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik supplier, auto-generated |
| kode_supplier | VARCHAR(20) | UNIQUE, NOT NULL | Kode unik supplier (SUP-001, SUP-002, dst) |
| nama_supplier | VARCHAR(100) | NOT NULL | Nama lengkap supplier |
| telepon | VARCHAR(20) | - | Nomor telepon supplier |
| alamat | TEXT | - | Alamat lengkap supplier |
| email | VARCHAR(100) | - | Alamat email supplier |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu data dibuat |

Tabel 2.2 Tabel: supplier

### 2. Tabel: barang

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_barang | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik barang |
| kode_barang | VARCHAR(20) | UNIQUE, NOT NULL | Kode barang (BRG-001, BRG-002, dst) |
| nama_barang | VARCHAR(100) | NOT NULL | Nama produk |
| kategori | VARCHAR(50) | - | Kategori produk (Elektronik, ATK, Aksesoris, dst) |
| harga_beli | DECIMAL(15,2) | NOT NULL | Harga pembelian dari supplier (Rp) |
| harga_jual | DECIMAL(15,2) | NOT NULL | Harga penjualan ke customer (Rp) |
| stok | INT | DEFAULT 0 | Jumlah stok barang saat ini |
| satuan | VARCHAR(20) | - | Satuan produk (Pcs, Rim, Unit, Botol, dst) |
| id_supplier | INT | FOREIGN KEY, ON DELETE SET NULL | Referensi ke supplier |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu data dibuat |

Tabel 2.3 Tabel: barang

### 3. Tabel: customer

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_customer | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik customer |
| kode_customer | VARCHAR(20) | UNIQUE, NOT NULL | Kode customer (CST-001, CST-002, dst) |
| nama_customer | VARCHAR(100) | NOT NULL | Nama lengkap customer |
| telepon | VARCHAR(20) | - | Nomor telepon customer |
| alamat | TEXT | - | Alamat lengkap customer |
| email | VARCHAR(100) | - | Alamat email customer |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu data dibuat |

Tabel 2.4 Tabel: customer

### 4. Tabel: transaksi

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_transaksi | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik transaksi internal |
| no_transaksi | VARCHAR(30) | UNIQUE, NOT NULL | Nomor transaksi (TRX-00001, dst) |
| id_customer | INT | FOREIGN KEY, ON DELETE SET NULL | Referensi ke customer |
| tanggal | DATETIME | DEFAULT CURRENT_TIMESTAMP | Waktu transaksi dilakukan |
| total_harga | DECIMAL(15,2) | - | Total harga sebelum diskon |
| diskon | DECIMAL(5,2) | DEFAULT 0 | Besar diskon dalam persen |
| grand_total | DECIMAL(15,2) | - | Total harga akhir (total_harga — diskon) |
| status | VARCHAR(20) | DEFAULT 'lunas' | Status transaksi (lunas/pending) |
| catatan | TEXT | - | Catatan tambahan transaksi |

Tabel 2.5 Tabel: transaksi

### 5. Tabel: detail_transaksi

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_detail | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik detail transaksi |
| id_transaksi | INT | FOREIGN KEY, ON DELETE CASCADE | Referensi ke transaksi (cascade delete) |
| id_barang | INT | FOREIGN KEY, ON DELETE SET NULL | Referensi ke barang |
| jumlah | INT | NOT NULL | Jumlah barang dalam transaksi |
| harga_satuan | DECIMAL(15,2) | NOT NULL | Harga per unit pada saat transaksi |
| subtotal | DECIMAL(15,2) | NOT NULL | Jumlah × harga_satuan |

Tabel 2.6 Tabel: detail_transaksi

### 6. Tabel: inventory_log

| Kolom | Tipe Data | Constraint | Keterangan |
|-------|-----------|-----------|-----------|
| id_log | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik log |
| id_barang | INT | FOREIGN KEY, ON DELETE SET NULL | Referensi ke barang |
| jenis | VARCHAR(10) | - | Jenis transaksi (IN/OUT) |
| jumlah | INT | - | Besar perubahan stok |
| keterangan | VARCHAR(200) | - | Keterangan alasan perubahan |
| tanggal | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu perubahan stok |

Tabel 2.7 Tabel: inventory_log

## 2.3 Relasi Antar Tabel

| Relasi | Kardinalitas | Aksi | Keterangan |
|--------|-------------|------|-----------|
| supplier → barang | 1 : N | ON UPDATE CASCADE, ON DELETE SET NULL | Satu supplier dapat memasok banyak barang; jika supplier dihapus, id_supplier pada barang di-set NULL |
| customer → transaksi | 1 : N | ON UPDATE CASCADE, ON DELETE SET NULL | Satu customer dapat memiliki banyak transaksi; jika customer dihapus, id_customer pada transaksi di-set NULL |
| transaksi → detail_transaksi | 1 : N | ON UPDATE CASCADE, ON DELETE CASCADE | Satu transaksi memiliki banyak baris item; jika transaksi dihapus, semua detail-nya juga terhapus |
| barang → detail_transaksi | 1 : N | ON UPDATE CASCADE, ON DELETE SET NULL | Satu barang dapat muncul di banyak detail transaksi; jika barang dihapus, id_barang di-set NULL |
| barang → inventory_log | 1 : N | ON UPDATE CASCADE, ON DELETE SET NULL | Satu barang dapat memiliki banyak log perubahan stok |

Tabel 2.8 Relasi Antar Tabel

## 2.4 Desain Antarmuka Pengguna

### 2.4.1 Layout Utama (MainFrame)

Antarmuka aplikasi mengikuti pola layout modern dengan tiga bagian utama:

**1. Header Bar (Atas)**
- Tinggi: 60 px
- Background: Dark Navy (#0D1117)
- Konten: Logo aplikasi, judul ("Ahmad Renaldy POS v1"), dan informasi pengguna
- Font: Segoe UI, bold, 14px

**2. Sidebar (Kiri)**
- Lebar: 230 px (dapat disembunyikan)
- Background: Surface Grey (#161B22)
- Border: Subtle line (#00BCD4) di kanan untuk aksen
- Konten: Logo brand, daftar menu navigasi
- Menu items: Dashboard, Data Barang, Data Customer, Data Supplier, Transaksi Penjualan, Laporan Transaksi, Laporan Inventory

**3. Content Area (Tengah-Kanan)**
- Background: Dark Navy (#0D1117)
- Konten dinamis berubah sesuai menu yang dipilih
- Padding: 16px di semua sisi untuk spacing yang nyaman

### 2.4.2 Tema Warna Aplikasi

| Nama Warna | Kode Hex | Penggunaan |
|------------|----------|-----------|
| Navy Dark | #0D1117 | Background aplikasi utama |
| Surface Grey | #161B22 | Background sidebar, panel, card |
| Cyan Accent | #00BCD4 | Tombol aktif, accent highlight, border, link |
| Text Primary | #F0F6FC | Teks label, heading utama |
| Text Secondary | #94A3B8 | Teks deskripsi, placeholder, hint |
| White | #FFFFFF | Latar form input, cell tabel |
| Grey Button | #808080 | Tombol Batal/Reset |
| Green Success | #4CAF50 | Tombol Simpan/Berhasil |
| Red Error | #FF6B6B | Tombol Hapus/Error |

Tabel 2.9 Tema Warna Aplikasi

### 2.4.3 Pola Desain Form CRUD

Setiap panel manajemen data (Barang, Customer, Supplier) mengikuti pola desain yang konsisten:

**Panel Kiri (Form Input)**
- JSplitPane vertical left side
- Form fields terstruktur vertikal
- Label di atas setiap input field
- Input fields: JTextField, JComboBox (untuk dropdown supplier/customer)
- Tombol aksi di bawah: Simpan (hijau), Update (biru), Hapus (merah), Batal (abu)
- Tombol diatur horizontal dengan spacing 10px

**Panel Kanan (Tabel Data)**
- JTable dengan model DefaultTableModel
- Column headers: ID, Kode, Nama, [field lain], Aksi
- Row selection: single selection, click row → data auto-populate ke form
- Alternating row colors: putih dan light grey untuk readability
- Scrollbar: auto-show ketika row > viewport height
- Double-click atau single-click event populate form

**Interaksi:**
1. **Simpan**: Tombol aktif saat form kosong atau pengguna belum klik baris
2. **Update/Hapus**: Tombol aktif hanya saat ada baris yang dipilih di tabel
3. **Batal**: Reset form dan clear selection
4. **Search**: Input field untuk cari berdasarkan nama (live search optional)

### 2.4.4 Panel Transaksi

Panel transaksi memiliki layout khusus dengan multi-section:

**Section 1: Header Transaksi (Atas)**
- Row 1: ID Transaksi (auto), Tanggal (date picker), Customer (dropdown)
- Row 2: Catatan (text area)

**Section 2: Tambah Item (Tengah-Atas)**
- Dropdown: Pilih Barang (tampilkan kode + nama)
- Input: Jumlah (spinner/number field)
- Tombol: Tambah Item (hijau)

**Section 3: Tabel Keranjang (Tengah)**
- Kolom: No, Kode Barang, Nama Barang, Qty, Harga Satuan, Subtotal
- Tombol Hapus Item (merah) per baris
- Alternating colors

**Section 4: Total & Bayar (Bawah)**
- Total Harga (read-only)
- Diskon % (input)
- Grand Total (calculated)
- Bayar (input)
- Kembalian (calculated)
- Tombol: Proses Transaksi (hijau), Reset (abu), Hapus Semua Item (merah)

**Section 5: Riwayat Transaksi (Bawah)**
- Tabel riwayat transaksi hari ini atau bulan ini
- Kolom: No Transaksi, Tanggal, Customer, Total

## 2.5 Desain Laporan

### 2.5.1 Laporan Transaksi Penjualan

Laporan transaksi menampilkan detail setiap penjualan dalam format tabel. Fitur filter memungkinkan pengguna melihat data transaksi pada rentang tanggal spesifik.

| Kolom | Sumber Data | Format / Keterangan |
|-------|-------------|---------------------|
| No Transaksi | transaksi.no_transaksi | TRX-00001 format |
| Tanggal | transaksi.tanggal | DD-MM-YYYY HH:MM:SS |
| Nama Customer | customer.nama_customer | Teks |
| Total Item | COUNT(detail_transaksi) | Jumlah item/baris |
| Total Harga | transaksi.total_harga | Rp format (contoh: Rp 8.750.000) |
| Diskon (%) | transaksi.diskon | Persen (contoh: 5%) |
| Grand Total | transaksi.grand_total | Rp format |
| Status | transaksi.status | lunas / pending |

Tabel 2.10 Laporan Transaksi Penjualan

**Fitur Filter & Summary:**
- Input: Tanggal Dari (date picker), Tanggal Sampai (date picker)
- Tombol: Filter, Reset
- Summary di sebelah kanan: Total Transaksi (count), Total Penjualan (sum grand_total), Total Item Terjual (sum jumlah)

### 2.5.2 Laporan Inventory

Laporan inventory menampilkan status stok barang dengan indikator visual untuk membantu pengambilan keputusan pemesanan.

| Kolom | Sumber Data | Format / Keterangan |
|-------|-------------|---------------------|
| Kode Barang | barang.kode_barang | BRG-001 format |
| Nama Barang | barang.nama_barang | Teks |
| Kategori | barang.kategori | Kategori produk |
| Stok | barang.stok | Angka |
| Harga Beli | barang.harga_beli | Rp format |
| Harga Jual | barang.harga_jual | Rp format |
| Margin | harga_jual — harga_beli | Rp format, color: hijau jika positif |
| Supplier | supplier.nama_supplier | Nama supplier |
| Satuan | barang.satuan | Pcs/Rim/Unit/Botol |

Tabel 2.11 Laporan Inventory

**Indikator Warna Baris:**
- **Merah Muda** (#FFE0E0): Stok = 0 (Habis — segera pesan)
- **Kuning Muda** (#FFF9E0): Stok ≤ 5 (Hampir habis — pertimbangkan pesan)
- **Putih/Krem** (#F5F5F0): Stok > 5 (Aman — tidak perlu tindakan)

**Fitur Tambahan:**
- Panel Summary di atas tabel: Total Item (count barang), Total Nilai Inventory (sum stok × harga_jual)
- Tombol Refresh untuk reload data dari database
- Legenda warna di bawah tabel untuk referensi pengguna

---

**LANJUT BAB III — BAB V DI PESAN BERIKUTNYA**

Laporan Bab I dan II sudah siap. Saya akan melanjutkan dengan:
- **BAB III**: Screenshot Project (7 halaman utama)
- **BAB IV**: Cara Instalasi (Prerequisites, langkah-langkah, troubleshooting)
- **BAB V**: Manual Book (navigasi, tutorial setiap fitur, tips penggunaan)

Apakah Anda ingin saya lanjutkan sekarang, atau ada revisi untuk Bab I-II terlebih dahulu?
