package com.github.griffty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Utility program demonstrating several ways to parse an input file of integer groups and
 * compute various aggregate results (maximum group sum, sum of top three group sums).
 *
 * <p>Input format expected by the methods:
 * - The input file contains integer values separated into groups by blank lines.
 * - Each non-blank line contains a single integer to be added to the current group's sum.
 *
 * <p>The class intentionally contains multiple implementations of the same computations to
 * illustrate trade-offs between readability, streaming APIs, and performance.
 */
public class Main {
    private static final File INPUT_FILE = new File("2026-02-03/prompt/input.txt");

    /**
     * Driver that invokes each approach in turn.
     *
     * <p>Note: This method uses the parameterless signature from the original code; it is not the
     * standard JVM entry point with {@code String[]} arguments. It may be invoked from tests or a
     * custom harness.
     *
     * @throws IOException if an I/O error occurs in any of the stream-based methods
     */
    static void main() throws IOException {
        firstList();
        firstListStream();
        firstThreeList();
        firstThreeListStream();
        firstLinear();
        firstThreeLinear();
    }

    /**
     * Reads {@link #INPUT_FILE} line-by-line, groups numbers by blank lines into an in-memory
     * {@link List} of group sums, sorts the groups descending, and prints the largest group sum.
     *
     * <p>Behavior details:
     * - Uses a {@link Scanner} to iterate lines.
     * - Maintains a {@code List<Integer> numbers} where each element is the sum of a group.
     * - On a blank line a new group sum (0) is appended.
     * - After reading all lines the list is sorted in descending order and the first element is
     *   printed.
     *
     * <p>Time complexity: O(L + g log g)
     * - L = number of input lines (parsing and summing groups)
     * - g = number of groups (sorting dominates: g log g)
     * Space complexity: O(g) extra space to store the group sums (plus negligible scanner buffers).
     *
     * @throws FileNotFoundException if {@code INPUT_FILE} is not found or cannot be opened
     *

     */
    private static void firstList() throws FileNotFoundException {
        Scanner sc = new Scanner(INPUT_FILE);
        List<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                numbers.add(0);
                continue;
            }
            numbers.set(numbers.size()-1, numbers.getLast() + Integer.parseInt(line));
        }
        numbers.sort((a, b) -> b - a);
        System.out.println(numbers.getFirst());
    }

    /**
     * Stream-based implementation that reads the entire file into memory, splits it into groups by
     * blank lines, converts each group's lines to integers, sums them, and prints the maximum sum.
     *
     * <p>Behavior details:
     * - Reads the whole file as a single string with {@link Files#readString(Path)}.
     * - Splits into groups on occurrences of a blank line sequence.
     * - For each group, splits by line breaks, parses integers and sums them.
     * - Uses an IntStream to compute the maximum group sum (or 0 if there are no groups).
     * <p>
     * Time complexity: O(N)
     * - N = total number of characters / tokens parsed. Each character/number is processed a
     *   constant number of times.
     * Space complexity: O(S)
     * - S = size of the file in memory (method reads the entire file into a String and creates
     *   intermediate arrays for the groups). This can be large for big files.
     *
     * @throws IOException if reading the file fails
     *
     */
    private static void firstListStream() throws IOException {
        System.out.println(Arrays.stream(Files.readString(Path.of("2026-02-03/Volo/input.txt")).split("\\R\\s*\\R")).mapToInt(group ->Arrays.stream(group.split("\\R")).mapToInt(Integer::parseInt).sum()).max().orElse(0));
    }

    /**
     * Reads {@link #INPUT_FILE} line-by-line, builds a list of group sums, sorts descending, and
     * prints the sum of the top three group sums.
     *
     * <p>Behavior details:
     * - Similar to {@link #firstList()} but after sorting the method prints the sum of the first
     *   three elements of the sorted list (indexes 0, 1 and 2).
     * - If there are fewer than three groups the code (as written) will attempt to access missing
     *   indexes; the original logic assumes at least three groups exist in the input.
     *   <p>
     *   Time complexity: O(L + g log g) where L = number of lines, g = number of groups (sorting dominates)
     *   Space complexity: O(g) to store group sums
     *
     * @throws FileNotFoundException if {@code INPUT_FILE} is not found or cannot be opened
     *
     */
    private static void firstThreeList() throws FileNotFoundException {
        Scanner sc = new Scanner(INPUT_FILE);
        List<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                numbers.add(0);
                continue;
            }
            numbers.set(numbers.size()-1, numbers.getLast() + Integer.parseInt(line));
        }
        numbers.sort((a, b) -> b - a);
        System.out.println(numbers.get(0) + numbers.get(1) + numbers.get(2));
    }


    /**
     * Stream-based implementation that reads the entire file, computes each group's sum, sorts the
     * group sums in descending order, limits to the top three, and prints their total.
     *
     * <p>Behavior details:
     * - Reads file into memory, splits into groups, computes group sums via streams.
     * - Boxes the IntStream to Stream<Integer> in order to sort in descending order, limits to 3,
     *   then sums the results.
     *   <p>
     * Time complexity: O(N + g log g)
     * - N = total parsed tokens; g = number of groups (sorting the boxed list is g log g)
     * Space complexity: O(S + g)
     * - S = file size in memory, plus O(g) for boxed group sums created for sorting
     *
     * @throws IOException if reading the file fails
     *
     */
    private static void firstThreeListStream() throws IOException {
        System.out.println(Arrays.stream(Files.readString(Path.of("2026-02-03/Volo/input.txt")).split("\\R\\s*\\R")).mapToInt(group -> Arrays.stream(group.split("\\R")).mapToInt(Integer::parseInt).sum()).boxed().sorted((a, b) -> b - a).limit(3).mapToInt(Integer::intValue).sum());
    }

    /**
     * Linear single-pass implementation that scans {@link #INPUT_FILE}, maintains a running group
     * sum, and keeps track of the maximum group sum seen so far. The final maximum is printed.
     *
     * <p>Behavior details:
     * - Uses a {@link Scanner} to read lines sequentially.
     * - Accumulates the current group's sum in {@code current}; on a blank line compares and
     *   updates {@code max} and resets {@code current}.
     * - After the loop, compares the final {@code current} in case the file does not end with a
     *   blank line.
     *   <p>
     * Time complexity: O(L) where L is the number of lines (each line is processed once)
     * Space complexity: O(1) additional space beyond input streaming buffers
     * @throws FileNotFoundException if {@code INPUT_FILE} is not found or cannot be opened
     *
     */
    private static void firstLinear() throws FileNotFoundException {
        Scanner sc = new Scanner(INPUT_FILE);
        int max = 0;
        int current = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                if (current > max) {
                    max = current;
                }
                current = 0;
                continue;
            }
            current += Integer.parseInt(line);
        }
        if (current > max) {
            max = current;
        }
        System.out.println(max);
    }

    /**
     * Linear single-pass implementation that finds the top three group sums without storing all
     * groups. It maintains a fixed-size array {@code topThree} which holds the three largest group
     * sums seen so far in descending order.
     *
     * <p>Behavior details:
     * - Scans the input line-by-line and accumulates the current group's sum.
     * - When a group ends (blank line or after EOF) calls {@link #check(int[], int)} to insert the
     *   group's sum into the {@code topThree} array if appropriate.
     * - After processing the file it prints the sum of the three values in {@code topThree}.
     * <p>
     * Time complexity: O(L) where L is the number of lines (single pass)
     * Space complexity: O(1) additional space (the {@code topThree} array is constant-size)
     *
     *
     * @throws FileNotFoundException if {@code INPUT_FILE} is not found or cannot be opened
     *
     */
    private static void firstThreeLinear() throws FileNotFoundException {
        Scanner sc = new Scanner(INPUT_FILE);
        int[] topThree = new int[3];
        int current = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                check(topThree, current);
                current = 0;
                continue;
            }
            current += Integer.parseInt(line);
        }
        check(topThree, current);
        System.out.println(Arrays.stream(topThree).sum());
    }

    /**
     * Maintain a descending-ordered top-3 array.
     *
     * <p>If {@code current} is larger than any of the values in {@code topThree} this method will
     * insert {@code current} at the correct position and shift the lower values down, preserving
     * the invariant {@code topThree[0] >= topThree[1] >= topThree[2]}.
     *
     * @param topThree an array of length 3 storing the current top three group sums in descending order
     * @param current the candidate group sum to insert
     */
    private static void check(int[] topThree, int current) {
        if (current > topThree[0]) {
            topThree[2] = topThree[1];
            topThree[1] = topThree[0];
            topThree[0] = current;
        } else if (current > topThree[1]) {
            topThree[2] = topThree[1];
            topThree[1] = current;
        } else if (current > topThree[2]) {
            topThree[2] = current;
        }
    }
}