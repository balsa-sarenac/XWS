package xws.team16.adminservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xws.team16.adminservice.generated.PostMessageRequest;
import xws.team16.adminservice.generated.PostMessageResponse;
import xws.team16.adminservice.service.MessageService;

@Endpoint
public class MessageEndpoint {
    private static final String NAMESPACE_URI = "https://ftn.uns.ac.rs/messages";

    private MessageService messageService;

    @Autowired
    public MessageEndpoint(MessageService messageService) {
        this.messageService = messageService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PostMessageRequest")
    @ResponsePayload
    public PostMessageResponse newMessage(@RequestPayload PostMessageRequest request) {
        PostMessageResponse response = new PostMessageResponse();
        response.setIsSent(this.messageService.newMessage(request));
        return response;
    }
}
