package test.alraedah.valditor.messaging;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BaseRabbitMQIntegrationTest {

  private static final String RABBIT_MQ_USERNAME = "guest";
  private static final String RABBIT_MQ_PASSWORD = "guest";

  @Value("${cyclic.array.queue}")
  protected String queueName;

  @Autowired
  protected RabbitAdmin rabbitAdmin;
  
  @Autowired
  protected RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;


  protected static RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer("rabbitmq:3.9.11-management-alpine")
          .withUser(RABBIT_MQ_USERNAME, RABBIT_MQ_PASSWORD).withReuse(true);



  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registery) {

    rabbitMQContainer.start();

    registery.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registery.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
    registery.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registery.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  @Test
  void testContainerStarted() {
    assertTrue(rabbitMQContainer.isRunning());
  }

  protected void waitForFiveSeconds() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
