package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Properties;

import lock.ReadWriteLock;
import main.Connect;

import java.sql.Connection;




public class dbgui {
	
	
	JFrame mainframe;	//kentriko parathuro
	JButton but1;		
	JButton but2;
	JButton refr;
	JButton go;
	JTextField tf1;
	JTextField tf2;
	JTextField tfx;
	JTextField tfy;
	JPanel p1;
	JLabel msg1;
	JLabel msg2;
	JTextField  gt;
	Timer timer;
	
	
	
	public dbgui(){
		
		frame();
		btnActions();
		
		
	}
	
	class MyWindowListener implements WindowListener {
		   public void windowActivated(WindowEvent e) {}
		   public void windowClosed(WindowEvent e) {}
		   public void windowClosing(WindowEvent e) {
			   Connect.closecon();
			   timer.cancel();
			   System.exit(0);
		     
		   }
		   public void windowDeactivated(WindowEvent e) {}
		   public void windowDeiconified(WindowEvent e) {}
		   public void windowIconified(WindowEvent e) {}
		   public void windowOpened(WindowEvent e) {}
		}
	
	
	
	public void frame() {
		Statement select=null;
		ResultSet result=null;
		
		
		mainframe = new JFrame("POIs Application");
		mainframe.setVisible(true);
		mainframe.setSize(500,300);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyWindowListener w = new MyWindowListener();
        mainframe.addWindowListener(w);
        mainframe.setVisible(true);
	   
		
		//mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		JTabbedPane tabs = new JTabbedPane();  // dhmiourgia twn tabbed panels		
		
		
		//Prwto tab		
		p1 = new JPanel();
		p1.setLayout(null);
		go = new JButton ("Go");
		go.setSize(80, 20);
		go.setLocation(370, 60);
		msg1 = new JLabel("Total Get: ");
		msg2 = new JLabel("Total Set:");
		JLabel msgx= new JLabel("Give x :");
		JLabel msgy= new JLabel("Give y :");
		msg1.setSize(70, 20);
		msg1.setLocation(20, 20);
		msg2.setSize(70, 20);
		msg2.setLocation(200, 20);
		msgx.setSize(70, 20);
		msgx.setLocation(20, 60);
		msgy.setSize(70, 20);
		msgy.setLocation(200, 60);
		
		refr = new JButton("Refresh");
		refr.setSize(80, 20);
		refr.setLocation(370, 20);
		tf1 = new JTextField(10);
		tf1.setLocation(100, 20);
		tf1.setSize(70, 20);
		tf2 = new JTextField(10);
		tf2.setLocation(280, 20);
		tf2.setSize(70, 20);
		tfx = new JTextField(10);
		tfx.setLocation(100, 60);
		tfx.setSize(70, 20);
		tfy = new JTextField(10);
		tfy.setLocation(280, 60);
		tfy.setSize(70, 20);
		tf1.setEditable(false);
		tf2.setEditable(false);
		p1.add(msg1);
		p1.add(tf1);
		p1.add(msg2);
		p1.add(tf2);
		p1.add(refr);
		p1.add(msgx);
		p1.add(tfx);
		p1.add(msgy);
		p1.add(tfy);
		p1.add(go);
		
			
		//Deutero tab
		JPanel p2 = new JPanel();
		but1 = new JButton ("Delete");
		but2 = new JButton ("Search");
		JLabel msg = new JLabel("Insert Username");
		gt = new JTextField(15);		
		p2.add(msg);
		p2.add(gt);
		p2.add(but2);
		p2.add(but1);
		
		
		tabs.add("General Info", p1);
		tabs.add("Admin Tools", p2);		
		mainframe.add(tabs);
		mainframe.revalidate();
		
			//metatropi tis parametrou T se seconds	
		
		
		int xronos = getT() * 1000;
		timer = new Timer();
		timer.schedule(new getSetGet(), 0, xronos);		
		
		
	}	
	
