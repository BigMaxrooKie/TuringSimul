public class UnaryAddition extends AbstractTuringOperation {
    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Scan through first number
                if (symbol == '1') {
                    head++;
                } else if (symbol == '+') {
                    currentState = "q1";
                    writeSymbol('B'); // Erase the + symbol
                    head++;
                } else {
                    halted = true;
                }
                break;
            case "q1": // Shift second number left to concatenate
                if (symbol == '1') {
                    writeSymbol('B'); // Erase current position
                    head--; // Go back to the gap
                    writeSymbol('1'); // Fill the gap with 1
                    head += 2; // Move to next position in second number
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
