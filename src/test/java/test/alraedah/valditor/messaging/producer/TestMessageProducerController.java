package test.alraedah.valditor.messaging.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import test.alraedah.valditor.messaging.BaseRabbitMQIntegrationTest;

@TestPropertySource(properties = "spring.rabbitmq.listener.simple.auto-startup=false")
class TestMessageProducerController extends BaseRabbitMQIntegrationTest {

  @BeforeEach
  void setUp() {
    rabbitListenerEndpointRegistry.stop();
    rabbitAdmin.purgeQueue(queueName, false);
  }

  @AfterEach
  void tearDown() {
    rabbitListenerEndpointRegistry.start();
    rabbitAdmin.purgeQueue(queueName, false);
  }

  @Test
  void testPushMessage() throws Exception {
    messageProducerController.pushMessage("list1", new int[] {3, 0, 1, 2});

    waitForFiveSeconds();

    assertEquals(1, rabbitAdmin.getQueueInfo(queueName).getMessageCount());
  }

  @Autowired
  private MessageProducerController messageProducerController;
}
