package chess.backend.codes.concretes.Pieces;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;


public class Rook extends APiece implements Piece.IRook {
    public Rook(ICoordinate currentCoor, PieceColor pieceColor) {
        super(currentCoor, pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'R' : 'r';
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int deltaX = targetCoor.get_X() - initialCoor.get_X();
        int deltaY = targetCoor.get_Y() - initialCoor.get_Y();
        return deltaX * deltaY == 0;
    }
}