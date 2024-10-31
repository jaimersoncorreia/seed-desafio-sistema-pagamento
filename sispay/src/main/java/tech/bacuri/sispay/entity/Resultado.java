package tech.bacuri.sispay.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Objects;


/**
 * @param <E> tipo que representa o erro
 * @param <S> tipo que representa o sucesso
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Resultado<E extends Exception, S> {
    private E erro;
    private S sucesso;

    public boolean temErro() {
        return Objects.nonNull(erro);
    }

    public boolean naoTemErro() {
        return !temErro();
    }

    public String getStackTrace() {
        Assert.isNull(sucesso, "Não pode ter sucesso para ter erro");
        Assert.isTrue(temErro(), "Você só deveria buscar por quando tiver dado erro");
        return Arrays.toString(this.erro.getStackTrace());
    }

    public S get() {
        Assert.isTrue(!temErro(), "Não pode ter tido para ter tido sucesso");
        return sucesso;
    }

    public static <T> Resultado<Exception, T> sucesso(T objeto) {
        Resultado<Exception, T> resultado = new Resultado<Exception, T>();
        resultado.sucesso = objeto;
        return resultado;
    }

    public static <E extends Exception, T> Resultado<E, T> erro(E exception) {
        Resultado<E, T> resultado = new Resultado<E, T>();
        resultado.erro = exception;
        return resultado;
    }
}
