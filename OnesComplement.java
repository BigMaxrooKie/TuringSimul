public class OnesComplement extends AbstractTuringOperation {
    @Override
    public void processStep(char symbol) {
        switch (currentState) {
            case "q0": // Flip each bit
                if (symbol == '0') {
                    writeSymbol('1');
                    head++;
                } else if (symbol == '1') {
                    writeSymbol('0');
                    head++;
                } else {
                    currentState = "qf";
                    halted = true;
                }
                break;
        }
    }
}
