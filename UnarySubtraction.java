public class UnarySubtraction extends AbstractTuringOperation {
    private int minuendCount = 0;
    private int subtrahendCount = 0;
    private boolean countingSubtrahend = false;
    private int difference = 0;

    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Count minuend and subtrahend
                if (symbol == '1' && !countingSubtrahend) {
                    minuendCount++;
                    writeSymbol('B'); // Clear as we count
                    head++;
                } else if (symbol == '-') {
                    countingSubtrahend = true;
                    writeSymbol('B');
                    head++;
                } else if (symbol == '1' && countingSubtrahend) {
                    subtrahendCount++;
                    writeSymbol('B');
                    head++;
                } else if (symbol == 'B' && countingSubtrahend) {
                    // Calculate difference (no negatives in unary)
                    difference = Math.max(0, minuendCount - subtrahendCount);
                    currentState = "q1";
                    head = 1; // Go back to write result
                } else {
                    head++;
                }
                break;

            case "q1": // Write difference
                if (difference > 0) {
                    writeSymbol('1');
                    difference--;
                    head++;
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
