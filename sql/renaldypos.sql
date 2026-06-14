-- ============================================================
-- RenaldyPOS Database Schema
-- Ahmad Renaldy — POS System
-- Database: renaldypos
-- ============================================================

CREATE DATABASE IF NOT EXISTS renaldypos
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE renaldypos;

-- ============================================================
-- TABLE: supplier
-- ============================================================
CREATE TABLE IF NOT EXISTS supplier (
  id_supplier   INT AUTO_INCREMENT PRIMARY KEY,
  kode_supplier VARCHAR(20) UNIQUE NOT NULL,
  nama_supplier VARCHAR(100) NOT NULL,
  telepon       VARCHAR(20),
  alamat        TEXT,
  email         VARCHAR(100),
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- TABLE: barang
-- ============================================================
CREATE TABLE IF NOT EXISTS barang (
  id_barang   INT AUTO_INCREMENT PRIMARY KEY,
  kode_barang VARCHAR(20) UNIQUE NOT NULL,
  nama_barang VARCHAR(100) NOT NULL,
  kategori    VARCHAR(50),
  harga_beli  DECIMAL(15,2) NOT NULL,
  harga_jual  DECIMAL(15,2) NOT NULL,
  stok        INT DEFAULT 0,
  satuan      VARCHAR(20),
  id_supplier INT,
  created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_supplier) REFERENCES supplier(id_supplier)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- TABLE: customer
-- ============================================================
CREATE TABLE IF NOT EXISTS customer (
  id_customer   INT AUTO_INCREMENT PRIMARY KEY,
  kode_customer VARCHAR(20) UNIQUE NOT NULL,
  nama_customer VARCHAR(100) NOT NULL,
  telepon       VARCHAR(20),
  alamat        TEXT,
  email         VARCHAR(100),
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- TABLE: transaksi
-- ============================================================
CREATE TABLE IF NOT EXISTS transaksi (
  id_transaksi  INT AUTO_INCREMENT PRIMARY KEY,
  no_transaksi  VARCHAR(30) UNIQUE NOT NULL,
  id_customer   INT,
  tanggal       DATETIME DEFAULT CURRENT_TIMESTAMP,
  total_harga   DECIMAL(15,2),
  diskon        DECIMAL(5,2) DEFAULT 0,
  grand_total   DECIMAL(15,2),
  status        VARCHAR(20) DEFAULT 'lunas',
  catatan       TEXT,
  FOREIGN KEY (id_customer) REFERENCES customer(id_customer)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- TABLE: detail_transaksi
-- ============================================================
CREATE TABLE IF NOT EXISTS detail_transaksi (
  id_detail    INT AUTO_INCREMENT PRIMARY KEY,
  id_transaksi INT,
  id_barang    INT,
  jumlah       INT NOT NULL,
  harga_satuan DECIMAL(15,2) NOT NULL,
  subtotal     DECIMAL(15,2) NOT NULL,
  FOREIGN KEY (id_transaksi) REFERENCES transaksi(id_transaksi)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_barang) REFERENCES barang(id_barang)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- TABLE: inventory_log
-- ============================================================
CREATE TABLE IF NOT EXISTS inventory_log (
  id_log     INT AUTO_INCREMENT PRIMARY KEY,
  id_barang  INT,
  jenis      VARCHAR(10),
  jumlah     INT,
  keterangan VARCHAR(200),
  tanggal    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_barang) REFERENCES barang(id_barang)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Suppliers
INSERT INTO supplier (kode_supplier, nama_supplier, telepon, alamat, email) VALUES
('SUP-001', 'PT Sumber Makmur',    '081234567890', 'Jl. Industri No. 10, Jakarta',        'info@sumbermakmur.co.id'),
('SUP-002', 'CV Mitra Sejahtera',   '082345678901', 'Jl. Perdagangan No. 25, Bandung',     'admin@mitrasejahtera.com'),
('SUP-003', 'UD Berkah Jaya',       '083456789012', 'Jl. Raya Pasar No. 7, Surabaya',      'berkah@jayagroup.com'),
('SUP-004', 'PT Global Tekno',      '084567890123', 'Jl. Teknologi No. 88, Tangerang',     'sales@globaltekno.id'),
('SUP-005', 'CV Mandiri Supply',    '085678901234', 'Jl. Mandiri Blok C5, Bekasi',         'order@mandirisupply.com');

-- Barang (Products)
INSERT INTO barang (kode_barang, nama_barang, kategori, harga_beli, harga_jual, stok, satuan, id_supplier) VALUES
('BRG-001', 'Laptop ASUS VivoBook 14',    'Elektronik',   7500000.00, 8500000.00,  15, 'Unit', 4),
('BRG-002', 'Mouse Logitech M331',         'Aksesoris',    180000.00,  250000.00,   50, 'Pcs',  4),
('BRG-003', 'Kertas HVS A4 70gsm',         'ATK',          35000.00,   45000.00,   200, 'Rim',  1),
('BRG-004', 'Tinta Printer Epson L3150',    'ATK',          85000.00,  120000.00,    3, 'Botol', 2),
('BRG-005', 'Kabel HDMI 2m',               'Aksesoris',    45000.00,   75000.00,    0, 'Pcs',  3);

-- Customers
INSERT INTO customer (kode_customer, nama_customer, telepon, alamat, email) VALUES
('CST-001', 'Budi Santoso',      '081111222333', 'Jl. Merdeka No. 45, Jakarta Pusat',   'budi.santoso@email.com'),
('CST-002', 'Siti Aisyah',       '082222333444', 'Jl. Kemang Raya No. 12, Jakarta',     'siti.aisyah@email.com'),
('CST-003', 'Ahmad Fauzi',       '083333444555', 'Jl. Sudirman No. 100, Bandung',       'ahmad.fauzi@email.com'),
('CST-004', 'Dewi Lestari',      '084444555666', 'Jl. Diponegoro No. 8, Surabaya',      'dewi.lestari@email.com'),
('CST-005', 'Rudi Hermawan',     '085555666777', 'Jl. Gatot Subroto No. 55, Semarang',  'rudi.h@email.com');

-- Transaksi (Transactions)
INSERT INTO transaksi (no_transaksi, id_customer, tanggal, total_harga, diskon, grand_total, status, catatan) VALUES
('TRX-00001', 1, '2025-05-10 09:30:00', 8750000.00,  5.00, 8312500.00, 'lunas',   'Pembelian laptop + mouse'),
('TRX-00002', 2, '2025-05-10 11:15:00', 250000.00,   0.00, 250000.00,  'lunas',   'Pembelian mouse wireless'),
('TRX-00003', 3, '2025-05-10 14:00:00', 285000.00,  10.00, 256500.00,  'lunas',   'ATK kantor'),
('TRX-00004', 4, '2025-05-11 08:45:00', 8500000.00,  0.00, 8500000.00, 'lunas',   'Laptop untuk kantor'),
('TRX-00005', 5, '2025-05-11 10:30:00', 570000.00,   5.00, 541500.00,  'pending', 'Tinta printer + kertas');

-- Detail Transaksi
INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah, harga_satuan, subtotal) VALUES
(1, 1, 1, 8500000.00, 8500000.00),
(1, 2, 1, 250000.00,  250000.00),
(2, 2, 1, 250000.00,  250000.00),
(3, 3, 3, 45000.00,   135000.00),
(3, 4, 1, 120000.00,  120000.00),
(3, 5, 1, 75000.00,   75000.00),  -- Kabel HDMI termasuk
(4, 1, 1, 8500000.00, 8500000.00),
(5, 4, 2, 120000.00,  240000.00),
(5, 3, 2, 45000.00,   90000.00),
(5, 5, 2, 75000.00,   150000.00);  -- Kabel HDMI lagi

-- Inventory Log
INSERT INTO inventory_log (id_barang, jenis, jumlah, keterangan) VALUES
(1, 'masuk',  20,  'Stok awal dari supplier'),
(2, 'masuk',  60,  'Stok awal dari supplier'),
(3, 'masuk',  210, 'Stok awal dari supplier'),
(4, 'masuk',  10,  'Stok awal dari supplier'),
(5, 'masuk',  5,   'Stok awal dari supplier'),
(1, 'keluar', 1,   'Penjualan TRX-00001'),
(2, 'keluar', 1,   'Penjualan TRX-00001'),
(2, 'keluar', 1,   'Penjualan TRX-00002'),
(3, 'keluar', 3,   'Penjualan TRX-00003'),
(4, 'keluar', 1,   'Penjualan TRX-00003'),
(1, 'keluar', 1,   'Penjualan TRX-00004'),
(4, 'keluar', 2,   'Penjualan TRX-00005'),
(3, 'keluar', 2,   'Penjualan TRX-00005');
