public class PalindromeCheck extends AbstractTuringOperation {
    private int leftHead = 1;
    private int rightHead = -1;
    private boolean rightHeadSet = false;

    @Override
    public void initialize(String input) {
        super.initialize(input);
        leftHead = 1;
        rightHead = -1;
        rightHeadSet = false;
        currentState = "q0";
        halted = false;
    }

    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Initialize right head by finding end of input
                if (!rightHeadSet) {
                    if (symbol == 'B' && head + 1 < tape.length() && tape.charAt(head + 1) == 'B') {
                        rightHead = head - 1;
                        rightHeadSet = true;
                        currentState = "q1";
                        head = leftHead; // Return to left head position
                    } else {
                        head++;
                    }
                }
                break;
            case "q1": // Check left and right symbols
                if (leftHead >= rightHead) {
                    currentState = "qaccept";
                    halted = true;
                } else {
                    char leftSymbol = tape.charAt(leftHead);
                    char rightSymbol = tape.charAt(rightHead);
                    if (leftSymbol == 'B' || rightSymbol == 'B' || leftSymbol != rightSymbol) {
                        currentState = "qreject";
                        halted = true;
                    } else {
                        // Mark checked symbols (optional, for visualization)
                        if (leftSymbol == '0' || leftSymbol == '1') {
                            // Could mark as 'X' or something, but for simplicity, just move heads
                            leftHead++;
                            rightHead--;
                            currentState = "q1";
                            head = leftHead; // Move to next left position
                        }
                    }
                }
                break;
        }
    }
}
