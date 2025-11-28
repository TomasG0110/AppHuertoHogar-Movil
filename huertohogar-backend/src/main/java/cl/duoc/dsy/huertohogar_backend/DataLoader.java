package cl.duoc.dsy.huertohogar_backend;

import cl.duoc.dsy.huertohogar_backend.Producto;
import cl.duoc.dsy.huertohogar_backend.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            repository.deleteAll();

             //FRUTAS

            repository.save(new Producto("Manzanas Fuji", "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule", 1200, "manzanas_fuji"));
            repository.save(new Producto("Naranjas Valencia", "Jugosas y ricas en vitamina C, ideales para jugo", 1000, "naranjas_valencia"));
            repository.save(new Producto("Plátanos Cavendish", "Plátanos maduros y dulces, ricos en potasio.", 800, "platanos_cavendish"));

            //VERDURAS
            repository.save(new Producto("Zanahorias", "Zanahorias crujientes cultivadas sin pesticidas", 900, "zanahorias_organicas"));
            repository.save(new Producto("Espinacas Frescas", "Espinacas frescas y nutritivas, perfectas para ensaladas.", 700, "espinacas_frescas"));
            repository.save(new Producto("Pimientos Tricolores", "Pimientos rojos, amarillos y verdes, ricos en antioxidantes.", 1500, "pimientos_tricolor"));

            //ORGANICOS
            repository.save(new Producto("Miel Orgánica", "Miel pura y orgánica producida por apicultores locales.", 5000, "miel_organica"));

            System.out.println("--- CATÁLOGO HUERTO HOGAR CARGADO ---");
        };
    }
}
