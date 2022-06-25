package br.com.fiap.controller;

import br.com.fiap.dto.MensagemDTO;
import br.com.fiap.service.MensageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mensagem")
@Tag(name = "mensagem", description = "Serviço para administração de mensagem")
public class MensagemController {

  private final MensageService mensageService;

  @PostMapping
  @Operation(tags = {"mensagem"}, summary = "Enviar mensagem")
  private ResponseEntity<Void> enviarMensagem(@RequestBody MensagemDTO dto) {
    log.info("Inicio enviar mensagem....[{}]", dto);
    mensageService.enviarMensagem(dto.getMsg());
    return ResponseEntity.ok().build();
  }

}
