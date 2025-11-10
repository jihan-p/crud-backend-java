package kreasi.karya.solusi.crud_backend_java.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "products") // Nama tabel di DB
public class Product {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // uint di Go dipetakan ke Long di Java

    @Column(nullable = false)
    private String name; // not null

    @Lob // Untuk field yang panjang (TEXT di SQL)
    private String description;

    @Column(nullable = false)
    private Integer price; // int di Go dipetakan ke Integer di Java

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // time.Time di Go

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // time.Time di Go

    // Catatan: Tidak perlu field deleted_at (untuk soft delete) kecuali Anda menggunakan fitur soft delete JPA.

    // TODO: Tambahkan konstruktor tanpa/dengan argumen dan semua Getters & Setters
}