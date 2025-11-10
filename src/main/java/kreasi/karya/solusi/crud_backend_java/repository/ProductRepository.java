package kreasi.karya.solusi.crud_backend_java.repository;

import kreasi.karya.solusi.crud_backend_java.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Catatan: Semua CRUD dasar (Create, ReadAll, ReadByID, Update, Delete) sudah tersedia otomatis.

}