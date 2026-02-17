package _2023.day08;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day8
{
	public static ArrayList<String> instructions = new ArrayList<String>();
	public static ArrayList<String> nodes = new ArrayList<String>();
	public static ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
	public static int count = 0;

	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/_2023/day08/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/_2023/day08/input_test.txt")))
		{
			String[] instr = input.nextLine().split("");

			for (int i = 0; i < instr.length; i++)
			{
				instructions.add(instr[i]);
			}

			input.nextLine();

			while (input.hasNextLine())
			{
				String[] line = input.nextLine().split(" = ");

				nodes.add(line[0]);

				String[] coord = line[1].split("[(),\\s]+");
				String x = coord[1];
				String y = coord[2];

				coords.add(new Coordinate(x, y));
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}

		String currentNode = "AAA";
		int instructionIndex = 0;

		while (!currentNode.equals("ZZZ"))
		{
			// Get current instruction
			String instruction = instructions.get(instructionIndex);

			// Find the index of current node
			int nodeIndex = nodes.indexOf(currentNode);

			// Move to next node based on instruction
			if (instruction.equals("L"))
			{
				currentNode = coords.get(nodeIndex).getX();
			}
			else if (instruction.equals("R"))
			{
				currentNode = coords.get(nodeIndex).getY();
			}

			count++;

			// Move to next instruction (loop back if needed)
			instructionIndex = (instructionIndex + 1) % instructions.size();
		}

		System.out.println("The answer is " + count);
	}
}