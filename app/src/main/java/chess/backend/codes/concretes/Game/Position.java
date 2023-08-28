package chess.backend.codes.concretes.Game;


import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.PieceColor;


public class Position extends APosition {

    private List<IPosition> variants;

    public Position() {
        super();
        variants = new ArrayList<>();
    }

    public Position(String fen) {
        super(fen);
        variants = new ArrayList<>();
    }

    public IPosition getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(IPosition nextPosition) {
        this.nextPosition = nextPosition;
    }

    public IPosition getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(IPosition previousPosition) {
        this.previousPosition = previousPosition;
    }

    public List<IPosition> getVariants() {
        return variants;
    }

    @Override
    public Position copyInShallowForNextPosition(String fen) {
        Position p = new Position();
        p.setOoForBlack(ooForBlack);
        p.setOoForWhite(ooForWhite);
        p.setOooForBlack(oooForBlack);
        p.setOooForWhite(oooForWhite);
        p.setWhosTurn(whosTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        p.setFenPosition(fen);
        return p;
    }

    public static String getDifferenceOfPositions(Position current, Position next) {
        StringBuilder resultFen = new StringBuilder();
        String fenCurrent = current.getPositionInString();
        String fenNext = next.getPositionInString();
        if (fenNext.length() != 64 || fenCurrent.length() != 64) {
            throw new RuntimeException();
        }
        int count = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                char cCurrent = fenCurrent.charAt(count);
                char cNext = fenNext.charAt(count);
                count++;
                if (cNext == cCurrent) {
                    resultFen.append('o');
                } else {
                    resultFen.append('x');
                }
            }
        }
        return resultFen.toString();
    }
}