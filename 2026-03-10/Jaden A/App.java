import java.io.File;
import java.io.FileWriter;
import java.util.Stack;
import java.util.Scanner;

public class App {

   
    public static void main(String[] args) {
        
        try {
        Scanner scanner = new Scanner(new File("src\\strings.txt"));
        int score = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            

           /*  // check for non - corrupted line 
            char firstChar = line.charAt(0);
            char lastChar = line.charAt(line.length() - 1);
            if ((firstChar == '{' && lastChar == '}')
                || (firstChar == '(' && lastChar == ')')
                || (firstChar == '[' && lastChar == ']') 
                || (firstChar == '<' && lastChar == '>') 
                || (line.length() % 2 == 1) ) {
                    continue;
                } */
            // create stack
            Stack<Character> bracketStack = new Stack<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (isOpenBracket(c)) {
                    bracketStack.push(c);
                } else if (!bracketStack.isEmpty()) {
                    System.out.println(bracketStack);
                    char openCharacter = bracketStack.pop();
                    if (!matches(openCharacter, c)) {
                        switch (c) {
                            case '}': 
                                score += 1197;
                                break;
                            case ')': 
                                score += 3;
                                break;
                            case ']':
                                score += 57;
                                break;
                            case '>': 
                                score += 25137;
                                break;
                            default: break;
                        }               
                    }
                }
            }
        



        }
        System.out.println(score);

        } catch (Exception e) {
            e.printStackTrace();
        }
       
        

    }

    public static boolean isOpenBracket(char c) {
            return (c == '(' || c == '{' || c == '[' || c == '<');
    }

    public static boolean matches(char open, char close) {
        switch (open) {
            case '{': 
                return (close == '}');
            case '(': 
                return (close == ')');
            case '[':
                return (close == ']');
            case '<': 
                return (close == '>');
            default: return false;
                        
        }
    }



}
