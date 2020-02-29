package tatsumibruno.alura.microservicos.loja.domain;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tatsumibruno.alura.microservicos.loja.application.dto.CompraDTO;
import tatsumibruno.alura.microservicos.loja.client.FornecedorClient;
import tatsumibruno.alura.microservicos.loja.client.TransportadorClient;
import tatsumibruno.alura.microservicos.loja.client.dto.InfoEntregaDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.InfoFornecedorDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.PedidoInfoDTO;
import tatsumibruno.alura.microservicos.loja.client.dto.VoucherDTO;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CompraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompraService.class);

    @Autowired
    private FornecedorClient fornecedorClient;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private TransportadorClient transportadorClient;

    @HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPoolKey")
    public Compra realizaCompra(CompraDTO compra) {
        Compra compraCriada = new Compra();
        compraCriada.setEnderecoEntrega(compra.getEndereco().toString());
        compraCriada.setStatus(StatusCompra.CRIADA);
        LOGGER.info("Criando compra {}.", compraCriada);
        compraRepository.save(compraCriada);
        compra.setId(compraCriada.getId());

        String estado = compra.getEndereco().getEstado();
        LOGGER.info("Buscando fornecedor para o estado {}.", estado);
        InfoFornecedorDTO fornecedor = fornecedorClient.getPorEstado(estado);
        LOGGER.info("Criando pedido para o fornecedor {}.", fornecedor);
        PedidoInfoDTO pedidoCriado = fornecedorClient.realizaPedido(compra.getItens());
        compraCriada.setPedidoId(pedidoCriado.getId());
        compraCriada.setStatus(StatusCompra.PEDIDO_REALIZADO);
        compraRepository.save(compraCriada);

        InfoEntregaDTO entrega = new InfoEntregaDTO();
        entrega.setDataParaEntrega(LocalDate.now().plusDays(pedidoCriado.getTempoDePreparo()));
        entrega.setEnderecoDestino(compra.getEndereco().toString());
        entrega.setEnderecoOrigem(compra.getEndereco().toString());
        entrega.setPedidoId(pedidoCriado.getId());
        LOGGER.info("Gerando voucher para o pedido {}.", pedidoCriado);

        VoucherDTO voucherEntrega = transportadorClient.reservaEntrega(entrega);
        LOGGER.info("Voucher gerado com número {}.", voucherEntrega.getNumero());

        compraCriada.setFornecedorId(fornecedor.getId());
        compraCriada.setDataEntrega(entrega.getDataParaEntrega());
        compraCriada.setVoucherId(voucherEntrega.getNumero());
        compraCriada.setStatus(StatusCompra.ENTREGA_AGENDADA);

        LOGGER.info("Finalizando compra {}.", compraCriada);
        compraRepository.save(compraCriada);

        return compraCriada;
    }

    public Compra realizaCompraFallback(CompraDTO compra) {
        if (compra.getId() != null) {
            return compraRepository.findById(compra.getId()).orElse(new Compra());
        }
        Compra compraPendente = new Compra();
        compraPendente.setEnderecoEntrega(compra.getEndereco().toString());
        compraPendente.setStatus(StatusCompra.CRIADA);
        compraRepository.save(compraPendente);
        return compraPendente;
    }

    @HystrixCommand(threadPoolKey = "getByIdThreadPool")
    public Optional<Compra> getById(Long id) {
        return compraRepository.findById(id);
    }
}
