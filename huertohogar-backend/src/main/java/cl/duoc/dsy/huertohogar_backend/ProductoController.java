package cl.duoc.dsy.huertohogar_backend;

import cl.duoc.dsy.huertohogar_backend.Producto;
import cl.duoc.dsy.huertohogar_backend.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository repository;

    //Obtener todos los productos (GET)
    @GetMapping
    public List<Producto> getAllProductos() {
        return repository.findAll();
    }

    //Crear un producto (POST)
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        producto.setId(null);
        return repository.save(producto);
    }

    //Actualizar un producto (PUT)
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto detalles) {
        Producto prod = repository.findById(id).orElseThrow();
        prod.setNombre(detalles.getNombre());
        prod.setPrecio(detalles.getPrecio());
        prod.setDescripcion(detalles.getDescripcion());
        return repository.save(prod);
    }

    //Eliminar un producto (DELETE)
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
