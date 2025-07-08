public interface TuringMachineOperation {
    void processStep(char symbol);
    boolean isHalted();
    String getCurrentState();
    void initialize(String input);
    String getTape();
    int getHead();
    void setTape(String tape);
    void setHead(int head);
    void setState(String state);
    void writeSymbol(char symbol);
}
