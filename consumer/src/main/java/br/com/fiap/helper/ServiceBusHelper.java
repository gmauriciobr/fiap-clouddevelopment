package br.com.fiap.helper;

import static br.com.fiap.model.Mensagem.MENSAGENS;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceBusHelper {

  private ServiceBusHelper() {
  }

  private static class LoadListenerHelper {
    static final ServiceBusHelper INSTANCE = new ServiceBusHelper();
  }

  public static ServiceBusHelper getInstance() {
    return LoadListenerHelper.INSTANCE;
  }

  public void receiveMessages(final String connectionString, final String queueName) throws InterruptedException {
    CountDownLatch countdownLatch = new CountDownLatch(1);
    // Create an instance of the processor through the ServiceBusClientBuilder
    ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
      .connectionString(connectionString)
      .processor()
      .queueName(queueName)
      .processMessage(ServiceBusHelper::processMessage)
      .processError(context -> processError(context, countdownLatch))
      .buildProcessorClient();

    log.info("Starting the processor");
    processorClient.start();
    TimeUnit.SECONDS.sleep(10);
    log.info("Stopping and closing the processor");
    processorClient.close();
  }

  private static void processMessage(ServiceBusReceivedMessageContext context) {
    ServiceBusReceivedMessage message = context.getMessage();
    log.info("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
      message.getSequenceNumber(), message.getBody());
    MENSAGENS.put(UUID.randomUUID().toString(), message.getBody().toString());
  }

  private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
    log.info("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
      context.getFullyQualifiedNamespace(), context.getEntityPath());

    if (!(context.getException() instanceof ServiceBusException)) {
      log.info("Non-ServiceBusException occurred: %s%n", context.getException());
      return;
    }

    ServiceBusException exception = (ServiceBusException) context.getException();
    ServiceBusFailureReason reason = exception.getReason();

    if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
      || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
      || reason == ServiceBusFailureReason.UNAUTHORIZED) {
      log.info("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
        reason, exception.getMessage());

      countdownLatch.countDown();
    } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
      log.info("Message lock lost for message: %s%n", context.getException());
    } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
      try {
        // Choosing an arbitrary amount of time to wait until trying again.
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        log.error("Unable to sleep for period of time");
      }
    } else {
      log.info("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
        reason, context.getException());
    }
  }

}
