/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import lock.ReadWriteLock;
  
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface WebPOIService 
{      
    @WebMethod 
    public String registeruser(String fields);
	public String setMonitorData(String fields, String newEntry);
	public String getMapData(String fields, String position);
	public String deleteData(String fields, String position);
}