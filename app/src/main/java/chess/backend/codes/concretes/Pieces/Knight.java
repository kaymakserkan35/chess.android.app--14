package chess.backend.codes.concretes.Pieces;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;


public class Knight extends APiece implements Piece.IKnight {
    public Knight(ICoordinate currentCoor, PieceColor pieceColor) {
        super(currentCoor, pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'N' : 'n';
        hexCode = (pieceColor == PieceColor.WHITE) ? "\u001B[37m" : "\u001B[37m";
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int difX = initialCoor.get_X() - targetCoor.get_X();
        int difY = initialCoor.get_Y() - targetCoor.get_Y();
        difX = Math.abs(difX);
        difY = Math.abs(difY);

        return difX * difY == 2;
    }
}