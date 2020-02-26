package tatsumibruno.alura.microservicos.loja.application.dto;

import lombok.Data;
import tatsumibruno.alura.microservicos.loja.domain.vo.Endereco;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompraDTO {
    private String id;
    private List<CompraItemDTO> itens = new ArrayList<>();
    private Endereco endereco;
}
