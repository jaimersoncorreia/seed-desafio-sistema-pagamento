package tech.bacuri.sispay.service;

/**
 * Apenas uma classe para explicitar o fato que alguém foi persistido
 * num evento de falha da prática de mexer no sistema alheio fora
 * do ponto de entrada
 */

public class Persisted<T> {
    private final T object;

    public Persisted(T object) {
        super();
        this.object = object;
    }

    public T get() {
        return object;
    }
}
