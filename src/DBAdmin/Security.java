package DBAdmin;

import java.util.ArrayList;
import java.util.Scanner;

public class Security {

    ArrayList<String[]> userPass;

    public Security(ArrayList<String[]> givenUserPass){
        userPass = givenUserPass;
    }

    public int securityCheckpoint(){
        Scanner sc = new Scanner(System.in);
        boolean correctUser = false;
        int i = 0;
        while (!correctUser && i < 3){
            System.out.println("Enter username:");
            String input = sc.nextLine();
            if (input.equals(userPass.get(0)[0])){
                correctUser = true;
            }else{
                System.out.println("Username not valid. "+(2-i)+" attempts remaining...");
                i++;
            }
        }
        if(correctUser){
            boolean correctPass = false;
            int j = 0;
            while (!correctPass && j < 3){
                System.out.println("\nEnter password:");
                String input = sc.nextLine();
                if (input.equals(userPass.get(0)[1])){
                    //correctPass = true;
                    return 0;
                }else{
                    System.out.println("Username not valid. "+(2-j)+" attempts remaining...");
                    j++;
                }
            }
        }
        return 1;

    }

}
