package io.github.vlcaetano.msavaliadorcredito.application;

import io.github.vlcaetano.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.vlcaetano.msavaliadorcredito.domain.model.DadosCliente;
import io.github.vlcaetano.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.vlcaetano.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.vlcaetano.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesClienteResponse = cartoesResourceClient.getCartoesByCliente(cpf);
        return SituacaoCliente.builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesClienteResponse.getBody())
                .build();
    }
}
