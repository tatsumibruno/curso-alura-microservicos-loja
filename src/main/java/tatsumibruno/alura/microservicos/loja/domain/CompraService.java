package tatsumibruno.alura.microservicos.loja.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tatsumibruno.alura.microservicos.loja.application.dto.CompraDTO;
import tatsumibruno.alura.microservicos.loja.client.FornecedorClient;
import tatsumibruno.alura.microservicos.loja.client.dto.InfoFornecedorDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.PedidoInfoDTO;

@Service
public class CompraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompraService.class);

    @Autowired
    private FornecedorClient fornecedorClient;

    public Compra realizaCompra(CompraDTO compra) {
        String estado = compra.getEndereco().getEstado();
        LOGGER.info("Buscando fornecedor para o estado {}.", estado);
        InfoFornecedorDTO fornecedor = fornecedorClient.getPorEstado(estado);
        LOGGER.info("Criando pedido para o fornecedor {}.", fornecedor);
        PedidoInfoDTO pedidoCriado = fornecedorClient.realizaPedido(compra.getItens());
        Compra compraCriada = new Compra(pedidoCriado.getId(),
                fornecedor.getId(),
                pedidoCriado.getTempoDePreparo(),
                compra.getEndereco().toString());
        return compraCriada;
    }
}
