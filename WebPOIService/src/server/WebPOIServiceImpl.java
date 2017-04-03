package server;
import main.Connect;
import lock.ReadWriteLock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

import GUI.dbgui;

import java.sql.Timestamp;
import java.util.Date;

@WebService(endpointInterface="server.WebPOIService") 
public class WebPOIServiceImpl implements WebPOIService {
 
	@Override
    @WebMethod 
     
	public String registeruser(String fields) {
		 
	
		String[] parts = fields.split("#");
	    String username=parts[0];
	    String pass1=parts[1];
	    String pass2=parts[2];
	      
   		 //elegxos gia to an to password pou dothike tairiazei me tin epivevaiwsi
	     if(!pass1.equals(pass2))	{    	  
	    	System.out.println("Password mismatch!");
	    	return "Password mismatch!";
	     }
	    	  
	     else{
	    	  Connection con1 = null; Statement statement = null; PreparedStatement preparedStatement = null;  ResultSet result = null; 
	          Statement select;  
	          int cnt = 0;
	          
	          //eisodos ston pinaka User pou perilamvanei olous tou eggegramenous xrhstes
	    	  String insertToUser = "INSERT INTO user"
	  				+ "(USERNAME,PASSWORD) VALUES"
	  				+ "(?, ?)";
	    	  con1 = Connect.getConnection();
	    	  ReadWriteLock L=new ReadWriteLock();
				try {
					L.lockRead();
	    	      try 
	    	        { 
	    	            select  = con1.createStatement();
	    	            result = select.executeQuery("select username from user where username like '" + username + "'");
	    	 // o cnt xrhsimopoieitai gia na elegksoume an uparxei hdh mesa sth vash xrhsths me to username 
	    	            while (result.next())  
	    	                cnt++;
	    	            
	    	         
	    	        } catch (SQLException ex) {
	    	            ex.printStackTrace();
	    	        }
	    	        finally
	    	        {
	    	            if(result != null)
	    	                try {
	    	                    result.close();
	    	                } catch (SQLException ex) {
	    	                	  ex.printStackTrace();
	    	                }
	    	        }
	    	      
	    	      if(cnt != 0){
  	                System.out.println("Username is already in use!");
  	                return "Username is already in use!";  	            
  	               }	    	        

	    	      // Statements allow to issue SQL queries to the database
	    	      try{
	    	      statement = con1.createStatement();
	    	      // Result set get the result of the SQL query
	    	      //epikoinwnia me webservice explorer kai ananewsh
	    	      preparedStatement = con1.prepareStatement(insertToUser);
	              preparedStatement.setString(1, username);
	              preparedStatement.setString(2, pass1);
	              preparedStatement.executeUpdate();
	    	        }  catch (SQLException ex) {
	    	            ex.printStackTrace();
	    	        }
	    	        finally
	    	        {
	    	            if(result != null)
	    	                try {
	    	                    result.close();
	    	                } catch (SQLException ex) {
	    	                	  ex.printStackTrace();
	    	                }
	    	        } 
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
	    	      finally{
				    	try {				    		
							L.unlockRead();
						} catch (InterruptedException e1) {
							
							e1.printStackTrace();
						}  				    	  
				    	  
				      }
	    	      System.out.println("Registration completed\n");
	    	
	    	  	}
	     
	     
   	return "Successful Registration";	
   	
	}
	
	
	public String setMonitorData(String fields, String newEntry){
		
		Connection connect;
		PreparedStatement preparedStatement,preparedStatement1,preparedStatement2;
		preparedStatement=preparedStatement1=preparedStatement2=null;
		ResultSet resultSet, result,result1,result2,result3,result4,result5,result6;
		resultSet=result=result1=result2=result3=result4=result5=result6=null;
		Statement statement,statement1,select,select1,select2,select3,select4,select5,select6;
		statement=statement1=select=select1=select2=select3=select4=select5=select6=null;
		int cnt,idType,userId,counter;
		cnt=idType=userId=counter=0;
		
		
		String[] part1 = fields.split("#");
		String username=part1[0];
		String password=part1[1];
		String[] part2 = newEntry.split("#");
		String x=part2[0];
		String y=part2[1];
		String type=part2[2];
		String name=part2[3];
		String temp = "";
		float x1= Float.parseFloat(x);
		float y1=Float.parseFloat(y);
		 
		
		
		String insertToPoi = "INSERT INTO poi"
			  				+ "(POINAME,IDTYPE,X,Y) VALUES"
			  				+ "(?, ?, ?, ?)";
	
		String insertToUserSet="INSERT INTO userset"
				 	+ "(IDUSER,TIME,X,Y) VALUES"
				 	+ "(?, ?,?,?)";
		
		Properties prop = new Properties();
		InputStream fis= null;
		 
    	try {
    		fis= new FileInputStream("settings.properties");
        //load a properties file
    		prop.load(fis);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
        } finally{
        	try{
        	fis.close();
        	}catch (IOException ioex){
        		ioex.printStackTrace();
        	}
        }
		
		String aktina = prop.getProperty("R");
		float R=Float.parseFloat(aktina);
		
		connect = Connect.getConnection();
		userId = Validation(fields, connect);
		if (userId>0 ){
			ReadWriteLock L=new ReadWriteLock();
			try {
				L.lockRead();
				try 
		        { 
		            select  = connect.createStatement();
		            result = select.executeQuery("select *from type where nametype like '" + type + "'");
		        
		            while (result.next()){  
		                
		                idType=result.getInt(1);
		            }	         
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        finally
		        {
		            if(result != null)
		                try {
		                    result.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
		        }
		        
		        cnt=0;
		        try 
		        { 
		            select6  = connect.createStatement();
		            result6 = select6.executeQuery("select *from poi where poiname like '" +name + "' and idtype='"+ idType +"'");
		          
		            while (result6.next()){ 
		            	/* edw elegxoume ean  yparxei se aktina R hdh poi me to idio onoma kai typo */
		            	if((Math.pow((result6.getDouble(3)-(x1)), 2))  + (Math.pow((result6.getDouble(4)-(y1)), 2))  <= (Math.pow(R,2))){
		            		x1=result6.getFloat(3);
		            		y1=result6.getFloat(4);
		            		cnt++;	    	            		
		            	}    	            	
		             
		            }
		               
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        finally
		        {
		            if(result6 != null)
		                try {
		                    result6.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
		        }
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
    	      finally{
			    	try {				    		
						L.unlockRead();
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}  				    	  
			    	  
			      }
			try {
				L.lockWrite();			
	    	        
		    	     
		    	     if(cnt==0){  // an o cnt isoutai me to 0 tote to POI mporei na eisaxthei kanonika , giati den vrethike allo sti vasi me to idio onoma kai typo se eyros R
			    	      try{
		    	        
		    	        statement = connect.createStatement();
			    	     
			    	      preparedStatement = connect.prepareStatement(insertToPoi);
			              preparedStatement.setString(1, name);
			              preparedStatement.setInt(2, idType);
			              preparedStatement.setFloat(3, x1);
			              preparedStatement.setFloat(4, y1);
			              preparedStatement.executeUpdate();
			              System.out.println("POI was successfully inserted!");
			    	      }
		 					catch (SQLException ex) {
		 						  System.out.println("Duplicate entry for coordinates x,y!");
		 						 temp = "Duplicate entry for coordinates x,y!";
		 						}
						     finally
						     {
						         if(preparedStatement != null)
						             try {
						                 preparedStatement.close();
						             } catch (SQLException ex) {
						             	  ex.printStackTrace();
						             	
						             }
						     }
			    	     }
		    	     else{
		    	    	 System.out.println("POI can't be inserted because it already exists in range R"); 		    	     
		    	    	 temp = "POI can't be inserted because it already exists in range R";
		    	     }
			              
			              try 
			    	        { 
			            	  	java.util.Date date= new java.util.Date();
			            	  	Timestamp currentTime=new Timestamp(date.getTime());
			    	            statement1 = connect.createStatement();
					    	     
					    	      preparedStatement1 = connect.prepareStatement(insertToUserSet);
					              preparedStatement1.setInt(1, userId);
					              preparedStatement1.setTimestamp(2, currentTime);
					              preparedStatement1.setFloat(3, x1);
					              preparedStatement1.setFloat(4,y1);
					              preparedStatement1.executeUpdate();
			    	      } catch (SQLException ex) {
			    	            System.out.println("The POI has already been set !");
				    	    	 return "The POI has already been set !";

			    	        }
			    	        finally
			    	        {
			    	            if(preparedStatement1 != null)
			    	                try {
			    	                    preparedStatement1.close();
			    	                } catch (SQLException ex) {
			    	                	  
			    	                }
			    	        }
			              temp = "swsta";
		    	     
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			 finally{
			    	try {
						L.unlockWrite();
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}  
			    	  
			      }
		}else if (userId==-1)
			return "Username not Found";
		else
			return "Wrong Password!";
		
		 return temp;
	 }
	
	public String getMapData(String fields, String position){
		
		
		Connection connect;
		Statement select=null;
		Statement select3=null;
		PreparedStatement preparedStatement=null;
		ResultSet result=null;
		ResultSet result3=null;
		String epistrofi="";
		String nmtype="";
		int userId=0;
		int poisCounter=0;		
		
		String insertToUserGet="INSERT INTO userget"
			 	+ "(USERID,X,Y,TIME) VALUES"
			 	+ "(?, ?,?, ?)";
		connect = Connect.getConnection();
		userId = Validation(fields, connect);
		String[] part1 = position.split("#");
		String x=part1[0];
		String y=part1[1];
		float x1= Float.parseFloat(x);
		float y1=Float.parseFloat(y);
		Properties prop = new Properties();
		InputStream fis= null;
		 
    	try {
    		fis= new FileInputStream("settings.properties");
    		//load a properties file
    		prop.load(fis);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
        } finally{
        	try{
        	fis.close();
        	}catch (IOException ioex){
        		ioex.printStackTrace();
        	}
        }
		String aktina = prop.getProperty("R");
		float R=Float.parseFloat(aktina);
		
		
		if (userId>0){
			
			ReadWriteLock L=new ReadWriteLock();
			try {
				L.lockRead();
				try { 
		            select  = connect.createStatement();
		            result = select.executeQuery("select *from poi");
		           
		            //xrhsimopoiouume th methodo concat gia na emfanistoun ta apotelesmata se ena string xwrismeno me #
		            while (result.next()){ 
		            	
		            	if((Math.pow((result.getDouble(3)-(x1)), 2))  + (Math.pow((result.getDouble(4)-(y1)), 2))  <= (Math.pow(R,2))){ 
		            		try {
		        	    		 	select3  = connect.createStatement();
				    	            result3 = select3.executeQuery("select nametype from type where idtype='"+result.getInt(2)+"'");          
				    	            while (result3.next()){  
				    	            	nmtype = result3.getString(1);
				    	            	
				    	            }				    	            
				    	        } catch (SQLException ex) {
				    	            ex.printStackTrace();
				    	        }
				    	        finally
				    	        {
				    	            if(result3 != null)
				    	                try {
				    	                    result3.close();
				    	                } catch (SQLException ex) {
				    	                	  ex.printStackTrace();
				    	                }
				    	        }
		            		
		            	poisCounter++;
		            	
		            	epistrofi=epistrofi.concat(result.getString(1));
		            	epistrofi=epistrofi.concat("#");
		            	epistrofi=epistrofi.concat(nmtype);
		            	epistrofi=epistrofi.concat("#");
		            	epistrofi=epistrofi.concat(Float.toString(result.getFloat(3)));
		            	epistrofi=epistrofi.concat("#");
		            	epistrofi=epistrofi.concat(Float.toString(result.getFloat(4)));
		            	epistrofi=epistrofi.concat("$");
		            	
		            	}
		            	
		            }		            
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        finally
		        {
		            if(result != null)
		                try {
		                    result.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
		        }
		
				try {  
					if(poisCounter==0){
						System.out.println("No POIS found in this region!");
						return "No POIS found in this region!";
				
						
						
					}
					java.util.Date date= new java.util.Date();
	    			 Timestamp currentTime=new Timestamp(date.getTime());
					 preparedStatement = connect.prepareStatement(insertToUserGet);
		              preparedStatement.setInt(1, userId);
		              preparedStatement.setFloat(2, x1);
		              preparedStatement.setFloat(3,y1);
		              preparedStatement.setTimestamp(4,currentTime);
		              preparedStatement.executeUpdate();
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        finally
		        {
		            if(preparedStatement != null)
		                try {
		                    preparedStatement.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
		        }
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		      finally{
			    	try {				    		
						L.unlockRead();
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}  				    	  
			    	  
			      }
			
			
		}else if (userId==-1)
			return "Username not Found";
		else
			return "Wrong Password!";
		
				System.out.println(epistrofi);
		
				return epistrofi;
		
	}
	
	public String deleteData(String fields, String position){
		
		Connection connect;
		Statement select=null;
		Statement select3=null;
		Statement statement=null;
		PreparedStatement preparedStatement=null;
		ResultSet result=null;
		ResultSet result3=null;
		String epistrofi="";
		int id = 0;
		int userId=0;
		int flag2 = 0;
		int poisCounter=0;
		
		connect = Connect.getConnection();
		userId = Validation(fields, connect);
		String[] part1 = position.split("#");
		String x=part1[0];
		String y=part1[1];
		float x1= Float.parseFloat(x);
		float y1=Float.parseFloat(y);
		
		if (userId>0){
			
			ReadWriteLock L=new ReadWriteLock();
			try {
				L.lockRead();
				try { 
		            select  = connect.createStatement();
		            result = select.executeQuery("select idUser from userset where x='" + x1 + "' and y='"+ y1 +"'");
		            while (result.next()){  
    	            	id = result.getInt(1);
    	            	if (id != userId)
    	            		flag2=1;
    	             
    	            }
		            
	        	} catch (SQLException ex) {
	        		ex.printStackTrace();
	        	
	        	}finally
				{
		            if(result != null)
		                try {
		                    result.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
				}
	         } catch (InterruptedException e) {					
					e.printStackTrace();
				
	         }finally{
				    	try {				    		
							L.unlockRead();
						} catch (InterruptedException e1) {							
							e1.printStackTrace();
						}  		    	  
		      }
			
			if (flag2==0){
				try {
					L.lockWrite();
					try { 
						 statement = connect.createStatement();				        	
					     statement.executeUpdate("delete from poi where x='" + x1 + "' and y='"+ y1 +"'");
			           
			            System.out.println("efuges!");
			            
		        	} catch (SQLException ex) {
		        		ex.printStackTrace();
		        	
		        	}finally
					{
			            if(result != null)
			                try {
			                    result.close();
			                } catch (SQLException ex) {
			                	  ex.printStackTrace();
			                }
					}
		         } catch (InterruptedException e) {					
						e.printStackTrace();
					
		         }finally{
					    	try {				    		
								L.unlockWrite();
							} catch (InterruptedException e1) {							
								e1.printStackTrace();
							}  		    	  
			      }				
				
			}	
			else 
				System.out.println("Cannot be deleted!");
		
		}else if (userId==-1)
			return "Username not Found";
		else
			return "Wrong Password!";
		
		return "Deleted";
		
	}
	
	
	//methodos gia na elegxoume an ta stoixeia tou xrhsth uparxoun sth vash
	public int Validation (String fields, Connection connect){
		Statement statement = null;
		Statement select=null;
		ResultSet result=null;
		int counter=0; int userId=0;
		String userPassword="";	
		String[] part1 = fields.split("#");
		String username=part1[0];
		String password=part1[1];
		ReadWriteLock L=new ReadWriteLock();
		try {
			L.lockRead();
	        try{
	        	statement = connect.createStatement();
	  	      // Result set get the result of the SQL query
		        select=connect.createStatement();
		        result=select.executeQuery("select *from user where username like '"+username+"'");
		        while(result.next()){
		        	 counter++;
		        	 userId=result.getInt(1);
		        	 userPassword=result.getString(3);	
		        	
		        }
		        if (counter==0){
		        	System.out.println("Username not Found");
		        	return -1;
		        	
		        }
		        if(!userPassword.equals(password)){
		        	System.out.println("Wrong Password!");
		        	return 0 ;
		        	
		        }	    	        
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		     
		        finally
		        {
		            if(result != null)
		                try {
		                    result.close();
		                } catch (SQLException ex) {
		                	  ex.printStackTrace();
		                }
		        }
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	      finally{
		    	try {				    		
					L.unlockRead();
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}  				    	  
		    	  
		      }
		return userId;
	}
}
