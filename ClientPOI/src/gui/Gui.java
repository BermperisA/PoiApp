package gui;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import server.WebPOIService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;
import gui.Gui.MyWindowListener;
import main.Service;

public class Gui {
	JFrame mainframe;	//kentriko parathuro
	JFrame results;  //parathuro apotelesmatwn
	JPanel p1;
	JPanel p2;
	JButton but1;	
	JButton registration;
	JButton home;
	JButton register;
	JButton setMonitorData;
	JButton getMapData;
	JButton set;
	JButton get;
	JButton refr;
	JTextField usrnm;
	JTextField pwd;
	JTextField cx;
	JTextField cy;
	JTextField cx1;
	JTextField cy1;
	JTextField cpwd;
	JTextField tfy;
	JTextField tp;
	JTextField nm;
	JLabel picLabel;
	JLabel picLabel2;
	JLabel username;        
	JLabel password;
	JLabel x;
	JLabel y;
	JLabel confirmPass;
	JLabel type;
	JLabel name;	
	JLabel dokimi;
	JCheckBox Etype,Dtype,Stype,Ctype,Ltype,Utype,TAtype,FFtype,TRtype,Edtype,Ftype;
	JComboBox typesSet;	
	String eTypeName;
	String typename;
	String typeName;
	String x1;
	String y1;
	String filter;
	private int flag=0;	
	Image img;
	Image wlc;
	Image botimg;
	Image sign;
	Image rimg;
	Timer timer;
	int ffSelected=0;
	int trSelected=0;
	int taSelected=0;
	int dSelected=0;
	int cSelected=0;
	int ssSelected=0;
	int lSelected=0;
	int uSelected=0;
	int xronos;
	WebPOIService server=Service.getService();
	

	
	public Gui(){
				
		frame();
		btnActions();

	}
	

	
	public void frame() {
		//frames
		mainframe = new JFrame("POIs Application");
		mainframe.setVisible(true);
		mainframe.setSize(640,350);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        results = new JFrame("Results");
        results.setSize(500,350);
   		results.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		MyWindowListener w = new MyWindowListener();
        results.addWindowListener(w);
        
        //panels
        p1 = new JPanel(null);
        p2 = new JPanel();
        
        // loading images
		try {
			
		   img = ImageIO.read(getClass().getResource("/home_icon-blue.png"));
		   wlc = ImageIO.read(getClass().getResource("/welcome.png"));
		   botimg = ImageIO.read(getClass().getResource("/Globe1.png"));
		   sign = ImageIO.read(getClass().getResource("/sign.png"));
		   rimg = ImageIO.read(getClass().getResource("/refresh.png"));
		} catch (IOException ex) {
		}
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
		
		xronos = Integer.parseInt(prop.getProperty("T"));

		//common fields/labels
		username = new JLabel("Username :");        
		password= new JLabel("Password :");
		x = new JLabel("Coordinate x :");
		y = new JLabel("Coordinate y :");
		usrnm=new JTextField(25);
		pwd=new JPasswordField(25);
	    cx=new JTextField(25);
		cy=new JTextField(25);
		cx1=new JTextField(25);
		cy1=new JTextField(25);		
		
		//buttons
		refr = new JButton();
        register = new JButton("Register");
        setMonitorData=new JButton("SetMonitorData");
        getMapData=new JButton("getMapData");
        get=new JButton("get");
        set = new JButton("Set");
		home = new JButton();
		refr.setSize(30, 30);
	    refr.setIcon(new ImageIcon(rimg));
		home.setSize(30, 30);
		home.setLocation(10, 10);
	    home.setIcon(new ImageIcon(img));
	    registration = new JButton();
		registration.setIcon(new ImageIcon(sign));
		registration.setSize(91, 39);
		registration.setLocation(250, 100);
	    //image labels
		picLabel =new JLabel();
		picLabel.setSize(150, 48);
		picLabel.setLocation(220, 5);	
		picLabel2 =new JLabel();
	    picLabel2.setSize(83, 97);
	    picLabel2.setLocation(480, 200);
	    picLabel2.setIcon(new ImageIcon(botimg));
	    picLabel.setIcon(new ImageIcon(wlc));
	    
	
		File f = new File("fields.properties");
		if (f.exists()){
			login();
			flag=1;  // exei ginei eggrafh
		}
		else{
			initialscreen();
       
			}
		
	}
	
	
	public void setProps(){
		
		if (flag==1){  // an exei ginei eggrafh fortose ta stoixeia
			
			Properties prop = new Properties();
			InputStream fis= null;
			 
	    	try {
	    		fis= new FileInputStream("fields.properties");
	    		
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
			
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			
			usrnm.setText(username);
			pwd.setText(password);
			
		}	
		else
			initialscreen();
		
		
	}
	
	
	
	public void initialscreen(){
				 

		 p1.add(picLabel);
		 p1.add(registration);
		 mainframe.add(p1);
		 mainframe.revalidate();
		 mainframe.repaint();		
	}
	
	public void login(){
			
			p1.removeAll();
			p1.revalidate();
			p1.repaint();				
			
			//Edw tora erxontai oi setmonitordata kai getmapdata 
			usrnm.setEditable(false);
			pwd.setEditable(false);
			setMonitorData.setSize(180, 35);
			setMonitorData.setLocation(120, 80);
			
			getMapData.setSize(180, 35);
			getMapData.setLocation(320, 80);
			p1.add(picLabel2);
			p1.add(home);
			p1.add(setMonitorData);
			p1.add(getMapData);
			p1.revalidate();
			p1.repaint();
	
			mainframe.add(p1);
			mainframe.revalidate();
			mainframe.repaint();
					
		}
	
	
	public void registration(){
		
		p1.removeAll();
		p1.revalidate();
		p1.repaint();		
		
		confirmPass= new JLabel("Confirm Password :");
		cpwd=new JPasswordField(25);
		
		username.setSize(100, 20);
		username.setLocation(20, 50);			
		usrnm.setSize(150, 20);
		usrnm.setLocation(200, 50);		
		password.setSize(150, 20);
		password.setLocation(20, 80);
		pwd.setSize(150, 20);
		pwd.setLocation(200, 80);
		confirmPass.setSize(150,20);
		confirmPass.setLocation(20,110);
		cpwd.setSize(150,20);
		cpwd.setLocation(200,110);
		register.setSize(100,30);
		register.setLocation(200,150);
		
		p1.add(username);
		p1.add(usrnm);
		p1.add(password);
		p1.add(pwd);
		p1.add(confirmPass);
		p1.add(cpwd);
		p1.add(register);		
		
		mainframe.add(p1);	
		mainframe.revalidate();
		mainframe.repaint();		
	}
	
	
	public void getMapData(){
		p1.removeAll();
		p1.revalidate();
		p1.repaint();	
	
		typeName = "";
		//String[] types = { "Entertainment", "   Drink","   Cinema", "   Site seeing", "Education","   Library", "   University", "Food","   Fast food Restaurant", "   Take away Restaurant", "   Typical Restaurant" };
		Etype=new JCheckBox("Entertainment");
		Edtype=new JCheckBox("Education");	
		Ftype=new JCheckBox("Food");
		
		Font newCheckBoxFont=new Font(Etype.getFont().getName(),Font.ITALIC+Font.BOLD,Etype.getFont().getSize());  
		 
		 //Set JCheckBox font using new created font  
		Etype.setFont(newCheckBoxFont);  
		Ftype.setFont(newCheckBoxFont);
		Edtype.setFont(newCheckBoxFont);
		Dtype=new JCheckBox("Drink");
		Ctype=new JCheckBox("Cinema");
		Stype=new JCheckBox("Site seeing");	
	
		Ltype=new JCheckBox("Library");
		Utype=new JCheckBox("University");
		
		FFtype=new JCheckBox("Fast Food Restaurant");
		TAtype=new JCheckBox("Take away Restaurant");
		TRtype=new JCheckBox("Typical Restaurant");
	
		
		
		Etype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				if(e.getStateChange()==ItemEvent.SELECTED){
					if (cSelected==0){
				Ctype.setSelected(true);
					cSelected=1;
					}
					if (ssSelected==0){
				Stype.setSelected(true);
				ssSelected=1;
					}
					if (dSelected==0){
				Dtype.setSelected(true);
				dSelected=1;
					}
				
				
				typeName=typeName.concat("drink#cinema#site seeing#");
				}
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					
					Ctype.setSelected(false);
					Stype.setSelected(false);
					Dtype.setSelected(false);
					cSelected=0;
					dSelected=0;
					ssSelected=0;
					typeName=typeName.replace("drink#cinema#site seeing#", "");
					
				}      
				
					
		        	
		    }		
		});
		Ctype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("cinema#", "");
					System.out.println("Egine");
					cSelected=0;
					if(ssSelected==1 && dSelected==1){
						Etype.setSelected(false);
						Stype.setSelected(true);
						ssSelected=1;
						Dtype.setSelected(true);
						dSelected=1;
					}
					
					
				} 
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					cSelected=1;
					if (ssSelected==1 && dSelected==1)
						Etype.setSelected(true);
					typeName=typeName.concat("cinema#");
					
				}
				
		        	
		    }		
		});
		Stype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("site seeing#", "");
					ssSelected=0;
				if(cSelected==1 && dSelected==1){
					Etype.setSelected(false);
					Ctype.setSelected(true);
					cSelected=1;
					dSelected=1;
					Dtype.setSelected(true);
				}
				}
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					ssSelected=1;
					if (cSelected==1 && dSelected==1)
						Etype.setSelected(true);
					typeName=typeName.concat("site seeing#");
					
				}
					
					
				        	
		        	
		    }		
		});
		Dtype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED)
					if(ssSelected==1 && cSelected==1){
						Etype.setSelected(false);
						
						Stype.setSelected(true);
						Ctype.setSelected(true);
						ssSelected=1;
						cSelected=1;
					}
					typeName=typeName.replace("drink#", "");
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					dSelected=1;
					if (cSelected==1 && ssSelected==1)
						Etype.setSelected(true);
					typeName=typeName.concat("drink#");
					
				}
					
					
				      	
		        	
		    }		
		});
		Edtype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				if(e.getStateChange()==ItemEvent.SELECTED){
					if (uSelected==0){
				Utype.setSelected(true);
					uSelected=1;
					}
					if (lSelected==0){
				Ltype.setSelected(true);
				lSelected=1;
					}
				
				
				
				typeName=typeName.concat("library#university#");
				}
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					
					Ltype.setSelected(false);
					Utype.setSelected(false);
					
					lSelected=0;
					uSelected=0;
					
					typeName=typeName.replace("library#university#", "");
					
				}      
				
					
		        	
		    }		
		});
		Utype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("university#", "");
					System.out.println("Egine");
					uSelected=0;
					if(lSelected==1){
						Edtype.setSelected(false);
						Ltype.setSelected(true);
						lSelected=1;
						
					}
					
					
				} 
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					uSelected=1;
					if (lSelected==1)
						Edtype.setSelected(true);
					typeName=typeName.concat("university#");
					
				}
				
		        	
		    }		
		});
		Ltype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("library#", "");
					lSelected=0;
				if(uSelected==1){
					Edtype.setSelected(false);
					Utype.setSelected(true);
					uSelected=1;
					
				}
				}
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					lSelected=1;
					if (uSelected==1)
						Edtype.setSelected(true);
					typeName=typeName.concat("library#");
					
				}
					
					
				        	
		        	
		    }		
		});
		
		
		
		
		
		Ftype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				if(e.getStateChange()==ItemEvent.SELECTED){
					if (ffSelected==0){
				FFtype.setSelected(true);
					ffSelected=1;
					}
					if (taSelected==0){
				TAtype.setSelected(true);
				taSelected=1;
					}
					if (trSelected==0){
				TRtype.setSelected(true);
				trSelected=1;
					}
				
				
				typeName=typeName.concat("fast food restaurant#take away restaurant#typical restaurant#");
				}
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					
					FFtype.setSelected(false);
					TAtype.setSelected(false);
					TRtype.setSelected(false);
					ffSelected=0;
					taSelected=0;
					trSelected=0;
					typeName=typeName.replace("fast food restaurant#take away restaurant#typical restaurant#", "");
					
				}      
				
					
		        	
		    }		
		});
		FFtype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("fast food restaurant#", "");
					System.out.println("Egine");
					ffSelected=0;
					if(trSelected==1 && taSelected==1){
						Ftype.setSelected(false);
						TRtype.setSelected(true);
						trSelected=1;
						TAtype.setSelected(true);
						taSelected=1;
					}
					
					
				} 
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					ffSelected=1;
					if (taSelected==1 && trSelected==1)
						Ftype.setSelected(true);
					typeName=typeName.concat("fast food restaurant#");
					
				}
				
		        	
		    }		
		});
		TAtype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED){
					typeName=typeName.replace("take away restaurant#", "");
					taSelected=0;
				if(trSelected==1 && ffSelected==1){
					Ftype.setSelected(false);
					FFtype.setSelected(true);
					ffSelected=1;
					trSelected=1;
					TRtype.setSelected(true);
				}
				}
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					taSelected=1;
					if (ffSelected==1 && trSelected==1)
						Ftype.setSelected(true);
					typeName=typeName.concat("take away restaurant#");
					
				}
					
					
				        	
		        	
		    }		
		});
		TRtype.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {			
				
				
				if(e.getStateChange()==ItemEvent.DESELECTED)
					if(taSelected==1 && ffSelected==1){
						Ftype.setSelected(false);
						
						FFtype.setSelected(true);
						TAtype.setSelected(true);
						ffSelected=1;
						taSelected=1;
					}
					typeName=typeName.replace("typical restaurant#", "");
				if(e.getStateChange()==ItemEvent.SELECTED){
					
					trSelected=1;
					if (ffSelected==1 && taSelected==1)
						Ftype.setSelected(true);
					typeName=typeName.concat("typical restaurant#");
					
				}
					
					
				      	
		        	
		    }		
		});
	
		username.setSize(100, 20);
		username.setLocation(20, 50);			
		usrnm.setSize(120, 20);
		usrnm.setLocation(120, 50);		
		password.setSize(100, 20);
		password.setLocation(20, 80);
		pwd.setSize(120, 20);
		pwd.setLocation(120, 80);
		x.setSize(120,20);
		x.setLocation(260,50);
		y.setSize(120,20);
		y.setLocation(260,80);
		
		cx.setSize(100,20);
		cx.setLocation(370,50);	
		cy.setSize(100,20);
		cy.setLocation(370,80);
		
		dokimi=new JLabel("Please choose the type of POI");
		dokimi.setSize(180, 20);
		dokimi.setLocation(20, 110);
		Etype.setSize(120,20);
		Etype.setLocation(20,130);
		Dtype.setSize(100,20);
		Dtype.setLocation(140,130);
		Stype.setSize(100,20);
		Stype.setLocation(300,130);
		Ctype.setSize(100,20);
		Ctype.setLocation(470,130);
		
		Edtype.setSize(120,20);
		Edtype.setLocation(20,150);
		Ltype.setSize(100,20);
		Ltype.setLocation(140,150);
		Utype.setSize(120,20);
		Utype.setLocation(300,150);
		
		Ftype.setSize(100,20);
		Ftype.setLocation(20,170);
		FFtype.setSize(160,20);
		FFtype.setLocation(140,170);
		TAtype.setSize(170,20);
		TAtype.setLocation(300,170);
		TRtype.setSize(170,20);
		TRtype.setLocation(470,170);
		
		
		get.setSize(100,30);
		get.setLocation(490,210);
		setProps();
		p1.add(username);
		p1.add(usrnm);
		p1.add(password);
		p1.add(pwd);
		p1.add(x);
		p1.add(cx);
		p1.add(y);
		p1.add(cy);
		p1.add(home);
		p1.add(dokimi);
		p1.add(Etype);	
		p1.add(Ctype);	
		p1.add(Dtype);	
		p1.add(Stype);
		p1.add(Utype);
		p1.add(Edtype);
		p1.add(Ltype);
		p1.add(Ftype);
		p1.add(FFtype);
		p1.add(TAtype);
		p1.add(TRtype);
		p1.add(get);
		p1.revalidate();	
		p1.repaint();
		mainframe.add(p1);
		mainframe.revalidate();
		mainframe.repaint();
		
		
	}
	
		
	public void setMonitorData(){
			
			p1.removeAll();
			p1.revalidate();
			p1.repaint();
			
			
			mainframe.add(p1);
			mainframe.revalidate();
			mainframe.repaint();
			
			type= new JLabel("Type :");
			name= new JLabel("Name :");
			typename = "";
			String[] stypes = { "Drink", "Cinema", "Site seeing", "Library", "University", "Fast food Restaurant", "Take away Restaurant", "Typical Restaurant" };
					
			typesSet = new JComboBox(stypes);
			typesSet.setSelectedIndex(0);		
			typesSet.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {				
					JComboBox combo = (JComboBox)arg0.getSource();
			        typename = (String)combo.getSelectedItem();
				}
			});		
	
			nm=new JTextField(25);
			
			
			username.setSize(100, 20);
			username.setLocation(20, 50);			
			usrnm.setSize(150, 20);
			usrnm.setLocation(200, 50);		
			password.setSize(150, 20);
			password.setLocation(20, 80);
			pwd.setSize(150, 20);
			pwd.setLocation(200, 80);
			x.setSize(150,20);
			x.setLocation(20,110);
			cx1.setSize(150,20);
			cx1.setLocation(200,110);
			
			y.setSize(150,20);
			y.setLocation(20,140);
			cy1.setSize(150,20);
			cy1.setLocation(200,140);
			type.setSize(150,20);
			type.setLocation(20,170);
			typesSet.setSize(150,20);
			typesSet.setLocation(200,170);
			name.setSize(150,20);
			name.setLocation(20,200);
			nm.setSize(150,20);
			nm.setLocation(200,200);
			set.setSize(100,30);
			set.setLocation(200,225);
			setProps();
			p1.add(home);
			p1.add(username);
			p1.add(usrnm);
			p1.add(password);
			p1.add(pwd);
			p1.add(x);
			p1.add(cx1);
			p1.add(y);
			p1.add(cy1);
			p1.add(typesSet);
			p1.add(type);
			p1.add(name);
			p1.add(nm);
			p1.add(set);
			p1.revalidate();
			p1.repaint();
			mainframe.add(p1);		
			mainframe.revalidate();
			mainframe.repaint();		
		}
	
	public void btnActions(){
		
		
		home.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {				
	
				login();
				
			}					 
		 });
		
		refr.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {				

				refreshMethod();
				
			}					 
		 });
			 
		registration.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				registration();
				
			}					 
		 });	 
		
		
		setMonitorData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				setMonitorData();
				
			}					 
		 });
		getMapData.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {				
				getMapData();	
			
			}
		});	
	 

		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			       	
				String username=usrnm.getText();
				String password=pwd.getText();
				String confirmPass=cpwd.getText();
				
				
				String rs="";
								
				if (username.equals("") || password.equals("") || confirmPass.equals(""))
					JOptionPane.showMessageDialog(null,"Please fill the gaps");				
				
				else {
				
					rs= rs.concat(username+ "#" +password+"#" +confirmPass);
					String epistrofi;
					try {
						epistrofi = server.registeruser(rs);
					
						if (epistrofi.equals("Successful Registration")){
							Properties prop = new Properties();
							FileOutputStream fis= null;
							 
					    	try {
					    	fis= new FileOutputStream("fields.properties");
					        //load a properties file
					    	prop.setProperty("username", username);
							prop.setProperty("password", password);
					    	prop.store(fis, null);
					    	
					    		
					    	} catch (Exception ex) {
					    		ex.printStackTrace();
					        } finally{
					        	try{
					        	fis.close();
					        	}catch (IOException ioex){
					        		ioex.printStackTrace();
					        	}
					        }						
							
							JOptionPane.showMessageDialog(null,"Successful Registration");
							flag=1;
							
							login();							
							
						}
						else if(epistrofi.equals("Password mismatch!"))
							JOptionPane.showMessageDialog(null,"Registration failed!Password mismatch!","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("Username is already in use!"))
							JOptionPane.showMessageDialog(null,"Username already exists!","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("User information incorrect!"))
							JOptionPane.showMessageDialog(null,"User information incorrect!","Error",JOptionPane.ERROR_MESSAGE);
					
						} catch (RemoteException e) {
						
							e.printStackTrace();
						}						
				}						
			}
		});

		
		set.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			      	
				String username=usrnm.getText();
				String password=pwd.getText();
				String x=cx1.getText();
				String y=cy1.getText();
				String name=nm.getText();				
				String rs="";
				String st="";
								
				if (username.equals("") || password.equals("") || x.equals("")||y.equals("")||typename.equals("")||name.equals("") )
					if (typename.equals(""))
						JOptionPane.showMessageDialog(null,"Please choose type");
					else
						JOptionPane.showMessageDialog(null,"Please fill the gaps");				
				
				else {
				
					rs= rs.concat(username+ "#" +password);
					st=st.concat(x+"#"+y+"#"+typename+"#"+name);
					String epistrofi;
					try {
						epistrofi = server.setMonitorData(rs, st);
					
						if (epistrofi.equals("swsta")){
							
							JOptionPane.showMessageDialog(null,"Poi was successfully inserted!");
							p1.removeAll();
							p1.revalidate();
							p1.repaint();
													
							login();
							//Edw tora erxontai oi setmonitordata kai getmapdata
							
						}
						else if(epistrofi.equals("Duplicate entry for coordinates x,y!"))
							JOptionPane.showMessageDialog(null,"Duplicate entry for coordinates x,y!","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("POI can't be inserted because it already exists in range R"))
							JOptionPane.showMessageDialog(null,"POI can't be inserted because it already exists in range R","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("The POI has already been set !"))
							JOptionPane.showMessageDialog(null,"The POI has already been set !","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("Username not Found"))
							JOptionPane.showMessageDialog(null,"Username not Found!","Error",JOptionPane.ERROR_MESSAGE);
						else 
							JOptionPane.showMessageDialog(null,"Wrong Password!","Error",JOptionPane.ERROR_MESSAGE);
						
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
		get.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
				p2.removeAll();
				p2.revalidate();
				p2.repaint();
		       	String username=usrnm.getText();
				String password=pwd.getText();
				String x=cx.getText();
				String y=cy.getText();
				int filterflag = 0;
				x1=x;
				y1=y;
				String rs="";
				String st="";
				JScrollPane scrollPane = new JScrollPane();	
				if (timer!=null) {
					timer.cancel();
				}
				timer = new Timer();
				timer.schedule(new refresh(), xronos * 1000, xronos * 1000);
				
				if (username.equals("") || password.equals("") || x.equals("")||y.equals(""))
					JOptionPane.showMessageDialog(null,"Please fill the gaps");				
				
				else {
					rs= rs.concat(username+ "#" +password);
					st=st.concat(x+"#"+y);	
					String[] columnNames = {"PoiName","TypeName", "X","Y"};     
			       	String epistrofi;
					try {
						epistrofi = server.getMapData(rs, st);
							       	
				       	if (epistrofi.equals("No POIS found in this region!"))
				       		JOptionPane.showMessageDialog(null,"No POIS found in this region!");
				       	else if(epistrofi.equals("Username not Found"))
							JOptionPane.showMessageDialog(null,"Username not Found!","Error",JOptionPane.ERROR_MESSAGE);
						else if(epistrofi.equals("Wrong Password!"))
							JOptionPane.showMessageDialog(null,"Wrong Password!","Error",JOptionPane.ERROR_MESSAGE);
				       	else {

				       		 results.setVisible(true);
				       					       		
				       		 String[] parts = epistrofi.split("\\$");
					         String[][] data = new String[parts.length][];
					         int r = 0;
					         for (String row : parts) {
					             data[r] = row.split("#");
					             r++;
					         }	
					         if (typeName.equals("")){
					        	 filterflag=1;
					        	 typeName=typeName.concat("drink#cinema#site seeing#library#university#fast food restaurant#take away restaurant#typical restaurant");
					         }
					         filter = typeName;
					         String [] typeParts= typeName.split("#");
					         
					         int sum = 0;
					         String str = epistrofi;
					         for (int i=0; i<=(typeParts.length - 1 ); i++){
					        	String subStr = typeParts[i];
					        	sum = sum + (str.length() - str.replace(subStr, "").length()) / subStr.length();
					        	 
					         }
					        
					         String[][] table2 = new String[sum][4];
					         int j =0 ;
					         for (int i=0; i<=(typeParts.length -1); i++){
					        	 for(int k=0; k<= r-1; k++){
					        		 if (data[k][1].equals((typeParts[i]))){
					        			 
					        			 for (int n=0; n<= 3; n++)
					        				 table2[j][n]=data[k][n];
					        		
					        			 j++;					        			 
					        		 }  					        		 
					        	 }					        	 
					         }
					         
					         JTable table = new JTable(table2, columnNames);
					         table.setEnabled(false);
					       	 scrollPane = new JScrollPane(table);	
					       	 scrollPane.setPreferredSize(new Dimension(450, 250));
					         scrollPane.setLocation(10, 10);
					         table.setFillsViewportHeight(false);
					     	 p2.add(refr);
					         p2.add(scrollPane);
					         p2.revalidate();
					         p2.repaint();
					         results.add(p2);
					         results.revalidate();
					         results.repaint();
					         if (filterflag==1){
					        	 filterflag=0;
					        	 typeName="";	 
					         }
					         
				       	}
			       	
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}	 
				}	
				
			}
		});
			
			
	}	
	
	public void refreshMethod() {
			
			String username=usrnm.getText();
			String password=pwd.getText();
			String x=x1;	
			String y=y1;
			String rs="";
			String st="";
			int filterflag=0;
			JScrollPane scrollPane = new JScrollPane();	
			
			if ((!username.equals("") && !password.equals("") && !x.equals("") && !y.equals(""))){
				p2.removeAll();
		        p2.revalidate();
		        p2.repaint();
				rs= rs.concat(username+ "#" +password);
				st=st.concat(x+"#"+y);	
				String[] columnNames = {"PoiName","TypeName", "X","Y"};     
		       	String epistrofi;
		       	try {
					epistrofi = server.getMapData(rs, st);
						       	
			       	if (epistrofi.equals("No POIS found in this region!"))
			       		JOptionPane.showMessageDialog(null,"No POIS found in this region!");
			       	else if(epistrofi.equals("Username not Found"))
						JOptionPane.showMessageDialog(null,"Username not Found!","Error",JOptionPane.ERROR_MESSAGE);
					else if(epistrofi.equals("Wrong Password!"))
						JOptionPane.showMessageDialog(null,"Wrong Password!","Error",JOptionPane.ERROR_MESSAGE);
			       	else {
			       					       		
			       		String[] parts = epistrofi.split("\\$");
				         String[][] data = new String[parts.length][];
				         int r = 0;
				         for (String row : parts) {
				             data[r] = row.split("#");
				             r++;
				         }	
				        
				         String [] typeParts= filter.split("#");
				         
				         int sum = 0;  
				         String str = epistrofi;
				         for (int i=0; i<=(typeParts.length - 1 ); i++){
				        	String subStr = typeParts[i];
				        	sum = sum + (str.length() - str.replace(subStr, "").length()) / subStr.length();
				        	 
				         }
				        
				         String[][] table2 = new String[sum][4];
				         int j =0 ;
				         for (int i=0; i<=(typeParts.length -1); i++){
				        	 for(int k=0; k<= r-1; k++){
				        		 if (data[k][1].equals((typeParts[i]))){				        			 
				        			 for (int n=0; n<= 3; n++)
				        				 table2[j][n]=data[k][n];

				        			 j++;				        			 
				        		 } 			        		 
				        	 }					        	 
				         }
				         
				         JTable table = new JTable(table2, columnNames);
				         table.setEnabled(false);
				       	 scrollPane = new JScrollPane(table);	
				       	 scrollPane.setPreferredSize(new Dimension(450, 250));
				         scrollPane.setLocation(10, 10);
				         table.setFillsViewportHeight(false);
				     	 p2.add(refr);
				         p2.add(scrollPane);
				         p2.revalidate();
				         p2.repaint();
				         results.add(p2);
				         results.revalidate();
				         results.repaint();

				        	 
			       	}
		       	
				} catch (RemoteException e) {
					
					e.printStackTrace();
				}	 
			}
			
		}
	
		
		class refresh extends TimerTask {
		    public void run() {		    	
		    	refreshMethod();    				    	
		    }		    	
		 }
		
		class MyWindowListener implements WindowListener {
			   public void windowActivated(WindowEvent e) {}
			   public void windowClosed(WindowEvent e) {}
			   public void windowClosing(WindowEvent e) {				   
				   timer.cancel();			     
			   }
			   public void windowDeactivated(WindowEvent e) {}
			   public void windowDeiconified(WindowEvent e) {}
			   public void windowIconified(WindowEvent e) {}
			   public void windowOpened(WindowEvent e) {}
			}
		
	
}

