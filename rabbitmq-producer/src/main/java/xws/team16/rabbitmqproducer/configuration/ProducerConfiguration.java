package xws.team16.rabbitmqproducer.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {

    @Value("mail-queue")
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
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("falysqic");
        connectionFactory.setPassword("WGXFaLcXLNsampQVJGT8FO-6nXQne-zh");
        connectionFactory.setVirtualHost("falysqic");
        connectionFactory.setHost("wasp.rmq.cloudamqp.com");
//        connectionFactory.setPort();
        return connectionFactory;
    }
}
