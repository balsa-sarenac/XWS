//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 01:09:16 PM CEST 
//


package xws.team16.carservice.generated.car;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.ftn_uns_ac_rs.car package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.ftn_uns_ac_rs.car
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStatisticsRequest }
     * 
     */
    public GetStatisticsRequest createGetStatisticsRequest() {
        return new GetStatisticsRequest();
    }

    /**
     * Create an instance of {@link GetStatisticsResponse }
     * 
     */
    public GetStatisticsResponse createGetStatisticsResponse() {
        return new GetStatisticsResponse();
    }

    /**
     * Create an instance of {@link TCarStatistics }
     * 
     */
    public TCarStatistics createTCarStatistics() {
        return new TCarStatistics();
    }

    /**
     * Create an instance of {@link PostReportRequest }
     * 
     */
    public PostReportRequest createPostReportRequest() {
        return new PostReportRequest();
    }

    /**
     * Create an instance of {@link TReport }
     * 
     */
    public TReport createTReport() {
        return new TReport();
    }

    /**
     * Create an instance of {@link PostReportResponse }
     * 
     */
    public PostReportResponse createPostReportResponse() {
        return new PostReportResponse();
    }

    /**
     * Create an instance of {@link TComment }
     * 
     */
    public TComment createTComment() {
        return new TComment();
    }

    /**
     * Create an instance of {@link TGrade }
     * 
     */
    public TGrade createTGrade() {
        return new TGrade();
    }

}