package br.com.fiap.service;

import static br.com.fiap.model.Mensagem.MENSAGENS;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MensagemService {

  public Map<String, String> listaMensagemRecebidas() {
    return MENSAGENS;
  }

  public void deletaTodos() {
    MENSAGENS.clear();
  }

}
