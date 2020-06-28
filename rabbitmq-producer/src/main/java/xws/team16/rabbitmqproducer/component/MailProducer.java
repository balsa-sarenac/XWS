package xws.team16.rabbitmqproducer.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {

    private static final Logger log = LoggerFactory.getLogger(MailProducer.class);

    /*
     * RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
     * pristup RabbitMQ za slanje i primanje poruka.
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * U ovom slucaju routingKey ce biti ime queue.
     * Poruka se salje u exchange (default exchange u ovom primeru) i
     * exchange ce rutirati poruke u pravi queue.
     */
    public void sendTo(String routingkey, String message){
        log.info("Sending> ...");
        this.rabbitTemplate.convertAndSend(routingkey,message);
    }
}
