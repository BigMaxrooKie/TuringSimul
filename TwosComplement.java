public class TwosComplement extends AbstractTuringOperation {
    private boolean flipped = false;

    @Override
    public void initialize(String input) {
        super.initialize(input);
        flipped = false;
    }

    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Flip bits for 1's complement
                if (!flipped) {
                    if (symbol == '0') {
                        writeSymbol('1');
                        head++;
                    } else if (symbol == '1') {
                        writeSymbol('0');
                        head++;
                    } else {
                        flipped = true;
                        head--; // Go back to last digit for adding 1
                        currentState = "q1";
                    }
                }
                break;
            case "q1": // Add 1 to get 2's complement
                if (symbol == '0') {
                    writeSymbol('1');
                    currentState = "qf";
                    halted = true;
                } else if (symbol == '1') {
                    writeSymbol('0');
                    head--;
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
