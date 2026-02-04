package _2022.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

public class Day1
{
	private static ArrayList<ArrayList<Integer>> calCount = new ArrayList<ArrayList<Integer>>();
	private static int max = 0;

	public static void main(String[] args)
	{
		ArrayList<Integer> currentGroup = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/_2022/day01/input.txt")))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.trim().isEmpty())
				{
					if (!currentGroup.isEmpty())
					{
						calCount.add(currentGroup);
						currentGroup = new ArrayList<>();
					}
				}
				else
				{
					currentGroup.add(Integer.parseInt(line.trim()));
				}
			}

			if (!currentGroup.isEmpty())
			{
				calCount.add(currentGroup);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ArrayList<Integer> sums = new ArrayList<Integer>();

		for (ArrayList<Integer> elf : calCount)
		{
			int sum = 0;

			for (int i = 0; i < elf.size(); i++)
			{
				sum += elf.get(i);
			}

			sums.add(sum);
		}

		max = sums.get(0);

		for (int i = 1; i < sums.size(); i++)
		{
			if (max < sums.get(i))
			{
				max = sums.get(i);
			}
		}

		System.out.println("The answer is " + max);
	}
}
