//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 01:33:30 PM CEST 
//


package xws.tim16.rentacar.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isSent" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isSent"
})
@XmlRootElement(name = "PostMessageResponse", namespace = "https://ftn.uns.ac.rs/messages")
public class PostMessageResponse {

    @XmlElement(namespace = "https://ftn.uns.ac.rs/messages")
    protected long isSent;

    /**
     * Gets the value of the isSent property.
     * 
     */
    public long getIsSent() {
        return isSent;
    }

    /**
     * Sets the value of the isSent property.
     * 
     */
    public void setIsSent(long value) {
        this.isSent = value;
    }

}
