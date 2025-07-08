public abstract class AbstractTuringOperation implements TuringMachineOperation {
    protected String tape;
    protected int head;
    protected String currentState;
    protected boolean halted;

    public AbstractTuringOperation() {
        this.halted = false;
        this.currentState = "q0";
    }

    @Override
    public boolean isHalted() {
        return halted;
    }

    @Override
    public String getCurrentState() {
        return currentState;
    }

    @Override
    public String getTape() {
        return tape;
    }

    @Override
    public int getHead() {
        return head;
    }

    @Override
    public void setTape(String tape) {
        this.tape = tape;
    }

    @Override
    public void setHead(int head) {
        this.head = head;
    }

    @Override
    public void setState(String state) {
        this.currentState = state;
    }

    @Override
    public void writeSymbol(char symbol) {
        if (head >= 0 && head < tape.length()) {
            tape = tape.substring(0, head) + symbol + tape.substring(head + 1);
        }
    }

    @Override
    public void initialize(String input) {
        this.halted = false;
        this.currentState = "q0";
    }


    @Override
    public abstract void processStep(char symbol);
}
