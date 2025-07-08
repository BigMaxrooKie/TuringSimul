public class AnBnC2n extends AbstractTuringOperation {
    private int aCount = 0;
    private int bCount = 0;
    private int cCount = 0;
    private int phase = 0; // 0 for 'a' phase, 1 for 'b' phase, 2 for 'c' phase
    private boolean initialized = false;

    @Override
    public void initialize(String input) {
        super.initialize(input);
        aCount = 0;
        bCount = 0;
        cCount = 0;
        phase = 0;
        initialized = false;
    }

    @Override
    public void processStep(char symbol) {
        if (!initialized) {
            // First pass: count the symbols and validate order
            if (head < tape.length() && tape.charAt(head) != 'B') {
                char ch = tape.charAt(head);
                if (phase == 0) {
                    if (ch == 'a') {
                        aCount++;
                        head++;
                    } else if (ch == 'b') {
                        phase = 1;
                        bCount++;
                        head++;
                    } else {
                        currentState = "qreject";
                        halted = true;
                    }
                } else if (phase == 1) {
                    if (ch == 'b') {
                        bCount++;
                        head++;
                    } else if (ch == 'c') {
                        phase = 2;
                        cCount++;
                        head++;
                    } else {
                        currentState = "qreject";
                        halted = true;
                    }
                } else if (phase == 2) {
                    if (ch == 'c') {
                        cCount++;
                        head++;
                    } else {
                        currentState = "qreject";
                        halted = true;
                    }
                }
            } else {
                // End of input, check if counts satisfy aⁿbⁿc²ⁿ
                initialized = true;
                if (aCount == bCount && cCount == 2 * aCount && aCount > 0) {
                    currentState = "qaccept";
                } else {
                    currentState = "qreject";
                }
                halted = true;
            }
        }
        // If initialized, we've already decided accept/reject
    }
}
