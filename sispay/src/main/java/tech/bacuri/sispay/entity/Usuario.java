package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import tech.bacuri.sispay.entity.converter.TipoFormaPagamentoConverter;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@SequenceGenerator(name = "usuarioSeq", sequenceName = "SQ_USUARIO")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioSeq")
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @Convert(converter = TipoFormaPagamentoConverter.class)
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    public Usuario(@NotBlank @Email String email,
                   @NotNull @NotEmpty FormaPagamento... formasPagamento) {
        this.email = email;
        validandoFormaPagamento(formasPagamento);
    }

    private void validandoFormaPagamento(@NotNull @NotEmpty FormaPagamento[] formasPagamento) {
        Set<FormaPagamento> duplicadas = Stream.of(formasPagamento)
                .filter(formaPagamento -> !this.formasPagamento.add(formaPagamento))
                .collect(Collectors.toSet());

        Assert.isTrue(duplicadas.isEmpty(), "forma de pagamento " + duplicadas + " duplicada");
    }
}
