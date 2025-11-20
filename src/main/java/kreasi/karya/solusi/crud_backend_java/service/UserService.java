package kreasi.karya.solusi.crud_backend_java.service;

import kreasi.karya.solusi.crud_backend_java.entity.User;
import kreasi.karya.solusi.crud_backend_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service // Menandakan bahwa ini adalah Service Layer
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    // Spring akan meng-inject (dependency injection) Repository secara otomatis
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // BCryptPasswordEncoder yang kita sediakan di SecurityConfig

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================
    // Fungsi Otentikasi/Pendaftaran (Register)
    // =====================================
    @Transactional
    public User registerUser(User user) throws IllegalStateException {
        // 1. Cek duplikasi email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email sudah digunakan oleh pengguna lain");
        }

        // 2. Hash password
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        user.setIsActive(false); // Default: tidak aktif sampai diverifikasi/diaktifkan

        // 3. Simpan user
        return userRepository.save(user);
    }

    // =====================================
    // Fungsi Admin (Menerjemahkan CreateUserByAdmin)
    // =====================================
    @Transactional
    public User createUserByAdmin(User user) throws IllegalStateException {
        // Logika validasi role ('admin' atau 'user')
        if (!user.getRole().equals("admin") && !user.getRole().equals("user")) {
            throw new IllegalArgumentException("Role tidak valid. Harus 'user' atau 'admin'.");
        }

        // Cek duplikasi email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email sudah digunakan oleh pengguna lain");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        user.setIsActive(true); // Admin membuat user aktif secara default

        return userRepository.save(user);
    }

    // =====================================
    // Fungsi CRUD/Lainnya
    // =====================================

    public User readUserByID(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pengguna tidak ditemukan"));
    }

    public Page<User> findAllUsers(int page, int size, String search) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (search != null && !search.trim().isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCaseOrNameContainingIgnoreCase(search, search,
                    pageRequest);
        }

        return userRepository.findAll(pageRequest);
    }

    @Transactional
    public User updateUser(User userDetails) {
        User existingUser = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pengguna tidak ditemukan untuk diupdate"));

        if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
        }

        existingUser.setName(userDetails.getName());
        existingUser.setRole(userDetails.getRole());

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException(
                        "User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole().toUpperCase())
                .build();
    }

    // Activation
    public void activateUser(String email, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getIsActive()) {
            throw new IllegalStateException("User already active");
        }

        if (user.getActivationToken() == null || !user.getActivationToken().equals(token)) {
            throw new IllegalArgumentException("Invalid activation token");
        }

        user.setIsActive(true);
        user.setActivationToken(null);
        userRepository.save(user);
    }

    // Request Reset
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String token = java.util.UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(java.time.LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        // Log token for now
        System.out.println("Reset Token for " + email + ": " + token);
    }

    // Reset Password
    public void resetPassword(String email, String token, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getResetToken() == null || !user.getResetToken().equals(token)) {
            throw new IllegalArgumentException("Invalid reset token");
        }

        if (user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset token expired");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}