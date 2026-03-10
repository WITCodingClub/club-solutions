package _2021.day10;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day10
{
	public static ArrayList<String> chunks = new ArrayList<String>();
	public static int errorScore = 0;
	public static final char[] OPEN_CHARS = {'(', '[', '{', '<'};
	public static final char[] CLOSE_CHARS = {')', ']', '}', '>'};
	public static final int[] SCORES = {3, 57, 1197, 25137};
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/_2021/day10/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/_2021/day10/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				
				chunks.add(line);
			}
			
			for(String chunk : chunks)
			{
			    int illegalIdx = getIllegalCloseIndex(chunk);
			    if (illegalIdx >= 0)
			    {
			        errorScore += SCORES[illegalIdx];
			    }
			}
			
			System.out.println("The answer is " + errorScore);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static int getIllegalCloseIndex(String line)
	{
	    StringBuilder stack = new StringBuilder();
	    
	    for (int i = 0; i < line.length(); i++)
	    {
	        char c = line.charAt(i);
	        
	        if (isOpener(c))
	        {
	            stack.append(c);
	            continue;
	        }
	        
	        if (stack.isEmpty())
	        {
	            return indexOf(CLOSE_CHARS, c);
	        }
	        
	        char top = stack.charAt(stack.length() - 1);
	        char expected = matchingCloser(top);
	        
	        if (c != expected)
	        {
	            return indexOf(CLOSE_CHARS, c);
	        }
	        
	        stack.deleteCharAt(stack.length() - 1);
	    }
	    
	    return -1;
	}
	
	public static int indexOf(char[] array, char target)
	{
	    for (int i = 0; i < array.length; i++)
	    {
	        if (array[i] == target)
	        {
	            return i;
	        }
	    }
	    
	    return -1;
	}
	
	public static boolean isOpener(char c)
	{
	    for (char o : OPEN_CHARS) if (c == o) return true;
	    return false;
	}

	public static char matchingCloser(char opener)
	{
	    if (opener == '(') return ')';
	    if (opener == '[') return ']';
	    if (opener == '{') return '}';
	    return '>';  // '<'
	}
}
