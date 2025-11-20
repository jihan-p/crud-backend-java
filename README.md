# CRUD Backend Java

Aplikasi backend Spring Boot untuk manajemen User dan Product dengan autentikasi JWT.

## Prasyarat

- **Java JDK 25** (sesuai konfigurasi `build.gradle`)
- **MySQL Database**

## Konfigurasi

1.  Buka file `src/main/resources/application.properties`.
2.  Sesuaikan konfigurasi database dengan environment Anda:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/nama_database_anda?useSSL=false&serverTimezone=UTC
    spring.datasource.username=DB_USER_ANDA
    spring.datasource.password=DB_PASSWORD_ANDA
    ```
3.  Pastikan database `nama_database_anda` sudah dibuat di MySQL.

## Cara Menjalankan (Run)

1.  **Build Project:**
    ```bash
    ./gradlew build
    ```
2.  **Jalankan Aplikasi:**
    ```bash
    ./gradlew bootRun
    ```
    Aplikasi akan berjalan di `http://localhost:8080`.

## Cara Testing

### 1. Menggunakan Unit Test

Jalankan perintah berikut untuk menjalankan unit test yang tersedia:

```bash
./gradlew test
```

### 2. Menggunakan Script Test Manual (cURL)

Telah disediakan script `test-endpoints.sh` untuk menguji endpoint utama.

1.  Berikan izin eksekusi pada script:
    ```bash
    chmod +x test-endpoints.sh
    ```
2.  Jalankan script:
    ```bash
    ./test-endpoints.sh
    ```

### 3. Endpoint API

Berikut adalah daftar endpoint yang tersedia:

**Auth:**

- `POST /api/auth/register` - Registrasi user baru
- `POST /api/auth/login` - Login dan dapatkan JWT
- `GET /api/auth/activate?email=...&token=...` - Aktivasi user
- `POST /api/auth/forgot-password` - Request reset password
- `POST /api/auth/reset-password` - Reset password

**User:**

- `GET /api/users/me` - Lihat profil sendiri (Butuh Token)

**Product:**

- `GET /api/products` - List produk
- `POST /api/products` - Tambah produk (Butuh Token)
- `GET /api/products/{id}` - Detail produk
- `PUT /api/products/{id}` - Update produk (Butuh Token)
- `DELETE /api/products/{id}` - Hapus produk (Butuh Token)
