package tatsumibruno.alura.microservicos.loja.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompraItemDTO {
    private Long id;
    private BigDecimal quantidade;
}
