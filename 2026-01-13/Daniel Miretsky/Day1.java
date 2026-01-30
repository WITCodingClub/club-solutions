package _2023.day01;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day1
{
	static ArrayList<String> data = new ArrayList<String>();
	static int sum = 0;
	
	public static void main(String[] args)
	{
		//try(Scanner input = new Scanner(new File("input.txt")))
		try(Scanner input = new Scanner(new File("./src/main/java/_2023/day01/input.txt")))
		{
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				
				data.add(line);
			}
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
		
		for(int i = 0; i < data.size(); i++)
		{
			char[] chars = data.get(i).toCharArray();
			
			char firstDigit = '\0';
		    char lastDigit = '\0';
		    
		    // Find first digit
		    for(int j = 0; j < chars.length; j++)
		    {
		        if(Character.isDigit(chars[j]))
		        {
		            firstDigit = chars[j];
		            break;
		        }
		    }
		    
		    // Find last digit
		    for(int j = chars.length - 1; j >= 0; j--)
		    {
		        if(Character.isDigit(chars[j]))
		        {
		            lastDigit = chars[j];
		            break;
		        }
		    }
		    
		    // Combine and parse
		    if(firstDigit != '\0' && lastDigit != '\0')
		    {
		        String number = "" + firstDigit + lastDigit;
		        sum += Integer.parseInt(number);
		    }
		}
		
		System.out.println("The answer is " + sum);
	}
}
