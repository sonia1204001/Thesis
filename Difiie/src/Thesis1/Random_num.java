package Thesis1;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.math.BigInteger;
import java.net.Socket;
public class Random_num {
	
	public static BigInteger a,publickey; 
	Socket socket;
	Operation op;
	Client client;
	public static BigInteger num3=new BigInteger("2");
	 public static  BigInteger num2=new BigInteger("1");
	 public static  BigInteger zero=new BigInteger("0");
	 public static  BigInteger ten=new BigInteger("10");
	 public static BigInteger c[]=new BigInteger[100000];
	  public static BigInteger k=zero;
	  public Random_num(BigInteger p, BigInteger g, BigInteger kA1, int id, BigInteger a, BigInteger sum, BigInteger divisor_a) {
		  JFrame frame = new JFrame("Attacker's calculation");
		  frame.setBounds(100,100,300,300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JLabel label = new JLabel("<html>ATTACK!!!! ATTACK!!!<br><br>secret key for client"+id+"= "+a+"<br>"+"(g^secret) mod p= "+g+"^"+a+" mod "+p+"= "+kA1+"<br>"+"generated divisor value= "+divisor_a+"<br>"+"calculated CRC for Client"+id+"= "+sum+"</html>",SwingConstants.CENTER);
			
			label.setFont(new Font("Serif", Font.BOLD, 15));
			frame.getContentPane().add(label);
	        label.setForeground(Color.blue);
	        //frame.pack();
	        frame.setVisible(true);
	}
	
}