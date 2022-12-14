package test.alraedah.valditor.messaging.consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import test.alraedah.valditor.messaging.BaseRabbitMQIntegrationTest;
import test.alraedah.valditor.messaging.consumer.exception.CustomFailureException;
import test.alraedah.valditor.messaging.dto.CyclicArrayDto;
import test.alraedah.valditor.service.CyclicListValidtorService;

class TestMessageConsumerService extends BaseRabbitMQIntegrationTest {

  @InjectMocks
  private MessageConsumerService messageConsumerService;

  @MockBean
  private CyclicListValidtorService cyclicListValidatorService;

  @Autowired
  private AmqpTemplate amqpTemplate;


  @BeforeEach
  void setUp() {
    when(cyclicListValidatorService.isItCyclic(any(int[].class))).thenReturn(false);
    rabbitListenerEndpointRegistry.start();
    rabbitAdmin.purgeQueue(queueName, false);
  }

  @AfterEach
  void tearDown() {
    rabbitListenerEndpointRegistry.stop();
    rabbitAdmin.purgeQueue(queueName, false);
  }

  @Disabled("Disabled for issue with MockBean on Servers.")
  @Test
  void testRecivingMessage() throws CustomFailureException {
    CyclicArrayDto cyclicArray = new CyclicArrayDto("list11", new int[] {3, 0, 1, 2});
    amqpTemplate.convertAndSend(queueName, cyclicArray);

    waitForFiveSeconds();

    verify(cyclicListValidatorService, times(1)).isItCyclic(any(int[].class));
  }

  @Test
  void testThrowingCustomException() {
    CyclicArrayDto cyclicArray = new CyclicArrayDto("retry", new int[] {3, 0, 1, 2});
    assertThrows(CustomFailureException.class,
        () -> messageConsumerService.recievedMessage(cyclicArray));
  }

  @Test
  void testRecievedMessage() throws CustomFailureException {
    CyclicArrayDto cyclicArray = new CyclicArrayDto("list1", new int[] {3, 0, 1, 2});
    messageConsumerService.recievedMessage(cyclicArray);
    verify(cyclicListValidatorService, times(1)).isItCyclic(any(int[].class));
  }
}
