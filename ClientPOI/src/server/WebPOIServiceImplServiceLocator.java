/**
 * WebPOIServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

import main.Service;

public class WebPOIServiceImplServiceLocator extends org.apache.axis.client.Service implements server.WebPOIServiceImplService {

    public WebPOIServiceImplServiceLocator() {
    }


    public WebPOIServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebPOIServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebPOIServiceImplPort
    private java.lang.String WebPOIServiceImplPort_address = "http://"+ Service.ip+":9999/WebPOIService/";

    public java.lang.String getWebPOIServiceImplPortAddress() {
        return WebPOIServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebPOIServiceImplPortWSDDServiceName = "WebPOIServiceImplPort";

    public java.lang.String getWebPOIServiceImplPortWSDDServiceName() {
        return WebPOIServiceImplPortWSDDServiceName;
    }

    public void setWebPOIServiceImplPortWSDDServiceName(java.lang.String name) {
        WebPOIServiceImplPortWSDDServiceName = name;
    }

    public server.WebPOIService getWebPOIServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebPOIServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebPOIServiceImplPort(endpoint);
    }

    public server.WebPOIService getWebPOIServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            server.WebPOIServiceImplPortBindingStub _stub = new server.WebPOIServiceImplPortBindingStub(portAddress, this);
            _stub.setPortName(getWebPOIServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebPOIServiceImplPortEndpointAddress(java.lang.String address) {
        WebPOIServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (server.WebPOIService.class.isAssignableFrom(serviceEndpointInterface)) {
                server.WebPOIServiceImplPortBindingStub _stub = new server.WebPOIServiceImplPortBindingStub(new java.net.URL(WebPOIServiceImplPort_address), this);
                _stub.setPortName(getWebPOIServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WebPOIServiceImplPort".equals(inputPortName)) {
            return getWebPOIServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server/", "WebPOIServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server/", "WebPOIServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebPOIServiceImplPort".equals(portName)) {
            setWebPOIServiceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
