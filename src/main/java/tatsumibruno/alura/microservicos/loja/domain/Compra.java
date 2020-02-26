package tatsumibruno.alura.microservicos.loja.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    private Long pedidoId;
    private Long fornecedorId;
    private Long tempoPreparo;
    private String enderecoEntrega;
}
