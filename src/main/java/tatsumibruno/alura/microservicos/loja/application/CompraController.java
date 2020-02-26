
package tatsumibruno.alura.microservicos.loja.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tatsumibruno.alura.microservicos.loja.application.dto.CompraDTO;
import tatsumibruno.alura.microservicos.loja.domain.Compra;
import tatsumibruno.alura.microservicos.loja.domain.CompraService;

@RestController
@RequestMapping(path = "compra")
public class CompraController {

    @Autowired
    private CompraService service;

    @PostMapping
    public Compra realizaCompra(@RequestBody CompraDTO compra) {
        return service.realizaCompra(compra);
    }
}
