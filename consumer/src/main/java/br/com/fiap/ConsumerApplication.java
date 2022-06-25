package br.com.fiap;

import br.com.fiap.helper.ServiceBusHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ConsumerApplication {

	@Value("${fiap.teste_queue}")
  private String testeQueue;

	private static String TESTE_QUEUE_NAME;

	@Value("${fiap.teste_queue}")
	public void setTesteQueueName(String queueName) {
		ConsumerApplication.TESTE_QUEUE_NAME = queueName;
	}

	@Value("${fiap.connection_string}")
	private String connectionString;

	private static String CONNECTION_STRING;

	@Value("${fiap.connection_string}")
	public void setConnectionString(String connectionString) {
		ConsumerApplication.CONNECTION_STRING = connectionString;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
		processaMensagens();
	}

	private static void processaMensagens() {
		while (true) {
			try {
				ServiceBusHelper.getInstance().receiveMessages(CONNECTION_STRING, TESTE_QUEUE_NAME);
			} catch (Exception e) {
				log.error("Erro: {}", e);
			}
		}
	}

}
