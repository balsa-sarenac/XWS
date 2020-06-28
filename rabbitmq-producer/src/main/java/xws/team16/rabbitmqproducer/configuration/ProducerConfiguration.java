package xws.team16.rabbitmqproducer.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"configuration","mailing"})
@Configuration
public class ProducerConfiguration {

    @Value("${myqueue}")
    String queue;

    @Bean
    Queue queue(){
        return new Queue(queue,false);
    }

    /*
     * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ
     * gde se mi u primeru kacimo u lokalu.
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }
}
