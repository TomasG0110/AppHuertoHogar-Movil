package cl.duoc.dsy.huertohogar_backend;

import cl.duoc.dsy.huertohogar_backend.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
