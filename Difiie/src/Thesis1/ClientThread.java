package Thesis1;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Scanner;
public class ClientThread extends Thread
{
    Socket socket;
    int id;
    Operation op;
    Random_num random_num;
    public static BigInteger p,g,A,divisor,sec;
	BigInteger a;
	int flag=0;
	int attack_value=0;
	BigInteger attack; 
	public static BigInteger on=new BigInteger("1");
	public static BigInteger zero=new BigInteger("0");
	public static BigInteger two=new BigInteger("2");
	public static BigInteger five=new BigInteger("5");
	public static BigInteger ten=new BigInteger("10");
	static BigInteger sum1=zero;
	int i;
    public static BigInteger secret[]=new BigInteger[1000000];
    public static BigInteger secret1[]=new BigInteger[100000];
    long m;
    public ClientThread(Socket clientSocket, int id, BigInteger p, BigInteger g,BigInteger divisor)
    {
        this.socket = clientSocket;
        this.id = id;
        this.p=p;
        this.g=g;
        this.divisor=divisor;
        System.out.println("Client" + id + " is connected");
        
        if(id==2)
        {
        	System.out.println("\n"+"Client1 wants to communicate with client2.....");
        	System.out.println("generated divisor for this session is: "+divisor+"\n");
        	
        }
        
    }
  
