package chess.backend.codes.concretes.Pieces;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;

public class Bishop extends APiece implements Piece.IBishop {
    public Bishop(ICoordinate currentCoor, PieceColor pieceColor) {
        super(currentCoor, pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'B' : 'b';
        hexCode = (pieceColor == PieceColor.WHITE) ? "\u001B[37m" : "\u001B[30m";
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

        difX = Math.abs(difX);
        difY = Math.abs(difY);
        return difX == difY;
    }



}