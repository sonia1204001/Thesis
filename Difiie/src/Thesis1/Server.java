package Thesis1;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
public class Server
{
	public static BigInteger p,g,a,A,divisor; 
    public static BigInteger num3=new BigInteger("2");
	 public static  BigInteger num2=new BigInteger("1");
	 public static  BigInteger zero=new BigInteger("0");
	 public static  BigInteger five=new BigInteger("5");
	 public static  BigInteger ten=new BigInteger("10");
	 public static BigInteger c[]=new BigInteger[100000];
	  public static BigInteger k=zero;
	  public static BigInteger two=new BigInteger("2");
	  
    public static final int PORT2 = 4530;
    public static Socket[] clientSockets = new Socket[100];
    public Server()
    {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try
        {
            serverSocket = new ServerSocket(PORT2);
            System.out.println("Server is running on port " +PORT2);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return;
        }
        int id = 0;
        while (true)
        {
            try
            {
            	while(true)
      		  {
      			  BigInteger random1 = new BigInteger(15, new SecureRandom()).add(ten);
      			  //System.out.println(random1);
      			  BigInteger m=random1.divide(num3);
      			  int count=0;
      			    for(BigInteger num=num3;m.compareTo(num)>=0;num=num.add(num2))
      			    {
      			    	BigInteger rnd=random1.mod(num);
      			    	if(rnd.compareTo(zero)==0)
      			    	{
      			    		count=1;
      			    		break;
      			    	}
      			    }
      			    if (count==0)
      			    {
      			    	 p=random1;
      			    //System.out.println("prime random p="+p);
      			    break;
      			    }
      		  }
      		  //primtive root of the prime number
      		  //System.out.println("primtive roots of "+p+" are:");
      		  for(BigInteger r=num3;p.compareTo(r)>=1;r=r.add(num2))
      		  {
      			  BigInteger b[] = new BigInteger[100000],p1=p.subtract(num3),i=zero;
      			  long flag=0;
      			  for(BigInteger x=zero;p1.compareTo(x)>=0;x=x.add(num2))
      			  {
      				
      				 BigInteger pr=r.modPow(x, p);
      				  for(BigInteger j=zero;j.compareTo(i)<0;j=j.add(num2))
      				  {
      					  if(pr.equals(b[j.intValue()]))
      					  {
      						 flag=1;
      						 break;
      					  }
      				  }
      					 
      					  if(flag==1)
      					  {
      						 break;
      					  }  
      					  else
      					  {
      						  b[i.intValue()]=pr;
      						  i=i.add(num2);
      					  }
      			  }
      			  if(flag==0)
      			  {
      				  BigInteger m=r.divide(num3);
      				  int count=0;
      				    for(BigInteger num=num3;m.compareTo(num)>=0;num=num.add(num2))
      				    {
      				    	BigInteger rnd=r.mod(num);
      				    	if(rnd.compareTo(zero)==0)
      				    	{
      				    		count=1;
      				    		break;
      				    	}
      				    }
      				    if (count==0)
      				    {
      				    	c[k.intValue()]=r;
      				    k=k.add(num2);
      				    
      				    }
      			  }
      		  }
      		  
      		  BigInteger l=k.subtract(num2);
      		  SecureRandom sRandom=new SecureRandom();
      		  BigInteger random2 = new BigInteger(3, new SecureRandom()).add(num2);
      		  g=c[random2.intValue()];
      		BigInteger sum=zero;
                socket = serverSocket.accept();
                id++;
                clientSockets[id] = socket;
                String string2="1101";
                int o1=string2.length()-1;
            	for(int i=0;i<string2.length();i++)
            	{
            		long l1=Character.digit(string2.charAt(i), 2);
            		BigInteger mul=BigInteger.valueOf(l1).multiply(two.pow(o1));
            	sum=sum.add(mul);
            	o1--;
            		
            	}
            	//System.out.println("sum="+sum);
            	BigInteger divisor= new BigInteger(8, new SecureRandom()).add(ten);
                new ClientThread(socket, id ,p,g,divisor).start();
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
        }
    }
}
