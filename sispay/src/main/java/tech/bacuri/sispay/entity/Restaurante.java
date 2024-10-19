package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@Entity
@SequenceGenerator(name = "restauranteSeq", sequenceName = "SQ_RESTAURANTE")
public class Restaurante {
    @Id
    @GeneratedValue(generator = "restauranteSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String nome;

    @Size(min = 1)
    @ElementCollection
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    public Restaurante(String nome, FormaPagamento... formaPagamento) {
        this.nome = nome;
        validando(formaPagamento);
    }

    private void validando(FormaPagamento[] formaPagamento) {
        Set<FormaPagamento> duplicadas = Stream.of(formaPagamento)
                .filter(forma -> !this.formasPagamento.add(forma))
                .collect(Collectors.toSet());

        Assert.isTrue(duplicadas.isEmpty(), "forma de pagamento " + duplicadas + " duplicada");
    }
}
