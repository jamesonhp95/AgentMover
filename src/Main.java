/*
Author: Jameson Price
The main user interface for adjusting the AgentMover.
 */
import java.util.Scanner;
import s18cs350task3.AgentMover;

public class Main
{
    public static void main(String ... args)
    {
        Scanner kb = new Scanner(System.in);
        int input = 0;
        double change, x, y, z, distance;
        boolean bool;
        AgentMover myMover = new AgentMover(0,0,0,0,10,25,150,10,5,25);
        while(input != 6)
        {
            System.out.println("1) Change altitude target (double)");
            System.out.println("2) Change speed target (double)");
            System.out.println("3) isProximate(x, y, z, distance)");
            System.out.println("4) Update");
            System.out.println("5) Change Azimuth Target (double)");
            System.out.println("6) Exit");
            input = Integer.parseInt(kb.nextLine());
            switch (input)
            {
                case 1: System.out.print("Enter a new altitude target: ");
                        try
                        {
                            change = Double.parseDouble(kb.nextLine());
                            myMover.setAltitudeTarget(change);
                        }
                        catch(Exception e)
                        {
                            System.out.println("Not a valid double");
                        }
                        break;
                case 2: System.out.print("Enter a new speed target: ");
                        try
                        {
                            change = Double.parseDouble(kb.nextLine());
                            myMover.setSpeedTarget(change);
                        }
                        catch(Exception e)
                        {
                            System.out.println("Not a valid double");
                        }
                        break;
                case 3: System.out.print("Enter an x coordinate:");
                        try
                        {
                            x = Double.parseDouble(kb.nextLine());
                            System.out.print("Enter a y coordinate:");
                            y = Double.parseDouble(kb.nextLine());
                            System.out.print("Enter a z coordinate:");
                            z = Double.parseDouble(kb.nextLine());
                            System.out.print("Enter a distance to check:");
                            distance = Double.parseDouble(kb.nextLine());
                            System.out.println(myMover.isProximate(x, y, z, distance));
                        }
                        catch(Exception e)
                        {
                            System.out.println("Invalid double entry");
                        }
                        break;
                case 4: myMover.update_();
                        break;
                case 5: System.out.print("Enter a new Azimuth target: ");
                        try
                        {
                            change = Double.parseDouble(kb.nextLine());
                            System.out.print("Enter a if clockwise (true or false)");
                            bool = Boolean.parseBoolean(kb.nextLine());
                            myMover.setAzimuthTarget(change, bool);
                        }
                        catch(Exception e)
                        {
                            System.out.println("Invalid double entry");
                        }
                case 6: break;
                default: System.out.println("Invalid menu option.");

            }
            System.out.println(myMover);
        }
    }
}