	class getSetGet extends TimerTask {
	    public void run() {
	    	Statement statement = null;
			Statement select=null;
			ResultSet result=null;
			String action="";
		    int getSum=0;
		    int setSum=0;
		    Connection connect = Connect.getConnection();
		    java.util.Date date= new java.util.Date();
			Timestamp currentTime=new Timestamp(date.getTime());
			Timestamp actionTime;
			long xronos = getT();
	
			 
	    	try 
	    	
	        { 
      		 
	            select  = connect.createStatement();
	            result = select.executeQuery("select *from userset");          
	            while (result.next()){  
	                
	                //action=result.getString(4);
	                actionTime=result.getTimestamp(2);
	                long milliseconds1 = actionTime.getTime();	  			  
	                long milliseconds2 = currentTime.getTime();
	                long diff = milliseconds2 - milliseconds1;
		  			long diffSeconds = diff / 1000;
		  			
	  			  if(diffSeconds<=xronos){  				  
		                
		                	setSum++;		          
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
	    	try 
	    	
	        { 
      		 
	            select  = connect.createStatement();
	            result = select.executeQuery("select *from userget");          
	            while (result.next()){  	                
	                
	                actionTime=result.getTimestamp(4);
	                long milliseconds1 = actionTime.getTime();
	  			  
	                long milliseconds2 = currentTime.getTime();
	                long diff = milliseconds2 - milliseconds1;
	  			    long diffSeconds = diff / 1000;
	  			    
	  			  if(diffSeconds<=xronos){
	  				  
		                	getSum++;		          
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
	    	
	    	tf1.setText(Integer.toString(getSum));
	    	tf2.setText(Integer.toString(setSum));
	    	System.out.println("The graphic environment was automatically refreshed");
	    	
	    }
	 }
	
public String defuser(String usrnm){
		
		Statement statement = null;
		Statement select=null;		
		ResultSet result=null;		
		int userId=0;
		int counter=0;							
		String userPassword="";
		String epistrofi="";
		
		
		Connection connect = Connect.getConnection();
			   						
        try{
        statement = connect.createStatement();
  	      // Result set get the result of the SQL query
	        select=connect.createStatement();
	        result=select.executeQuery("select *from user where username like '"+usrnm+"'");
	        while(result.next()){
	        	counter++;  // vrethike to username stin vasi
	        	userId=result.getInt(1);
	        	userPassword=result.getString(3);	
	        	
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
        if (counter==0){    //an den vrethei xrhsths me auto to username stin vasi
        	epistrofi="0#0";
        	
        	
        }  else {
		        epistrofi=Integer.toString(userId);
		        epistrofi=epistrofi.concat("#");
		        epistrofi=epistrofi.concat(userPassword);
        }
        return epistrofi;		
		
	}

public int getT(){
	
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
	
	 return (Integer.parseInt(prop.getProperty("T")));
	
}

	
	public void btnActions(){
		
		
		refr.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				  
				   Statement statement = null;
					Statement select=null;
					ResultSet result=null;
					String action="";
				    int getSum=0;
				    int setSum=0;
				    Connection connect = Connect.getConnection();
				    java.util.Date date= new java.util.Date();					
					Timestamp currentTime=new Timestamp(date.getTime());
					Timestamp actionTime;
					long xronos = getT();
										 
			    	try 
			    	
			        { 
		      		 
			            select  = connect.createStatement();
			            result = select.executeQuery("select *from userset");          
			            while (result.next()){  
			                
			                //action=result.getString(4);
			                actionTime=result.getTimestamp(2);
			                long milliseconds1 = actionTime.getTime();	  			  
			                long milliseconds2 = currentTime.getTime();
			                long diff = milliseconds2 - milliseconds1;
				  			long diffSeconds = diff / 1000;
				  			
			  			  if(diffSeconds<=xronos){				                
				                	setSum++;			          
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
			    	try 
			    	
			        { 
		      		 
			            select  = connect.createStatement();
			            result = select.executeQuery("select *from userget");          
			            while (result.next()){  			                
			                
			                actionTime=result.getTimestamp(4);
			                long milliseconds1 = actionTime.getTime();
			  			  
			                long milliseconds2 = currentTime.getTime();
			                long diff = milliseconds2 - milliseconds1;
			  			    long diffSeconds = diff / 1000;
			  			    
			  			  if(diffSeconds<=xronos){				                
				                	getSum++;				          
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
			    	
			    	tf1.setText(Integer.toString(getSum));
			    	tf2.setText(Integer.toString(setSum));			   
				   
			   }
		});
		
		go.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				Statement select=null;
				Statement select1=null;
				Statement select2=null;
				Statement select3=null;
				Statement select4=null;
				ResultSet result=null;
				ResultSet result1=null;
				ResultSet result2=null;
				ResultSet result3=null;
				ResultSet result4=null;
				
				float Resultx=0;
				float Resulty=0;
				int setCounter=0;
				int getCounter=0;
				int userId=0;
				int userIdGet=0;
				double x = Double.parseDouble(tfx.getText());
				double y = Double.parseDouble(tfy.getText());
				String xString="";
				String yString="";
				String poiName="";
				String username="";
				String poisSet="";
				String poisGet="";
				Connection connect = Connect.getConnection();
				java.util.Date date= new java.util.Date();
				System.out.println(new Timestamp(date.getTime()));
				Timestamp currentTime=new Timestamp(date.getTime());
				Timestamp actionTime;
				long xronos = getT();
				
				
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
				
				
				try { 
		            select  = connect.createStatement();
		            result = select.executeQuery("select *from poi");
		           
		            //xrhsimopoiouume th methodo concat gia na emfanistoun ta apotelesmata se ena string xwrismeno me #
		            while (result.next()){ 
		            	
		            	if((Math.pow((result.getDouble(3)-(x)), 2))  + (Math.pow((result.getDouble(4)-(y)), 2))  <= (Math.pow(R,2))){ 
		            	
		            	
		            		Resultx=result.getFloat(3);
		            		Resulty=result.getFloat(4);
		            		xString=Float.toString(Resultx);
		            		yString=Float.toString(Resulty);
		            		
		            		poiName=result.getString(1);
			            	try 
        			    	
        			        { 
        		      		 
        			            select3 = connect.createStatement();
        			            result3 = select3.executeQuery("select *from userget where x='"+Resultx+"' and y='"+Resulty+"'");          
        			            while (result3.next()){  
        			            	actionTime=result3.getTimestamp(4);
	            	                long milliseconds1 = actionTime.getTime();
	            	  			  
	            	                long milliseconds2 = currentTime.getTime();
	            	                long diff = milliseconds2 - milliseconds1;
	            	  			    long diffSeconds = diff / 1000;
	            	  			    
	            	  			  if(diffSeconds<=xronos){
	            	  				  
	            		                	getCounter++;
	            		                	userIdGet=result3.getInt(1);
	            		                	try 
			            			    	
			            			        { 
			            		      		 
			            			            select4 = connect.createStatement();
			            			            result4 = select4.executeQuery("select *from user where userid='"+userIdGet+"'");          
			            			            while (result4.next()){  
			            			            	
			            			            	username=result4.getString(2);
			            			            	
			            			            	poisGet = poisGet.concat(username +"#"+ poiName +"#"+ xString+"#" + yString +"$");
			            			               
			            			            }  
			            			        } catch (SQLException ex) {
			            			            ex.printStackTrace();
			            			        }
			            			        finally
			            			        {
			            			            if(result4 != null)
			            			                try {
			            			                    result4.close();
			            			                } catch (SQLException ex) {
			            			                	  ex.printStackTrace();
			            			                }
			            			        } 	            		          
	            	  			  }
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
			            	  try{
			            	        
			            	  	      // Result set get the result of the SQL query
			            		        select1=connect.createStatement();
			            		        result1=select1.executeQuery("select *from userset where x='"+ Resultx +"' and y='"+Resulty+"'");
			            		        while(result1.next()){
			            		        	actionTime=result1.getTimestamp(2);
			            	                long milliseconds1 = actionTime.getTime();
			            	  			  
			            	                long milliseconds2 = currentTime.getTime();
			            	                long diff = milliseconds2 - milliseconds1;
			            	  			    long diffSeconds = diff / 1000;
			            	  			    
			            	  			  if(diffSeconds<=xronos){        	  				  

			            		                setCounter++;
					            		        userId=result1.getInt(1);
			            		          
			            	  			  
			            		        	
			            		        	try 
			            			    	
			            			        { 
			            		      		 
			            			            select2 = connect.createStatement();
			            			            result2 = select2.executeQuery("select *from user where userid='"+userId+"'");          
			            			            while (result2.next()){  		
			            			            	
			            			            	username=result2.getString(2);			            			            	
			            			            	poisSet = poisSet.concat(username +"#"+ poiName +"#"+ Resultx+"#" + Resulty +"$");
			            			                
			            			            }  
			            			        } catch (SQLException ex) {
			            			            ex.printStackTrace();
			            			        }
			            			        finally
			            			        {
			            			            if(result2 != null)
			            			                try {
			            			                    result2.close();
			            			                } catch (SQLException ex) {
			            			                	  ex.printStackTrace();
			            			                }
			            			        }	
			            	  			  }
			            		        	
			            		        }			            		        
			            		        
			            			 } catch (SQLException ex) {
			            			            ex.printStackTrace();
			            			 }
			            			     
			            			  finally
			            			  {
			            			      if(result1 != null)
			            			          try {
			            			                result1.close();
			            			          } catch (SQLException ex) {
			            			       	  ex.printStackTrace();
			            			          }
			            			  }			            	
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

				if (setCounter>=1){
					
					 String[] columnNames = {"Username","PoiName","X","Y"};
			         String[] parts = poisSet.split("\\$");
			         String[][] data = new String[parts.length][];
			         int r = 0;
			         for (String row : parts) {
			             data[r++] = row.split("#");
			         }
			         
			         JTable table = new JTable(data, columnNames);
			         table.setEnabled(false);
			         JScrollPane scrollPane = new JScrollPane(table);
			         
			         scrollPane.setPreferredSize(new Dimension(450, 200));
			         table.setFillsViewportHeight(false);
			         JFrame res1 = new JFrame("Results");
					 JPanel p = new JPanel();
			         res1.setVisible(true);
				     res1.setSize(600,300);
					 res1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					 JLabel totalSets = new JLabel("Number of users that used the set method for the geographic region around(" +x+","+y+")");
					 JTextField userSets = new JTextField(15);	
					 userSets.setText(Integer.toString(setCounter));
					 p.add(totalSets);
				 	 p.add(userSets);				 	
					 p.add(scrollPane);
					 res1.add(p);
			         
				}
				else 
					JOptionPane.showMessageDialog(null,"No sets for this spot in the past "+xronos+" seconds");
				
				if (getCounter>=1){
					
					 String[] columnNames = {"Username","PoiName","X","Y"};
			         String[] parts = poisGet.split("\\$");
			         String[][] data = new String[parts.length][];
			         int r = 0;
			         for (String row : parts) {
			             data[r++] = row.split("#");
			         }
			         
			         JTable table = new JTable(data, columnNames);
			         table.setEnabled(false);
			         JScrollPane scrollPane = new JScrollPane(table);
			         
			         scrollPane.setPreferredSize(new Dimension(450, 200));
			         table.setFillsViewportHeight(false);
			         JFrame res1 = new JFrame("Results");
					 JPanel p = new JPanel();
			         res1.setVisible(true);
				     res1.setSize(600,300);
					 res1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					
					 JLabel totalGets = new JLabel("Number of users that used the get method for the geographic region around ("+x+","+y+")" );
					 JTextField userGets = new JTextField(15);	
					 userGets.setText(Integer.toString(getCounter));
					 p.add(totalGets);
				 	p.add(userGets);				 	
			  		p.add(scrollPane);
						 res1.add(p);	
				}
				else 
					JOptionPane.showMessageDialog(null,"No gets for this spot in the past "+xronos+" seconds");		        
				   
				   
			   }				   
				   
		});
		
			
		but1.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				    String str = gt.getText();
				   
				    Statement statement = null;
					Statement select=null;
					Statement select2=null;
					ResultSet result=null;
					ResultSet result2=null;
					Statement statement1 = null;
					Statement select1=null;
					ResultSet result1=null;
					int userId=0;
					int counter=0;
					int poiId=0;					
					String userPassword="";
					String poiName="";
					String typeId="";
					String x1;
					String y1;
					String epistrofi="";
				
					Connection connect = Connect.getConnection();
									   						
					epistrofi=defuser(str);
					String fields[]=epistrofi.split("#");
					userId=Integer.parseInt(fields[0]);
					userPassword=fields[1];
					
			       
				        if (userId==0){    //an den vrethei xrhsths me auto to username stin vasi
				        	JOptionPane.showMessageDialog(null,"User not found!","Error",JOptionPane.ERROR_MESSAGE);
				        	
				        }
				        else{
				        ReadWriteLock L=new ReadWriteLock();
					      try {
							L.lockWrite();					      
					        try {
					        	
				        	    statement = connect.createStatement();				        	
						        statement.executeUpdate("delete from user where userid ='" + userId + "'" );
						        JOptionPane.showMessageDialog(null, "User deleted succesfully");
						       
					        }
					        catch (SQLException ex) {
					        	ex.printStackTrace();
					        }	
					        finally
					        {
					            if(statement != null)
					                try {
					                    statement.close();
					                } catch (SQLException ex) {
					                	  ex.printStackTrace();
					                }
					        }
					      } catch (InterruptedException e1) {
							
								e1.printStackTrace();
							}
					      finally{
					    	try {					    		
								L.unlockWrite();
							} catch (InterruptedException e1) {
								
								e1.printStackTrace();
							}  
					      }					        
				         }				          
					        
			   }
				  
			});
		
		
		but2.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				    String str = gt.getText();
				    String action="";
				    double UserGetCounter=0;
				    double UserSetCounter=0;
				   
				    Statement statement = null;
					Statement select=null;
					Statement select2=null;
					Statement select3=null;
					ResultSet result=null;
					ResultSet result2=null;
					ResultSet result3=null;
					Statement statement1 = null;
					Statement select1=null;
					ResultSet result1=null;
					int userId=0;
					int counter=0;
					int poiId=0;
					double setSum=0;
					double getSum=0;
					int counterPois=0;
					double pos1=0;
					double pos2=0;
					String userPassword="";
					String poiName="";
					String typeId="";
					String nmtype="";
					Float x1;
					Float y1;
					String epistrofi="";
					DecimalFormat df = new DecimalFormat("#,###,##0.00");
					
					Connection connect = Connect.getConnection();
					JFrame res = new JFrame("Results");
					JPanel p = new JPanel();
					
					
					epistrofi=defuser(str);
					String fields[]=epistrofi.split("#");
					userId=Integer.parseInt(fields[0]);
					userPassword=fields[1];
					
					
				       
			        if (userId==0){    //an den vrethei xrhsths me auto to username stin vasi
			        	JOptionPane.showMessageDialog(null,"User not found!","Error",JOptionPane.ERROR_MESSAGE);
			        	
			        }
			  	        else {
			  	        	 java.util.Date date= new java.util.Date();
					  	        Timestamp currentTime=new Timestamp(date.getTime());
			  	        		Timestamp actionTime;
			  	        		int xronos = getT();

							try 
			    	        { 
		  	        		 /*!!!!!! na dialegei moni ti stili action*/
			    	            select3  = connect.createStatement();
			    	            result3 = select3.executeQuery("select *from userget");          
			    	            while (result3.next()){  
			    	            	actionTime=result3.getTimestamp(4);
	            	                long milliseconds1 = actionTime.getTime();
	            	  			  
	            	                long milliseconds2 = currentTime.getTime();
	            	                long diff = milliseconds2 - milliseconds1;
	            	  			    long diffSeconds = diff / 1000;
	            	  			    
	            	  			  if(diffSeconds<=xronos){ 
			    	                if(result3.getInt(1)==userId)			    	                	
			    	                	UserGetCounter++;				    	                
						  	        getSum++;		    	
	            	  			  }
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
							try 
			    	        { 
		  	        		 /*!!!!!! na dialegei moni ti stili action*/
			    	            select3  = connect.createStatement();
			    	            result3 = select3.executeQuery("select *from userset");          
			    	            while (result3.next()){  
			    	            	actionTime=result3.getTimestamp(2);
			    	            	long milliseconds1 = actionTime.getTime();
		            	  			  
	            	                long milliseconds2 = currentTime.getTime();
	            	                long diff = milliseconds2 - milliseconds1;
	            	  			    long diffSeconds = diff / 1000;
	            	  			    
	            	  			  if(diffSeconds<=xronos){ 
			    	                if(result3.getInt(1)==userId)			    	                	
			    	                	UserSetCounter++;			    	                
						  	        setSum++; 	
	            	  			  }
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
			  	        	counter=0;
			  	        	try {
			  	        		statement1 = connect.createStatement();
					    	      // Result set get the result of the SQL query
					  	        select1=connect.createStatement();
					  	        result1=select1.executeQuery("select *from userset where idUser= '"+userId+"'");
					  	        
					  	        String s="";
					  	       
					  	        while(result1.next()){					  	        				
					  	        	
					  	        actionTime=result1.getTimestamp(2);				  	        	
					  	        x1=result1.getFloat(3);
					  	        y1=result1.getFloat(4);
					  	        
					  	        long milliseconds1 = actionTime.getTime();	  			  
					            long milliseconds2 = currentTime.getTime();
					            long diff = milliseconds2 - milliseconds1;
						  	    long diffSeconds = diff / 1000;
						  		
						  		
						  		if(diffSeconds<=xronos){ 
						  				counter++;
					  	        	
					  	        	if (counter!=0){
					  	        		
					  	        		 try {
					  	        			 
								        	    statement = connect.createStatement();
								        	    select2=connect.createStatement();
								        	    result2=select2.executeQuery("select *from poi where x='"+x1+"' and y='"+y1+"'");
								        	    while(result2.next()){
								        	    	
								        	    	poiName=result2.getString(1);
								        	    	typeId=result2.getString(2);
								        	    	x1=result2.getFloat(3);
								        	    	y1=result2.getFloat(4);
								        	    	
								        	    	 try {
								        	    		 select3  = connect.createStatement();
										    	            result3 = select3.executeQuery("select nametype from type where idtype='"+typeId+"'");          
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
								        	    	 }
					  	        		 
								        	    	
								        	    	
								        	    	s = s.concat(poiName +"#"+ nmtype +"#"+ x1+"#" + y1+"$");
								        	    	
								        	    
									        }
									        catch (SQLException ex) {
									        	ex.printStackTrace();
									        }	
									        finally
									        {
									            if(statement != null)
									                try {
									                    statement.close();
									                } catch (SQLException ex) {
									                	  ex.printStackTrace();
									                }
									        }
							  			}
					  	        	}
					  	        	
			  	        	}
					  	      if (counter==0) //o xrhsths den exei eisagei dedomena             
					              JOptionPane.showMessageDialog(res,"User "+str+" hasn't set any pois the last "+xronos+" seconds ");					  	        		
				  	        
					  	    if (counter >= 1){
				  	        	res.setVisible(true);
						        res.setSize(600,400);
							    res.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
		        	    	
				  	        	
							    JLabel username = new JLabel("Username:");
							    JLabel pass = new JLabel("Password:");
							    JLabel set = new JLabel ("set %:");
							    JLabel get = new JLabel ("get %:");
										
							    JTextField t1 = new JTextField(10);
							    JTextField t2 = new JTextField(10);
							    JTextField t3 = new JTextField(10);
							    JTextField t4 = new JTextField(10);
								t1.setEditable(false);
								t2.setEditable(false);
								t3.setEditable(false);
								t4.setEditable(false);
								t1.setText(str);
								t2.setText(userPassword);
								if (setSum!=0)
									pos1=UserSetCounter/setSum;
								if (getSum!=0)
									pos2=UserGetCounter/getSum;
								
								t3.setText(String.valueOf(df.format(pos1*100)));
								t4.setText(String.valueOf(df.format(pos2*100)));
								
								JPanel p2 = new JPanel();	// Dhmiourgia tou panel opou 8a mpoun ta apotelesmata			
									p2.setLayout(new GridLayout(4, 2,10,10));
									p2.add(username);
									p2.add(t1);
									p2.add(pass);
									p2.add(t2);
									p2.add(set);
									p2.add(t3);
									p2.add(get);
									p2.add(t4);
						         String[] columnNames = {"Spot Name","Type","X","Y"};
						         String[] parts = s.split("\\$");
						         String[][] data = new String[parts.length][];
						         int r = 0;
						         for (String row : parts) {
						             data[r++] = row.split("#");
						         }
						         
						         JTable table = new JTable(data, columnNames);
						         table.setEnabled(false);
						         JScrollPane scrollPane = new JScrollPane(table);
						         //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						         scrollPane.setPreferredSize(new Dimension(450, 200));
						         table.setFillsViewportHeight(false);
						         p.add(p2);
						         p.add(scrollPane);					         
						         res.add(p);
					  	    }
					         
					         
			  	        	} catch (SQLException ex) {
			  	        		ex.printStackTrace();
			  	        	}
			  	        	finally
				  	        {
				  	            if(result1 != null)
				  	                try {
				  	                    result1.close();
				  	                } catch (SQLException ex) {
				  	                	  ex.printStackTrace();
				  	                }
				  	        }
			  	        	
			  	        }
			  	            
			  	        				 
			   }	

		});		
		
		
	}
	
	
	
	
}
