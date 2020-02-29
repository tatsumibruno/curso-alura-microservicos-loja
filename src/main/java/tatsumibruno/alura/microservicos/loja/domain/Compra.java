package tatsumibruno.alura.microservicos.loja.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@ToString
public class Compra {
    @Id
    @Setter
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long pedidoId;
    @Setter
    private Long fornecedorId;
    @Setter
    private LocalDate dataEntrega;
    @Setter
    private Long voucherId;
    @Setter
    private String enderecoEntrega;
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusCompra status = StatusCompra.CRIADA;
}
