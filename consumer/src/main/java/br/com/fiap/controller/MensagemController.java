package br.com.fiap.controller;

import br.com.fiap.service.MensagemService;
import br.com.fiap.viewmodel.MensagemViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagem")
@RequiredArgsConstructor
@Tag(name = "mensagem", description = "Serviço para administração de mensagem")
public class MensagemController {

  private final MensagemService mensagemService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(tags = {"mensagem"}, summary = "Listar mensagens recebidas")
  public ResponseEntity<List<MensagemViewModel>> listaMensagens() {
    var response = mensagemService.listaMensagemRecebidas();
    return ResponseEntity.ok(MensagemViewModel.parse(response));
  }

  @DeleteMapping
  @Operation(tags = {"mensagem"}, summary = "Deletar todas mensagens recebidas")
  public ResponseEntity<Void> deletaTodos() {
    mensagemService.deletaTodos();
    return ResponseEntity.ok().build();
  }

}
