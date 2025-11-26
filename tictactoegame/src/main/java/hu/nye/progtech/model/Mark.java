package hu.nye.progtech.model;

public enum Mark {

    X('x'),
    O('o'),
    EMPTY('-');

    private final char symbol;

    Mark(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return  symbol;
    }
}
