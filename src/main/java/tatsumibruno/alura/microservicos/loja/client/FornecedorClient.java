package tatsumibruno.alura.microservicos.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tatsumibruno.alura.microservicos.loja.application.dto.CompraItemDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.InfoFornecedorDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.PedidoInfoDTO;

import java.util.List;

@FeignClient("fornecedor")
public interface FornecedorClient {

    @RequestMapping(path = "/info/{estado}")
    InfoFornecedorDTO getPorEstado(@PathVariable String estado);

    @RequestMapping(method = RequestMethod.POST, path = "/pedido")
    PedidoInfoDTO realizaPedido(List<CompraItemDTO> items);
}
