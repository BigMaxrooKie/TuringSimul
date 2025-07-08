public class UnaryDivision extends AbstractTuringOperation {
    private int dividendCount = 0;
    private int divisorCount = 0;
    private boolean countingDivisor = false;
    private int quotient = 0;

    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Count dividend and divisor
                if (symbol == '1' && !countingDivisor) {
                    dividendCount++;
                    writeSymbol('B'); // Clear as we count
                    head++;
                } else if (symbol == '/') {
                    countingDivisor = true;
                    writeSymbol('B');
                    head++;
                } else if (symbol == '1' && countingDivisor) {
                    divisorCount++;
                    writeSymbol('B');
                    head++;
                } else if (symbol == 'B' && divisorCount > 0) {
                    // Calculate quotient
                    quotient = dividendCount / divisorCount;
                    currentState = "q1";
                    head = 1; // Go back to write result
                } else {
                    head++;
                }
                break;

            case "q1": // Write quotient
                if (quotient > 0) {
                    writeSymbol('1');
                    quotient--;
                    head++;
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
