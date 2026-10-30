
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
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

    private static final QName _GetData_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "getData");
    private static final QName _GetDataDouble_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "getDataDouble");
    private static final QName _GetDataDoubleResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "getDataDoubleResponse");
    private static final QName _GetDataResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "getDataResponse");
    private static final QName _SubmitDataInt_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataInt");
    private static final QName _SubmitDataIntArray_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataIntArray");
    private static final QName _SubmitDataIntArrayResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataIntArrayResponse");
    private static final QName _SubmitDataIntResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataIntResponse");
    private static final QName _SubmitDataString_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataString");
    private static final QName _SubmitDataStringArray_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataStringArray");
    private static final QName _SubmitDataStringArrayResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataStringArrayResponse");
    private static final QName _SubmitDataStringResponse_QNAME = new QName("http://soap.server.network.judge.dblab.ptit/", "submitDataStringResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetData }
     * 
     */
    public GetData createGetData() {
        return new GetData();
    }

    /**
     * Create an instance of {@link GetDataDouble }
     * 
     */
    public GetDataDouble createGetDataDouble() {
        return new GetDataDouble();
    }

    /**
     * Create an instance of {@link GetDataDoubleResponse }
     * 
     */
    public GetDataDoubleResponse createGetDataDoubleResponse() {
        return new GetDataDoubleResponse();
    }

    /**
     * Create an instance of {@link GetDataResponse }
     * 
     */
    public GetDataResponse createGetDataResponse() {
        return new GetDataResponse();
    }

    /**
     * Create an instance of {@link SubmitDataInt }
     * 
     */
    public SubmitDataInt createSubmitDataInt() {
        return new SubmitDataInt();
    }

    /**
     * Create an instance of {@link SubmitDataIntArray }
     * 
     */
    public SubmitDataIntArray createSubmitDataIntArray() {
        return new SubmitDataIntArray();
    }

    /**
     * Create an instance of {@link SubmitDataIntArrayResponse }
     * 
     */
    public SubmitDataIntArrayResponse createSubmitDataIntArrayResponse() {
        return new SubmitDataIntArrayResponse();
    }

    /**
     * Create an instance of {@link SubmitDataIntResponse }
     * 
     */
    public SubmitDataIntResponse createSubmitDataIntResponse() {
        return new SubmitDataIntResponse();
    }

    /**
     * Create an instance of {@link SubmitDataString }
     * 
     */
    public SubmitDataString createSubmitDataString() {
        return new SubmitDataString();
    }

    /**
     * Create an instance of {@link SubmitDataStringArray }
     * 
     */
    public SubmitDataStringArray createSubmitDataStringArray() {
        return new SubmitDataStringArray();
    }

    /**
     * Create an instance of {@link SubmitDataStringArrayResponse }
     * 
     */
    public SubmitDataStringArrayResponse createSubmitDataStringArrayResponse() {
        return new SubmitDataStringArrayResponse();
    }

    /**
     * Create an instance of {@link SubmitDataStringResponse }
     * 
     */
    public SubmitDataStringResponse createSubmitDataStringResponse() {
        return new SubmitDataStringResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetData }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "getData")
    public JAXBElement<GetData> createGetData(GetData value) {
        return new JAXBElement<GetData>(_GetData_QNAME, GetData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataDouble }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDataDouble }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "getDataDouble")
    public JAXBElement<GetDataDouble> createGetDataDouble(GetDataDouble value) {
        return new JAXBElement<GetDataDouble>(_GetDataDouble_QNAME, GetDataDouble.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataDoubleResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDataDoubleResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "getDataDoubleResponse")
    public JAXBElement<GetDataDoubleResponse> createGetDataDoubleResponse(GetDataDoubleResponse value) {
        return new JAXBElement<GetDataDoubleResponse>(_GetDataDoubleResponse_QNAME, GetDataDoubleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "getDataResponse")
    public JAXBElement<GetDataResponse> createGetDataResponse(GetDataResponse value) {
        return new JAXBElement<GetDataResponse>(_GetDataResponse_QNAME, GetDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataInt }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataInt }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataInt")
    public JAXBElement<SubmitDataInt> createSubmitDataInt(SubmitDataInt value) {
        return new JAXBElement<SubmitDataInt>(_SubmitDataInt_QNAME, SubmitDataInt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataIntArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataIntArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataIntArray")
    public JAXBElement<SubmitDataIntArray> createSubmitDataIntArray(SubmitDataIntArray value) {
        return new JAXBElement<SubmitDataIntArray>(_SubmitDataIntArray_QNAME, SubmitDataIntArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataIntArrayResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataIntArrayResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataIntArrayResponse")
    public JAXBElement<SubmitDataIntArrayResponse> createSubmitDataIntArrayResponse(SubmitDataIntArrayResponse value) {
        return new JAXBElement<SubmitDataIntArrayResponse>(_SubmitDataIntArrayResponse_QNAME, SubmitDataIntArrayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataIntResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataIntResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataIntResponse")
    public JAXBElement<SubmitDataIntResponse> createSubmitDataIntResponse(SubmitDataIntResponse value) {
        return new JAXBElement<SubmitDataIntResponse>(_SubmitDataIntResponse_QNAME, SubmitDataIntResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataString }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataString }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataString")
    public JAXBElement<SubmitDataString> createSubmitDataString(SubmitDataString value) {
        return new JAXBElement<SubmitDataString>(_SubmitDataString_QNAME, SubmitDataString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataStringArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataStringArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataStringArray")
    public JAXBElement<SubmitDataStringArray> createSubmitDataStringArray(SubmitDataStringArray value) {
        return new JAXBElement<SubmitDataStringArray>(_SubmitDataStringArray_QNAME, SubmitDataStringArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataStringArrayResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataStringArrayResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataStringArrayResponse")
    public JAXBElement<SubmitDataStringArrayResponse> createSubmitDataStringArrayResponse(SubmitDataStringArrayResponse value) {
        return new JAXBElement<SubmitDataStringArrayResponse>(_SubmitDataStringArrayResponse_QNAME, SubmitDataStringArrayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataStringResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubmitDataStringResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.server.network.judge.dblab.ptit/", name = "submitDataStringResponse")
    public JAXBElement<SubmitDataStringResponse> createSubmitDataStringResponse(SubmitDataStringResponse value) {
        return new JAXBElement<SubmitDataStringResponse>(_SubmitDataStringResponse_QNAME, SubmitDataStringResponse.class, null, value);
    }

}
