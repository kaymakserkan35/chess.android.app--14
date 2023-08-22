package chess.backend.codes.concretes.Pieces;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;

public class Pawn extends APiece implements IPawn {
    public Pawn(ICoordinate currentCoor, PieceColor pieceColor) {
        super(currentCoor, pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'P' : 'p';
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        if (getCurrentCoor() == null) return false;
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;

        if (type == PieceType.IPawn) {
            int deltaX = targetCoor.get_X() - initialCoor.get_X();
            int deltaY = targetCoor.get_Y() - initialCoor.get_Y();

            if (getPieceColor() == PieceColor.WHITE) {
                if (deltaX == 0) {
                    if (deltaY == 1) return true;
                    if (initialCoor.get_Y() == 2 && deltaY == 2) return true;
                }
                if (Math.abs(deltaX) == 1 && deltaY == 1) return true;
            } else if (getPieceColor() == PieceColor.BLACK) {
                if (deltaX == 0) {
                    if (deltaY == -1) return true;
                    if (initialCoor.get_Y() == 7 && deltaY == -2) return true;
                }
                if (Math.abs(deltaX) == 1 && deltaY == -1) return true;
            }
            return false;
        }
        else if (type == PieceType.IRook) {
            int deltaX = targetCoor.get_X() - initialCoor.get_X();
            int deltaY = targetCoor.get_Y() - initialCoor.get_Y();
            return deltaX * deltaY == 0;
        }
        else if (type == PieceType.IKnight) {
            int difX = getCurrentCoor().get_X() - targetCoor.get_X();
            int difY = getCurrentCoor().get_Y() - targetCoor.get_Y();
            difX = Math.abs(difX);
            difY = Math.abs(difY);
            return difX * difY == 2;
        }
        else if (type == PieceType.IBishop) {
            int difX = targetCoor.get_X() - getCurrentCoor().get_X();
            int difY = targetCoor.get_Y() - getCurrentCoor().get_Y();
            difX = Math.abs(difX);
            difY = Math.abs(difY);
            return difX == difY;
        }
        else if (type == PieceType.IQueen) {
            int difX = targetCoor.get_X() - getCurrentCoor().get_X();
            int difY = targetCoor.get_Y() - getCurrentCoor().get_Y();

            boolean asRook = difX * difY == 0;
            boolean asBishop = Math.abs(difX) == Math.abs(difY);

            return asRook || asBishop;
        } else {
            throw new RuntimeException("piece type is incorrect!");
        }
    }



    @Override
    public  void promote(PieceType pieceType) {

        if (pieceType == PieceType.IQueen) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'Q' : 'q';
        } else if (pieceType ==  PieceType.IRook) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'R' : 'r';
        } else if (pieceType ==  PieceType.IBishop) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'B' : 'b';
        } else if (pieceType ==  PieceType.IKnight) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'N' : 'n';
        } else {
            throw new RuntimeException("");
        }

        this.type = pieceType;
    }

    @Override
    public void dePromote() {
        if (type == PieceType.IPawn) return;
        type = PieceType.IPawn;
        symbol = (getPieceColor() == PieceColor.WHITE) ? 'P' : 'p';
    }


    public void promote(Class<? extends IPiece> pieceType) {

    }
}





