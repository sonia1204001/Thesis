package Thesis1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
public class Client
{
    public static final int PORT2 = 4530;
    Socket socket;
    Operation op;
    Random_num random_num;
    public static  BigInteger two=new BigInteger("2");
    public static  BigInteger five=new BigInteger("5");
    public static  BigInteger one=new BigInteger("1");
    public static  BigInteger zero=new BigInteger("0");
    public static BigInteger a;
    public static BigInteger array[]=new BigInteger[10000000];
    BigInteger i=zero;
    public Client()
    {
        try
        {
            String host = "localhost";
            socket = new Socket(host, PORT2);
            op = new Operation(socket);
            //random_num=new Random_num();
            System.out.println("Connected with " + host + ":" + PORT2);
            
            //BigInteger a = new BigInteger(4, new SecureRandom()).add(two);
        }
        catch (Exception e)
        {
            System.err.println(e);
            return;
        }
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                {
                    try
                    
                    {
                    	//client window te jekono kichu ekhan theke print hobe
                    	String a = op.receiveMsg();
                        System.out.println(a);
                    	
                    	
                        
                       
                    }
                    catch (Exception e)
                    {
                        System.err.println(e);
                    }
                }
            }
        });
        thread.start();
        System.out.println("1. Secure channel 2. Insecure Chennel");
        
        while (true)
        { 
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            try
            {
            	 int choice = Integer.parseInt(br.readLine()); 
            	 if (choice == 1)
            	 {
            	   
                    System.out.print("Send to: client");
                    String receiver = br.readLine();
                    BigInteger a = new BigInteger(5, new SecureRandom()).add(five);
                    System.out.print("your secret key is: "+a);
                    System.out.println();
          		    op.sendMsg(receiver);
          		    BigInteger attack=one;
          		   // i=i.add(one);
                    op.sendMsg(attack.toString());
          		    op.sendMsg(a.toString());
          		    //op.sendMsg(i.toString());
          		  //System.out.print("i="+i);
                }
            	 else {
            		 System.out.print("Send to: client");
                     String receiver = br.readLine();
                    BigInteger a = new BigInteger(5, new SecureRandom()).add(two);
                     System.out.print("your secret key is: "+a);
                     System.out.println();
           		    op.sendMsg(receiver);
           		 BigInteger attack=two;
                 op.sendMsg(attack.toString());
           		    op.sendMsg(a.toString());
				}
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
    }
}

