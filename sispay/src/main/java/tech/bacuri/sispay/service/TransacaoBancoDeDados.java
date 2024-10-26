package tech.bacuri.sispay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class TransacaoBancoDeDados {

    @Transactional
    public <T> T executa(Supplier<T> supplier) {
        return supplier.get();
    }
}
