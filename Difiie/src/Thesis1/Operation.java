package Thesis1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
public class Operation
{
    DataInputStream din;
    DataOutputStream dout;
    Socket socket;
    public Operation(Socket socket) throws Exception
    {
        this.socket = socket;
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
    }
    public String receiveMsg() throws Exception
    {
        String msg = din.readUTF();
        
        return msg;
    }
    public void sendMsg(String msg) throws Exception
    {
        dout.writeUTF(msg);
    }
}
