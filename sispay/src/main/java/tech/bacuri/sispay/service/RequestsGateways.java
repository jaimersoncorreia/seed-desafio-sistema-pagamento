package tech.bacuri.sispay.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tech.bacuri.sispay.externo.DadosCartaoSeyaRequest;
import tech.bacuri.sispay.externo.DadosCompraGenerico;
import tech.bacuri.sispay.externo.DadosCompraSeyaRequest;

//@FeignClient(url = "${enderecos-externos.gateways.base-url}", name = "gateways")
@FeignClient(url = "http://localhost:8080/sispay/api", name = "gateways")
public interface RequestsGateways {
    @PostMapping("/seya/verifica")
    int seyaVerifica(DadosCartaoSeyaRequest request);

    @PostMapping("/seya/processa/{codigo}")
    void seyaProcessa(@PathVariable("codigo") Integer codigo, DadosCompraSeyaRequest request);

    @PostMapping("/saori/processa")
    void saoriProcessa(DadosCompraGenerico request);

    @PostMapping("/tango/processa")
    void tangoProcessa(DadosCompraGenerico request);
}