    @Override
    public void run()
    {
        try
        {
        	
            op = new Operation(socket);
        }
        catch (Exception e)
        {
            System.err.println(e);
            return;
        }
        while (true)
        {
            try
            {
            	
                String cmd = op.receiveMsg();
                if (cmd.equals("END"))
                {
                    System.out.println("Client " + id + "is disconnected");
                    
                    socket.close();
                    return;
                }
                else
                {	 
                	int receiverID = Integer.parseInt(cmd);
                    Socket receiverSocket = Server.clientSockets[receiverID];
                    if (receiverSocket != null)
                    {	 
                    	//secret key from client 
                    	attack=new BigInteger(op.receiveMsg());
                    a=new BigInteger(op.receiveMsg());
                    //i=new BigInteger(op.receiveMsg());
        		  BigInteger KA=g.modPow(a, p);
        		  secret1[id]=a;
        		  secret[receiverID]=KA;
        		  
        		  ///////////////////////////////////////////////////////////////////////////////////////////////
                  ///////////////////////  CRC  ////////////////////////////////////////////////////////////
        		  String div=divisor.toString(2);
        		  String div1=div;
                  String s_ka=KA.toString(2);
                BigInteger b[]=new BigInteger[div.length()];
              	BigInteger data[]=new BigInteger[s_ka.length()+div.length()-1];
              	for(long i=0;i<data.length;i++) data[(int) i]=zero;
              	for(long i=0;i<b.length;i++) b[(int) i]=zero;
              	for(long i=0;i<s_ka.length();i++)
              	{
              		long j=Character.digit(s_ka.charAt((int) i), 2);
              		data[(int)i]=BigInteger.valueOf(j);
              		
              		//a[i]=BigInteger.valueOf(j);
              	}
              	for(long i=0;i<div.length();i++)
              	{
              		long j=Character.digit(div.charAt((int) i),2);
						b[(int) i]=BigInteger.valueOf(j);
              	}
              	for(long i=0;i<s_ka.length();i++)
              	{
              		if(data[(int) i].equals(on))
              		{
              			for(long j=0;j<div.length();j++)
              			{
              				data[(int) (i+j)]=data[(int) (i+j)].xor(b[(int) j]);
              			}
              		}
              	}
              	long o=data.length-1;
              	
              	for(long i=0;i<s_ka.length();i++)
              	{
              		long j=Character.digit(s_ka.charAt((int) i), 2);
              		data[(int) i]=BigInteger.valueOf(j);
              
              	}
              	BigInteger sum=zero;
              	for(long i=0;i<data.length;i++)
            	{
            		BigInteger mul=data[(int) i].multiply(two.pow((int) o));
            	sum=sum.add(mul);
            	o--;
            	}
              	op.sendMsg("generator polynomial from server="+divisor);
              	op.sendMsg("p= "+p+ "      g= "+g);
              	op.sendMsg("(g^secret)mod p= "+g+"^"+a+" mod "+p+"= "+KA);
				op.sendMsg("CRC of public key sent= " +sum);
              	
				if(attack.equals(on))
				{
              	new Operation(receiverSocket).sendMsg("From client " + id + ": (p,g,crc_PK)=(" +p+","+g+","+ sum+")");
              	
              	//System.out.println("From client "+id+", Send to client " +receiverID+ ": (p,g,crc_KA,KA)=(" +p+","+g+","+ sum+","+KA+")");
				}
				else
					
				{
					
					 BigInteger a1 = new BigInteger(5, new SecureRandom()).add(five);
					 BigInteger KA_a=g.modPow(a1, p);
					 //////////////////////////////// attacker's CRC //////////////////////////////////
					 
					 //String div_a="1011";
					 BigInteger divisor_a= new BigInteger(8, new SecureRandom()).add(five);
					  String div_a=divisor_a.toString(2);
	        		  String div1_a=div_a;
	                  String s_ka_a=KA_a.toString(2);
	                BigInteger b_a[]=new BigInteger[div_a.length()];
	              	BigInteger data_a[]=new BigInteger[s_ka_a.length()+div_a.length()-1];
	              	for(long i=0;i<data_a.length;i++) data_a[(int) i]=zero;
	              	for(long i=0;i<b_a.length;i++) b_a[(int) i]=zero;
	              	for(long i=0;i<s_ka_a.length();i++)
	              	{
	              		long j=Character.digit(s_ka_a.charAt((int) i), 2);
	              		data_a[(int) i]=BigInteger.valueOf(j);
	              		
	              		//a[i]=BigInteger.valueOf(j);
	              	}
	              	for(long i=0;i<div_a.length();i++)
	              	{
	              		long j=Character.digit(div_a.charAt((int) i),2);
							b_a[(int) i]=BigInteger.valueOf(j);
	              	}
	              	for(long i=0;i<s_ka_a.length();i++)
	              	{
	              		if(data_a[(int) i].equals(on))
	              		{
	              			for(long j=0;j<div_a.length();j++)
	              			{
	              				data_a[(int) (i+j)]=data_a[(int) (i+j)].xor(b_a[(int) j]);
	              			}
	              		}
	              	}
	              	long o_a=data_a.length-1;
	              	
	              	for(long i=0;i<s_ka_a.length();i++)
	              	{
	              		long j=Character.digit(s_ka_a.charAt((int) i), 2);
	              		data_a[(int) i]=BigInteger.valueOf(j);
	              
	              	}
	              	BigInteger sum_a=zero;
	              	for(long i=0;i<data_a.length;i++)
	            	{
	            		BigInteger mul_a=data_a[(int) i].multiply(two.pow((int) o_a));
	            	sum_a=sum_a.add(mul_a);
	            	o_a--;
	            	}
	              	 sum=sum_a;
	              	random_num=new Random_num(p,g,KA_a,receiverID,a1,sum_a,divisor_a);
	              	 
					 ///////////////////////////////////////////////////////////////////////////
					 new Operation(receiverSocket).sendMsg("From client " + id + ": (p,g,crc_KA)=(" +p+","+g+","+ sum_a+")");
		              	//System.out.println("From client "+id+", Send to client " +receiverID+ ": (p,g,crc_KA,KA)=(" +p+","+g+","+ sum1+","+KA1+")");type name = new type();
				}
                 op.sendMsg("Message sent"+"\n"+"\n");
                        
/////////////////////////////////////Check validity//////////////////////
			String string3=sum.toString(2);
			BigInteger b1[]=new BigInteger[string3.length()];
			BigInteger data1[]=new BigInteger[string3.length()+div1.length()-1];
			BigInteger data3[]=new BigInteger[string3.length()+div1.length()-1];
			for(long i=0;i<data1.length;i++) data1[(int) i]=zero;
			for(long i=0;i<data3.length;i++) data3[(int) i]=zero;
			for(long i=0;i<b1.length;i++) b1[(int) i]=zero;
			for(long i=0;i<string3.length();i++)
			{
				long j=Character.digit(string3.charAt((int) i), 2);
				data1[(int) i]=BigInteger.valueOf(j);
				data3[(int) i]=BigInteger.valueOf(j);
				
			}
			for(long i=0;i<div1.length();i++)
			{
				long j=Character.digit(div1.charAt((int) i),2);
				b1[(int) i]=BigInteger.valueOf(j);
			}
			for(long i=0;i<string3.length();i++)
			{
				if(data1[(int) i].equals(on))
				{
					for(long j=0;j<div1.length();j++)
					{
						data1[(int) (i+j)]=data1[(int) (i+j)].xor(b1[(int) j]);
					}
				}
			}
			boolean valid=true;
			for(long i=0;i<data1.length;i++)
			{
				if(data1[(int) i].equals(on))
				{
					valid=false;
					break;
				}
			}
			
			if(valid==true)
			{
				new Operation(receiverSocket).sendMsg("Key is valid... Now after calculating reverse CRC ");
///////////////////////////////////////retrieve main message//////////////////////////
              	
           long lm=div1.length()-1;
           m=data.length-lm-1;
         sum1=zero;
           for(long i=0;i<data.length-lm;i++)
       	{
        	   
       	BigInteger mul2=data[(int) i].multiply(two.pow((int) m));
       	sum1=sum1.add(mul2);
       	m--;
       	}
         new Operation(receiverSocket).sendMsg("received public key is="+sum1);
         
		  
///////////////////////////////////////////////////////////////////////////////////////////
			}
			else
			{
			new Operation(receiverSocket).sendMsg("key is courrupted.Request for another key....");
			attack_value=1;
			
			}
			
			if(attack_value==1)
			{
				System.out.println("There is an attack informed by the client"+receiverID+"....");
			}
			else
			{
			System.out.println("Do u want to end your conversation? enter 1 ");
  		  Scanner in=new Scanner(System.in);
  		  i=in.nextInt();
  		  if(i==1)
            {
         	   for(int j=1;j<3;j++)
         	   {
         		   Socket receiver = Server.clientSockets[j];
         		   sec=secret[j].modPow(secret1[j], p);
         		   new Operation(receiver).sendMsg("At the end of the conversation calculated shared secret key is = "+"" + sec);
         	   }
            }
  		  else
  		  {
  			  System.out.println("ok you can continue......");
  		  } 
			}
			//////////////////////////////////////////////////////////////////////
                    }
                   
                    
                    else
                    {
                        op.sendMsg("Receiver is not connected");
                    }
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
    }
}

