package chess.backend.codes.concretes.Pieces;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;

public class Queen extends APiece implements Piece.IQueen {
    public Queen(ICoordinate currentCoor, PieceColor pieceColor) {
        super(currentCoor, pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'Q' : 'q';
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int difX = targetCoor.get_X() - initialCoor.get_X();
        int difY = targetCoor.get_Y() - initialCoor.get_Y();

        boolean asRook = difX * difY == 0;
        boolean asBishop = Math.abs(difX) == Math.abs(difY);

        return asRook || asBishop;
    }
}