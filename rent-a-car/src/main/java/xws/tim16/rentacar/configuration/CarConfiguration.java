package xws.tim16.rentacar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import xws.tim16.rentacar.client.AdminClient;
import xws.tim16.rentacar.client.CarClient;

@Configuration
public class CarConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("xws.tim16.rentacar.generated");
        return marshaller;
    }

    @Bean
    public CarClient carClient(Jaxb2Marshaller marshaller) {
        CarClient carClient = new CarClient();
        carClient.setDefaultUri("http://localhost:8083/ad-soap");
        carClient.setMarshaller(marshaller);
        carClient.setUnmarshaller(marshaller);
        return carClient;
    }

    @Bean
    public AdminClient adminClient(Jaxb2Marshaller marshaller) {
        AdminClient adminClient = new AdminClient();
        adminClient.setDefaultUri("http://localhost:8082/messages");
        adminClient.setMarshaller(marshaller);
        adminClient.setUnmarshaller(marshaller);
        return adminClient;
    }
}
