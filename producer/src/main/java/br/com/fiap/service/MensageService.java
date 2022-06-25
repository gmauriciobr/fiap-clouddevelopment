package br.com.fiap.service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MensageService {

  @Value("${fiap.connection_string}")
  private String CONNECTION_STRING;

  @Value("${fiap.teste_queue}")
  private String TESTE_QUEUE;

  public void enviarMensagem(String msg) {
    log.info("Enviando mensagem.....[{}]", msg);
    sendMessage(msg);
  }

  private void sendMessage(String msg) {
    ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
      .connectionString(CONNECTION_STRING)
      .sender()
      .queueName(TESTE_QUEUE)
      .buildClient();

    senderClient.sendMessage(new ServiceBusMessage(msg));
    log.info("Sent a single message to the queue: " + TESTE_QUEUE);
  }

}
