package chess.backend.codes.concretes.Pieces;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;


public abstract class APiece implements IPiece {
    protected String hexCode = "";
    char symbol;
    PieceType type;

    @Override
    public PieceType getType() {
        return type;
    }

    public APiece(ICoordinate currentCoor, PieceColor pieceColor) {
        this.currentCoor = currentCoor;
        this.pieceColor = pieceColor;
        if (this instanceof IPawn) type = PieceType.IPawn;
        else if (this instanceof IRook) type = PieceType.IRook;
        else if (this instanceof Piece.IKnight) type = PieceType.IKnight;
        else if (this instanceof IBishop) type = PieceType.IBishop;
        else if (this instanceof IQueen) type = PieceType.IQueen;
        else if (this instanceof IKing) type = PieceType.IKing;
        else throw new IllegalStateException();
        avaibleMoves = new ArrayList<IMove>();

    }

    ICoordinate currentCoor;
    PieceColor pieceColor;
    List<IMove> avaibleMoves;

    public abstract boolean canMove(ICoordinate targetCoor);
    public abstract boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor);

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }



    public ICoordinate getCurrentCoor() {
        return currentCoor;
    }

    public void setCurrentCoor(ICoordinate currentCoor) {
        this.currentCoor = currentCoor;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public List<IMove> getAvailableMoves() {
        return avaibleMoves;
    }

    public void setAvailableMoves(List<IMove> availableMoves) {
        this.avaibleMoves = availableMoves;
    }
}