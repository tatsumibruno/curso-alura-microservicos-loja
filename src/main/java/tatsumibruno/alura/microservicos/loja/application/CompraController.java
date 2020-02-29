
package tatsumibruno.alura.microservicos.loja.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tatsumibruno.alura.microservicos.loja.application.dto.CompraDTO;
import tatsumibruno.alura.microservicos.loja.domain.Compra;
import tatsumibruno.alura.microservicos.loja.domain.CompraService;

import java.util.Optional;

@RestController
@RequestMapping(path = "compra")
public class CompraController {

    @Autowired
    private CompraService service;

    @PostMapping
    public Compra realizaCompra(@RequestBody CompraDTO compra) {
        return service.realizaCompra(compra);
    }

    @GetMapping(path = "/{id}")
    public Optional<Compra> getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }
}
