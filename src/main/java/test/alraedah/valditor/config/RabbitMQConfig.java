package test.alraedah.valditor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "rabbitmq.enable", havingValue = "true", matchIfMissing = true)
public class RabbitMQConfig {

  @Bean
  DirectExchange deadLetterExchange() {
    return new DirectExchange(DEAD_LETTER_EXCHANGE);
  }

  @Bean
  Queue dlq() {
    return QueueBuilder.nonDurable("deadLetter.queue").build();
  }

  @Bean
  Binding dlqBinding() {
    return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(DEAD_LETTER_BINDING);
  }

  @Bean
  Queue queue() {
    return QueueBuilder.nonDurable(queueName)
        .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", DEAD_LETTER_BINDING)
        .withArgument("x-message-ttl", TTL_PER_MESSAGE).build();
  }

  // 1 minute
  private static final int TTL_PER_MESSAGE = 60000;

  private static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";

  private static final String DEAD_LETTER_BINDING = "deadLetter";

  @Value("${cyclic.array.queue}")
  private String queueName;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;
}
