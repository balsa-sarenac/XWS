package xws.team16.carservice.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ad/*");
    }

    @Bean(name = "ad")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema adSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AdPort");
        wsdl11Definition.setLocationUri("/ad");
        wsdl11Definition.setTargetNamespace("https://ftn.uns.ac.rs/ad");
        wsdl11Definition.setSchema(adSchema);
        return wsdl11Definition;
    }

    @Bean(name = "car")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCar(XsdSchema carSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CarPort");
        wsdl11Definition.setLocationUri("/car");
        wsdl11Definition.setTargetNamespace("https://ftn.uns.ac.rs/car");
        wsdl11Definition.setSchema(carSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema adSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Ad.xsd"));
    }

    @Bean
    public XsdSchema carSchema() {
        return new SimpleXsdSchema(new ClassPathResource("CarStatistics.xsd"));
    }
}
