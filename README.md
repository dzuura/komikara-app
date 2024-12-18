# Komikara - Aplikasi Baca Komik

## Deskripsi Proyek
**Komikara** adalah aplikasi baca komik berbasis Android yang menyediakan dua tampilan utama, yaitu untuk **user** dan **admin**. Aplikasi ini memanfaatkan API untuk proses autentikasi dan pengambilan data komik, serta Room Database untuk fitur penyimpanan lokal. Dengan antarmuka yang sederhana dan fitur yang lengkap, Komikara dirancang untuk memberikan pengalaman membaca komik yang nyaman dan praktis.

---

## Fitur Utama
### User
1. **Registrasi dan Login**
   - Proses registrasi menyimpan data pengguna ke API.
   - Login mencocokkan data yang dimasukkan dengan data yang tersimpan di API.

2. **Fitur Search**
   - Mencari nama komik berdasarkan inputan pengguna (masih dalam pengembangan).

3. **Fitur Download**
   - Mengunduh komik yang ditampilkan dalam **Detail Komik** (masih dalam pengembangan).

4. **Fitur Favorit**
   - Menyimpan komik dari API ke dalam Room Database.
   - Menampilkan daftar komik favorit di halaman **Rak Buku**.
   - Menghapus komik dari daftar favorit di tampilan **Detail Komik**.

5. **Fitur Baca Komik**
   - Membaca chapter setiap komik yang ada (masih dalam pengembangan).

5. **Tampilan Home**
   - Menampilkan beberapa menu pilihan (masih dalam pengembangan) daftar komik pupuler, dan paling baru yang diambil dari API menggunakan RecyclerView.

6. **Tampilan Terbaru**
   - Menampilkan daftar komik rilisan terbaru dan dapat dipilih berdasarkan tipe komiknya, semua, dan favorit. 

7. **Tampilan Rak Buku**
   - Menampilkan list histori komik yang dibaca (masih dalam pengembangan).
   - Menampilkan daftar komik yang difavoritkan.

8. **Tampilan Profil**
   - Menampilkan detail informasi user seperti username yang dimasukkan ketika register dan email yang terkait.

9. **Persistensi Login**
   - Menggunakan Shared Preferences untuk menyimpan status login.
   - Jika aplikasi ditutup tanpa logout, pengguna akan langsung masuk ke aplikasi tanpa harus login kembali.

### Admin
1. **CRUD Komik**
   - Menambahkan, membaca, mengubah, dan menghapus data komik di antarmuka admin yang terhubung langsung ke API.

---

## Teknologi yang Digunakan
- **Kotlin**: Bahasa pemrograman utama untuk pengembangan aplikasi Android.
- **RecyclerView**: Untuk menampilkan daftar komik di halaman home.
- **Room Database**: Untuk menyimpan data komik favorit secara lokal.
- **API**: Untuk proses autentikasi, registrasi, dan pengambilan data komik.
- **Shared Preferences**: Untuk menyimpan status login pengguna.

---

## Panduan Instalasi
1. Clone repository ini:
   ```bash
   git clone https://github.com/dzuura/komikara.git
   ```
2. Buka proyek di Android Studio.
3. Tambahkan file konfigurasi API (contoh: `config.properties`) dengan endpoint API yang sesuai.
4. Jalankan aplikasi di emulator atau perangkat fisik.

---

## Cara Penggunaan
### Untuk User
1. Registrasi menggunakan email dan password.
2. Login untuk mengakses halaman user.
3. Jelajahi daftar komik di halaman home.
4. Tambahkan komik ke daftar favorit dengan menekan tombol favorit di detail komik.
5. Lihat komik favorit di halaman Bookmark.
6. Hapus komik dari daftar favorit jika diinginkan.

### Untuk Admin
1. Login menggunakan akun admin yang tertera dalam file **LoginActivity**.
2. Kelola data komik (CRUD) melalui antarmuka admin.

---

## Kontributor
- Daffa Zulfahmi Al-Ahyar (Developer Utama)

---

## Lisensi
Proyek ini dilisensikan di bawah [MIT License](LICENSE).

