package server;

public class WebPOIServiceProxy implements server.WebPOIService {
  private String _endpoint = null;
  private server.WebPOIService webPOIService = null;
  
  public WebPOIServiceProxy() {
    _initWebPOIServiceProxy();
  }
  
  public WebPOIServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebPOIServiceProxy();
  }
  
  private void _initWebPOIServiceProxy() {
    try {
      webPOIService = (new server.WebPOIServiceImplServiceLocator()).getWebPOIServiceImplPort();
      if (webPOIService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webPOIService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webPOIService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webPOIService != null)
      ((javax.xml.rpc.Stub)webPOIService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public server.WebPOIService getWebPOIService() {
    if (webPOIService == null)
      _initWebPOIServiceProxy();
    return webPOIService;
  }
  
  public java.lang.String setMonitorData(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (webPOIService == null)
      _initWebPOIServiceProxy();
    return webPOIService.setMonitorData(arg0, arg1);
  }
  
  public java.lang.String registeruser(java.lang.String arg0) throws java.rmi.RemoteException{
    if (webPOIService == null)
      _initWebPOIServiceProxy();
    return webPOIService.registeruser(arg0);
  }
  
  public java.lang.String getMapData(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (webPOIService == null)
      _initWebPOIServiceProxy();
    return webPOIService.getMapData(arg0, arg1);
  }
  
  
}