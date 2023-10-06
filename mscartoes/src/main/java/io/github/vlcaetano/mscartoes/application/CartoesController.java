package io.github.vlcaetano.mscartoes.application;

import io.github.vlcaetano.mscartoes.application.representation.CartaoSaveRequest;
import io.github.vlcaetano.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.vlcaetano.mscartoes.domain.Cartao;
import io.github.vlcaetano.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Void> cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam Long renda) {
        List<Cartao> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam String cpf) {
        List<ClienteCartao> list = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> response = list.stream().map(CartoesPorClienteResponse::fromModel).toList();
        return ResponseEntity.ok(response);
    }
}
