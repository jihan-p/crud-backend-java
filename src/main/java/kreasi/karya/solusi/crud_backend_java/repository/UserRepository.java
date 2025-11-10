package kreasi.karya.solusi.crud_backend_java.repository;

import kreasi.karya.solusi.crud_backend_java.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Menandakan bahwa ini adalah Spring Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Menerjemahkan FindByEmail(email string)
    // Spring Data JPA secara otomatis membuat query: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // Menerjemahkan FindByActivationToken(token string)
    Optional<User> findByActivationToken(String token);

    // Menerjemahkan FindByResetToken(token string)
    Optional<User> findByResetToken(String token);

    // Method khusus untuk pencarian yang digunakan di UserService
    // Query yang dihasilkan: SELECT * FROM users WHERE email LIKE '%search%' OR name LIKE '%search%'
    Page<User> findByEmailContainingIgnoreCaseOrNameContainingIgnoreCase(String emailSearch, String nameSearch, Pageable pageable);

}