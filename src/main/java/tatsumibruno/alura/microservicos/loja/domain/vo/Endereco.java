package tatsumibruno.alura.microservicos.loja.domain.vo;

import lombok.Getter;

@Getter
public class Endereco {
    private final String estado;
    private final String cidade;
    private final String bairro;
    private final String rua;
    private final int numero;

    public Endereco(String estado, String cidade, String bairro, String rua, int numero) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", cidade, bairro, rua, numero);
    }
}
