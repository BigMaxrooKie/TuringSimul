public class UnaryMultiplication extends AbstractTuringOperation {
    private int firstCount = 0;
    private int secondCount = 0;
    private boolean countingSecond = false;
    private boolean writingResult = false;
    private int resultWritten = 0;

    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Count numbers and clear tape
                if (symbol == '1' && !countingSecond) {
                    firstCount++;
                    writeSymbol('B');
                    head++;
                } else if (symbol == '*') {
                    countingSecond = true;
                    writeSymbol('B');
                    head++;
                } else if (symbol == '1' && countingSecond) {
                    secondCount++;
                    writeSymbol('B');
                    head++;
                } else if (symbol == 'B' && countingSecond && secondCount > 0) {
                    writingResult = true;
                    currentState = "q1";
                    head = 1; // Go back to start
                } else {
                    head++;
                }
                break;

            case "q1": // Write result
                if (resultWritten < firstCount * secondCount) {
                    writeSymbol('1');
                    resultWritten++;
                    head++;
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
