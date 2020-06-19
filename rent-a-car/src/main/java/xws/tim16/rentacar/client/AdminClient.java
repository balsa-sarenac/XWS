package xws.tim16.rentacar.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import xws.tim16.rentacar.wsdl.PostMessageRequest;
import xws.tim16.rentacar.wsdl.PostMessageResponse;
import xws.tim16.rentacar.wsdl.TMessage;

@Slf4j
public class AdminClient extends WebServiceGatewaySupport {

    public PostMessageResponse postMessage(TMessage tMessage) {
        PostMessageRequest request = new PostMessageRequest();
        request.setMessage(tMessage);

        log.info("Requesting creating new message " + tMessage.toString());

        PostMessageResponse response = (PostMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8082/messages", request,
                        new SoapActionCallback("https://ftn.uns.ac.rs/messaging/PostMessageRequest"));
        return response;
    }
}
