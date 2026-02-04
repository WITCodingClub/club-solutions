import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class codingClubFeb3_1 {
    public static void main(String[] args)
    {
        try
        {
            Scanner sc = new Scanner(new File("realFeb3.txt"));
            ArrayList<String> calories = new  ArrayList<>();
            ArrayList<Long> calTotals =  new ArrayList<>();
            while(sc.hasNextLine())
            {
                calories.add(sc.nextLine());
            }
            long sum = 0;
            for(String s : calories)
            {
                if(s.equals(""))
                {
                    calTotals.add(sum);
                    sum = 0;
                    continue;
                }
                sum += Long.parseLong(s);
            }
            long max = calTotals.get(0);
            for(int i = 1; i < calTotals.size(); i++)
            {
                if(calTotals.get(i) > max)
                {
                    max = calTotals.get(i);
                }
            }
            System.out.println(max);
        }catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
