package tatsumibruno.alura.microservicos.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tatsumibruno.alura.microservicos.loja.client.dto.InfoEntregaDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {
    @RequestMapping(path = "/entrega", method = RequestMethod.POST)
    VoucherDTO reservaEntrega(@RequestBody InfoEntregaDTO pedidoDTO);
}
