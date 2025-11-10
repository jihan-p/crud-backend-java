package kreasi.karya.solusi.crud_backend_java.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity // Menandakan bahwa ini adalah Entitas JPA yang dipetakan ke tabel DB
@Table(name = "users") // Nama tabel di DB
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id; // Menggunakan Long (setara dengan BIGINT di SQL)

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) // unique:true, not null di GORM/SQL
    private String email;

    @Column(name = "password_hash", nullable = false) // Menerjemahkan PasswordHash
    private String passwordHash;

    @Column(nullable = false, length = 20) // varchar(20)
    private String role = "user"; // default:'user'

    @Column(name = "is_active", columnDefinition = "boolean default false") // is_active boolean default false
    private Boolean isActive = false;

    // Token dan Expired Time yang bisa NULL (Pointer di Go)
    @Column(name = "activation_token")
    private String activationToken;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @CreationTimestamp // Diurus otomatis oleh JPA (menggantikan autoCreateTime di GORM)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Diurus otomatis oleh JPA (menggantikan autoUpdateTime di GORM)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // TODO: Tambahkan konstruktor tanpa/dengan argumen dan semua Getters & Setters
    // (Bisa dibuat otomatis oleh IDE atau menggunakan library Lombok)
}