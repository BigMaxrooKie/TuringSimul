import java.util.*;

public class Main {
    private String tape;
    private int head;
    private String currentState;
    private String operation;
    private List<String> stateTransitions;
    private TuringMachineOperation currentOperation;

    public Main() {
        this.stateTransitions = new ArrayList<>();
    }

    public void initialize(String input, String op) {
        this.tape = "B" + input + "BBBBBBBBBBBBBBBB";
        this.head = 1;
        this.currentState = "q0";
        this.operation = op;
        this.stateTransitions.clear();

        // Initialize specific operation
        switch (op) {
            case "ADD":
                currentOperation = new UnaryAddition();
                break;
            case "SUB":
                currentOperation = new UnarySubtraction();
                break;
            case "MUL":
                currentOperation = new UnaryMultiplication();
                break;
            case "DIV":
                currentOperation = new UnaryDivision();
                break;
            case "1COMP":
                currentOperation = new OnesComplement();
                break;
            case "2COMP":
                currentOperation = new TwosComplement();
                break;

            case "PALINDROME":
                currentOperation = new PalindromeCheck();
                break;
            case "ANBNCN":
                currentOperation = new AnBnCn();
                break;
            case "ANBNC2N":
                currentOperation = new AnBnC2n();
                break;

        }

        if (currentOperation != null) {
            currentOperation.initialize(input);
        }
    }

    public void run() {
        int steps = 0;
        while (!isHalted() && steps < 1000) {
            String transition = executeStep();
            if (transition != null) {
                stateTransitions.add(transition);
            }
            steps++;
        }
    }

    private String executeStep() {
        if (head < 0 || head >= tape.length() || currentOperation == null) {
            return null;
        }

        char currentSymbol = tape.charAt(head);
        String oldState = currentState;
        String oldTape = tape;
        int oldHead = head;


        currentOperation.setTape(tape);
        currentOperation.setHead(head);
        currentOperation.setState(currentState);


        currentOperation.processStep(currentSymbol);


        tape = currentOperation.getTape();
        head = currentOperation.getHead();
        currentState = currentOperation.getCurrentState();

        String direction = oldHead < head ? "R" : (oldHead > head ? "L" : "S");
        return String.format("δ(%s, %c) = (%s, %c, %s) | Head: %d→%d | Tape: %s",
                oldState, currentSymbol, currentState,
                tape.charAt(oldHead), direction, oldHead, head, tape);
    }

    private boolean isHalted() {
        return currentOperation != null && currentOperation.isHalted();
    }

    public void displayStateTable() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("UNARY TURING MACHINE STATE TRANSITION TABLE");
        System.out.println("=".repeat(80));
        System.out.println("Operation: " + operation);
        System.out.println("Final Tape: " + tape.replaceAll("B+$", "").replaceAll("^B+", ""));
        System.out.println("Final State: " + currentState);
        System.out.println("Total Transitions: " + stateTransitions.size());
        System.out.println("-".repeat(80));

        for (int i = 0; i < stateTransitions.size(); i++) {
            System.out.printf("Step %3d: %s%n", i + 1, stateTransitions.get(i));
        }
        System.out.println("=".repeat(80));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main tm = new Main();

        System.out.println(" MODULAR UNARY TURING MACHINE SIMULATOR");
        System.out.println("==========================================");
        System.out.println("Note: For arithmetic operations, use UNARY representation:");
        System.out.println("- Number 3 = '111'");
        System.out.println("- Number 5 = '11111'");
        System.out.println("- Addition: '111+11111' (3+5)");
        System.out.println("- Subtraction: '11111-111' (5-3)");
        System.out.println("- Multiplication: '111*11' (3*2)");
        System.out.println("- Division: '111111/11' (6/2)");

        while (true) {
            System.out.println("\nAvailable Operations:");
            System.out.println("1.  ADD        - Unary Addition (e.g., 111+11111)");
            System.out.println("2.  SUB        - Unary Subtraction (e.g., 11111-111)");
            System.out.println("3.  MUL        - Unary Multiplication (e.g., 111*11)");
            System.out.println("4.  DIV        - Unary Division (e.g., 111111/11)");
            System.out.println("5.  1COMP      - One's Complement (e.g., 1010)");
            System.out.println("6.  2COMP      - Two's Complement (e.g., 1010)");

            System.out.println("7.  PALINDROME - Check Palindrome (e.g., 1001)");
            System.out.println("8.  ANBNCN     - Language a^n b^n c^n (e.g., aaabbbccc)");
            System.out.println("9. ANBNC2N    - Language a^n b^n c^2n (e.g., aabbcccc)");
            System.out.println("0.  EXIT       - Exit Program");

            System.out.print("\nEnter operation number (0-11): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Thank you for using the Modular Turing Machine Simulator!");
                break;
            }

            String[] operations = {"", "ADD", "SUB", "MUL", "DIV", "1COMP", "2COMP",
                    "PALINDROME", "ANBNCN", "ANBNC2N", "WCW"};

            if (choice < 1 || choice > 11) {
                System.out.println(" Invalid choice! Please try again.");
                continue;
            }

            String operation = operations[choice];
            System.out.print("Enter input string: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(" Input cannot be empty!");
                continue;
            }

            System.out.println("\n Processing...");
            tm.initialize(input, operation);
            tm.run();
            tm.displayStateTable();

            System.out.print("\nContinue? (y/n): ");
            String cont = scanner.nextLine().trim().toLowerCase();
            if (!cont.equals("y") && !cont.equals("yes")) {
                break;
            }
        }

        scanner.close();
    }
}
