package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.util.Assert;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.externo.DadosCartao;

import java.math.BigDecimal;
import java.util.*;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@Entity
@SequenceGenerator(name = "pagamentoSeq", sequenceName = "SQ_PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "pagamentoSeq", strategy = GenerationType.SEQUENCE)
    private Long idPagamento;

    private Long idPedido;

    private BigDecimal valor;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Restaurante restaurante;

    @ElementCollection
    private Set<Transacao> transacoes = new HashSet<>();

    @NotBlank
    private String codigo;

    @NotNull
    private FormaPagamento formaPagamento;

    private String infoAdicional;

    private Pagamento(Long idPedido, BigDecimal valor, FormaPagamento formaPagamento, Usuario usuario, Restaurante restaurante, StatusTransacao statusInicial) {
        this.idPedido = idPedido;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.usuario = usuario;
        this.restaurante = restaurante;
        this.transacoes.add(new Transacao(statusInicial));
        this.codigo = UUID.randomUUID().toString();
    }

    public static Pagamento cartao(Long idPedido,
                                   BigDecimal valor,
                                   FormaPagamento formaPagamento,
                                   @CreditCardNumber @NotBlank String numeroCartao,
                                   int codigoSeguranca,
                                   Usuario comprador,
                                   Restaurante restaurante,
                                   StatusTransacao statusTransacao) {

        Assert.isTrue(formaPagamento.isOnline(), "Forma de pagamento aqui precisa ser online");
        Pagamento pagamento = new Pagamento(idPedido, valor, formaPagamento, comprador, restaurante, statusTransacao);
        pagamento.infoAdicional = FacilitadorJackson.serializa(Map.of("numero", numeroCartao, "codigoSeguranca", codigoSeguranca));
        return pagamento;
    }

    public static Pagamento offline(Long idPedido,
                                    BigDecimal valor,
                                    FormaPagamento formaPagamento,
                                    Usuario comprador,
                                    Restaurante restaurante,
                                    StatusTransacao statusTransacao) {

        Assert.isTrue(!formaPagamento.isOnline(), "Forma de pagamento aqui precisa ser offline");
        return new Pagamento(idPedido, valor, formaPagamento, comprador, restaurante, statusTransacao);
    }

    public void conclui() {
        Assert.state(!foiConcluido(), "Você não pode concluir uma compra que já foi concluída");
        this.transacoes.add(new Transacao(StatusTransacao.CONCLUIDA));
    }

    public boolean foiConcluido() {
        return this.transacoes.stream().anyMatch(Transacao::foiConcluido);
    }

    public void adicionaTransacao(List<Transacao> transacoesGeradas) {
        Assert.state(transacoes.stream().noneMatch(Transacao::foiConcluido),
                "Não pode adicionar transacao quando já tem uma marcando que concluiu");

        Assert.state(this.transacoes.addAll(transacoesGeradas),
                "A transação sendo adicionada já existe no pagamento => " + transacoesGeradas);
    }

    public DadosCartao getDadosCartao() {
        Assert.isTrue(formaPagamento.isOnline(), "Não tem dado de cartão para forma de pagamento que não é online");
        Assert.hasText(infoAdicional, "Você deveria ter adicionado informacao adicional relativa ao cartao");
        return FacilitadorJackson.desserializa(infoAdicional, DadosCartao.class);
    }
}
