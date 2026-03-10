private static final File INPUT_FILE = new File("2026-03-10/input.txt");

void main() throws FileNotFoundException {
    Scanner sc = new Scanner(INPUT_FILE);
    int i = 0;
    while (sc.hasNextLine()) {
        String line = sc.nextLine();
        IO.println("Processing line: " + line);
        i += getLineValue(line);
        IO.println("Line value: " + getLineValue(line));
    }
    IO.println(i);
}

private static int getLineValue(String s) {
    Stack<Character> chars = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{' || c == '<') {
            chars.push(c);
        } else {
            char top = chars.pop();
            if (c != getClosed(top)) {
                IO.println("Expected " + getClosed(top) + " but found " + c + " + " + getPoints(c));
                return getPoints(c);
            }
        }
    }
    return 0;
}

private static int getPoints(char c) {
    return switch (c) {
        case ')' -> 3;
        case ']' -> 57;
        case '}' -> 1197;
        case '>' -> 25137;
        default -> throw new IllegalArgumentException("Invalid character: " + c);
    };
}

private static char getClosed(char c) {
    return switch (c) {
        case '(' -> ')';
        case '[' -> ']';
        case '{' -> '}';
        case '<' -> '>';
        default -> throw new IllegalArgumentException("Invalid character: " + c);
    };
}
