//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 08:45:33 PM CEST 
//


package xws.team16.adminservice.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.ftn_uns_ac_rs.messages package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.ftn_uns_ac_rs.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PostMessageRequest }
     * 
     */
    public PostMessageRequest createPostMessageRequest() {
        return new PostMessageRequest();
    }

    /**
     * Create an instance of {@link TMessage }
     * 
     */
    public TMessage createTMessage() {
        return new TMessage();
    }

    /**
     * Create an instance of {@link PostMessageResponse }
     * 
     */
    public PostMessageResponse createPostMessageResponse() {
        return new PostMessageResponse();
    }

    /**
     * Create an instance of {@link GetMessagesRequest }
     * 
     */
    public GetMessagesRequest createGetMessagesRequest() {
        return new GetMessagesRequest();
    }

    /**
     * Create an instance of {@link GetMessagesResponse }
     * 
     */
    public GetMessagesResponse createGetMessagesResponse() {
        return new GetMessagesResponse();
    }

    /**
     * Create an instance of {@link TChat }
     * 
     */
    public TChat createTChat() {
        return new TChat();
    }

    /**
     * Create an instance of {@link TUser }
     * 
     */
    public TUser createTUser() {
        return new TUser();
    }

}
