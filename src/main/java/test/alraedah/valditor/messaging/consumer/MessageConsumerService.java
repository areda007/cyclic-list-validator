package test.alraedah.valditor.messaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import test.alraedah.valditor.messaging.consumer.exception.CustomFailureException;
import test.alraedah.valditor.messaging.dto.CyclicArrayDto;
import test.alraedah.valditor.service.CyclicListValidtorService;

@Component
public class MessageConsumerService {

  private CyclicListValidtorService cyclicListValidtorService;

  public MessageConsumerService(CyclicListValidtorService cyclicListValidtorService) {
    this.cyclicListValidtorService = cyclicListValidtorService;
  }

  @RabbitListener(queues = "${cyclic.array.queue}")
  public void recievedMessage(CyclicArrayDto cyclicArray) throws CustomFailureException {
    System.out.println("X---=======> Recieved Message From RabbitMQ: " + cyclicArray.toString());

    String arrayStatus =
        cyclicListValidtorService.isItCyclic(cyclicArray.getArray()) ? "CYCLIC" : "NOT-CYCLIC";
    System.out.println(String.format("X---=======> %s is %s", cyclicArray.getName(), arrayStatus));

    // testing dlq and retrial scenarios
    if (cyclicArray.getName().equalsIgnoreCase("retry")) {
      throw new CustomFailureException("Failed on purpose!!");
    }
  }
}
