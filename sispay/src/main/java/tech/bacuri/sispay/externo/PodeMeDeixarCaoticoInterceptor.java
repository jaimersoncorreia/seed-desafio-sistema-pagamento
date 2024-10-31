package tech.bacuri.sispay.externo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Random;

@RequiredArgsConstructor
public class PodeMeDeixarCaoticoInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(PodeMeDeixarCaoticoInterceptor.class);

    private final Environment env;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Boolean caosLiberado = env.getProperty("enderecos-externos.caos-liberado",
                    Boolean.class, false);
            log.info("Caos está liberado => {}", caosLiberado);

            if (caosLiberado && method.getBean() instanceof PodeMeDeixarCaotico) {
                talvezARequisicaoFiqueLenta();
                talvezSolteException();
            }
        }

        return true;
    }

    private Random random = new Random();

    private void talvezSolteException() {
        int numero = random.nextInt(10);
        if (numero % 3 == 0) {
            throw new RuntimeException("O caos está rolando...");
        }
    }

    private void talvezARequisicaoFiqueLenta() {
        int numero = random.nextInt(10);
        if (numero % 3 == 0) {
            try {
                log.info("Atrasando a request em {} segundos", numero);
                Thread.sleep(numero * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
