import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class codingClubFeb3_2 {
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
            calTotals.add(sum);
            ArrayList<Integer> topThree = new  ArrayList<>(3);
            long max = calTotals.get(0);
            int maxIndex = 0;
            for (int i = 1; i < calTotals.size(); i++)
            {
                if (calTotals.get(i) > max && !topThree.contains(i))
                {
                    max = calTotals.get(i);
                    maxIndex = i;
                }
            }
            long nextMax = calTotals.get(0);
            int nextMaxIndex = 0;
            for (int i = 1; i < calTotals.size(); i++)
            {
                if(i == maxIndex)
                {
                    continue;
                }
                if (calTotals.get(i) > nextMax)
                {
                    nextMax = calTotals.get(i);
                    nextMaxIndex = i;
                }
            }
            long lastMax = calTotals.get(0);
            int lastMaxIndex = 0;
            for (int i = 1; i < calTotals.size(); i++)
            {
                if(i == maxIndex || i == nextMaxIndex)
                {
                    continue;
                }
                if (calTotals.get(i) > lastMax)
                {
                    lastMax = calTotals.get(i);
                    lastMaxIndex = i;
                }
            }
            topThree.add(maxIndex);
            topThree.add(nextMaxIndex);
            topThree.add(lastMaxIndex);
            long threeSum = 0;
            for(Integer i : topThree)
            {
                System.out.println(i + " " + calTotals.get(i));
                threeSum += calTotals.get(i);
            }
            System.out.println(threeSum);
        }catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
