package com.github.griffty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main class demonstrating four solution options for computing largest group sums.
 *
 * <p>Problem description (implicit): given an input file `input.txt` containing integer values
 * separated into groups by blank lines, compute:
 * <ul>
 *   <li>the largest single group sum (Part 1)</li>
 *   <li>the sum of the top three group sums (Part 2)</li>
 * </ul>
 *
 * <p>This class contains two pairs of implementations:
 * <ol>
 *   <li>List-based solutions:
 *     <ul>
 *       <li>{@link #firstList(Scanner)} — builds a list of group sums, sorts it, and extracts the maximum.</li>
 *       <li>{@link #firstThreeList(Scanner)} — builds a list of group sums, sorts it, and sums the top three.</li>
 *     </ul>
 *   </li>
 *   <li>Linear (streaming) solutions:
 *     <ul>
 *       <li>{@link #firstLinear(Scanner)} — tracks the maximum group sum while scanning without storing all groups.</li>
 *       <li>{@link #firstThreeLinear(Scanner)} — maintains the top three group sums in a fixed-size array while scanning.</li>
 *     </ul>
 *   </li>
 * </ol>
 *
 * <p>Usage notes:
 * <ul>
 *   <li>The file `input.txt` must exist in the working directory.</li>
 *   <li>Each solution reads the file independently — the example `main` repeatedly constructs a new {@link Scanner}
 *       for each call to demonstrate all solutions with the same input.</li>
 *   <li>Complexities:
 *     <ul>
 *       <li>List-based: time O(G log G) due to sorting (G = number of groups), space O(G).</li>
 *       <li>Linear (streaming): time O(N) where N = number of lines, space O(1) (constant extra space).</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class Main {
    /**
     * Entry point used by this example to run all four solution variants in sequence.
     *
     * <p>Note: this `main` uses a non-standard signature (no String[] args) but demonstrates
     * sequential reading of the same input file by recreating {@link Scanner} instances.
     *
     * @throws FileNotFoundException if `input.txt` cannot be opened
     */
    static void main() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        firstList(scanner);
        scanner = new Scanner(new File("input.txt"));
        firstThreeList(scanner);
        scanner = new Scanner(new File("input.txt"));
        firstLinear(scanner);
        scanner = new Scanner(new File("input.txt"));
        firstThreeLinear(scanner);
    }

    /**
     * Solution option: List-based, single maximum.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Scan the input line by line, accumulate running group sums.</li>
     *   <li>On blank line, start a new group (append a zero to the list).</li>
     *   <li>After reading all lines, sort the list in descending order and print the first element
     *       (largest group sum).</li>
     * </ol>
     *
     * <p>Complexity:
     * <ul>
     *   <li>Time: O(G log G) where G is the number of groups (sorting dominates).</li>
     *   <li>Space: O(G) to store all group sums.</li>
     * </ul>
     *
     * <p>Pros:
     * <ul>
     *   <li>Simple to implement and reason about.</li>
     *   <li>Easy to extend if you need more than the top three (e.g., top K).</li>
     * </ul>
     *
     * <p>Cons:
     * <ul>
     *   <li>Requires extra memory proportional to the number of groups.</li>
     *   <li>Sorting adds overhead compared to streaming approaches.</li>
     * </ul>
     *
     * @param sc Scanner over the input source containing integer lines and blank lines between groups
     */
    private static void firstList(Scanner sc){
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
     * Solution option: List-based, top three sum.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Accumulate group sums into a list similarly to {@link #firstList(Scanner)}.</li>
     *   <li>Sort the list in descending order and print the sum of the first three elements.</li>
     * </ol>
     *
     * <p>Complexity:
     * <ul>
     *   <li>Time: O(G log G) due to sorting.</li>
     *   <li>Space: O(G).</li>
     * </ul>
     *
     * <p>Pros:
     * <ul>
     *   <li>Straightforward when you need the top K values for K that may vary or be large.</li>
     * </ul>
     *
     * <p>Cons:
     * <ul>
     *   <li>Less memory- and CPU-efficient than streaming for small fixed K (like 3).</li>
     * </ul>
     *
     * @param sc Scanner over the input source containing integer lines and blank lines between groups
     */
    private static void firstThreeList(Scanner sc) {
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
     * Solution option: Linear (streaming), single maximum.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Scan lines once, maintain `current` running group sum.</li>
     *   <li>On blank line, compare `current` with `max` and reset `current`.</li>
     *   <li>At end, perform one final compare and print `max`.</li>
     * </ol>
     *
     * <p>Complexity:
     * <ul>
     *   <li>Time: O(N) where N is the number of lines.</li>
     *   <li>Space: O(1) extra space (only two integers maintained).</li>
     * </ul>
     *
     * <p>Pros:
     * <ul>
     *   <li>Very memory efficient and fastest for large inputs when only the maximum is needed.</li>
     * </ul>
     *
     * <p>Cons:
     * <ul>
     *   <li>Only computes the single maximum — not suitable if you later need additional top-K values.</li>
     * </ul>
     *
     * @param sc Scanner over the input source containing integer lines and blank lines between groups
     */
    private static void firstLinear(Scanner sc){
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
     * Solution option: Linear (streaming), top three sum.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Scan lines once, maintain `current` running group sum.</li>
     *   <li>On blank line, call {@link #check(int[], int)} to insert `current` into a fixed-size
     *       top-three array if appropriate, then reset `current`.</li>
     *   <li>After scanning, call {@link #check(int[], int)} one last time and print the sum of the top three entries.</li>
     * </ol>
     *
     * <p>Complexity:
     * <ul>
     *   <li>Time: O(N) where N is the number of lines (each line processed once, constant-time check updates).</li>
     *   <li>Space: O(1) extra space (array of size 3).</li>
     * </ul>
     *
     * <p>Pros:
     * <ul>
     *   <li>Efficient both in time and space for small fixed K (here K = 3).</li>
     * </ul>
     *
     * <p>Cons:
     * <ul>
     *   <li>Less flexible if you need a larger K — you would generalize to a min-heap or other structure.</li>
     * </ul>
     *
     * @param sc Scanner over the input source containing integer lines and blank lines between groups
     */
    private static void firstThreeLinear(Scanner sc){
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
     * Helper to maintain the top three values in descending order within a fixed-size array.
     *
     * <p>Contract:
     * <ul>
     *   <li>`topThree` is an array of length 3 where index 0 holds the largest value, index 1 the second,
     *       and index 2 the third largest.</li>
     *   <li>If `current` is larger than one of these, shift lower values down and insert `current` at the
     *       correct position to preserve descending order.</li>
     * </ul>
     *
     * <p>This method performs constant-time comparisons and assignments, making it suitable for streaming updates.
     *
     * @param topThree array of length 3 storing the top three values in descending order (mutated in-place)
     * @param current the new candidate value to consider for inclusion among the top three
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