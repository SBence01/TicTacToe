package hu.nye.progtech.model;

import java.util.Objects;

public final class Move {

    private final Position position;
    private final Mark mark;

    public Move(Position position, Mark mark) {
        this.position = position;
        this.mark = mark;
    }

    public Position getPosition() {
        return position;
    }

    public Mark getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Move otherMove)) {
            return false;
        }
        return Objects.equals(position, otherMove.position) && mark == otherMove.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, mark);
    }

    @Override
    public String toString() {
        return "Move{" + "position=" + position + ", mark=" + mark + "}";
    }
}
