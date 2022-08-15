package test.alraedah.valditor.messaging.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.alraedah.valditor.messaging.dto.CyclicArrayDto;

/**
 * 
 * @author ahmedreda
 *
 *         A sample producer for sake of testing messaging.
 */
@RestController
public class MessageProducerController {

  @Autowired
  private AmqpTemplate amqpTemplate;

  @Value("${cyclic.array.queue}")
  private String queueName;

  @GetMapping(value = "/pushMessage")
  public String pushMessage(@RequestParam("listName") String listName,
      @RequestParam("array") int[] array) {

    CyclicArrayDto cyclicArray = new CyclicArrayDto(listName, array);

    amqpTemplate.convertAndSend(queueName, cyclicArray);

    return "Message sent to queue successfully!!";
  }

}
