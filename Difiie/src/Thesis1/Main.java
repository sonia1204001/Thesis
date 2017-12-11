package Thesis1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main
{
    public static final int PORT2 = 4530;
    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1.Server\n2.Client");
        System.out.println("Enter your choice:");
        int choice2 = Integer.parseInt(br.readLine());
            if (choice2 == 1)
            {
                new Server();
            }
            else
            {
                new Client();
            }
    }
}
