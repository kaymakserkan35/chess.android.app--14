package chess.backend.codes.abstractions;

import java.util.List;

import chess.backend.codes.concretes.Pieces.PieceType;




public class Piece {
    public interface IPromotable {
        void promote(PieceType type);
        void dePromote();
    }

    public interface IPawn extends IPiece, IPromotable {
    }

    public interface IRook extends IPiece {
    }

    public interface IKnight extends IPiece {
    }

    public interface IBishop extends IPiece {
    }

    public interface IQueen extends IPiece {
    }

    public interface IKing extends IPiece {
    }

    public interface IPiece {

        char getSymbol();
        void setSymbol(char symbol);
        boolean canMove(ICoordinate targetCoor);
        boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor);
        ICoordinate getCurrentCoor();
        void setCurrentCoor(ICoordinate currentCoor);
        PieceColor getPieceColor();
        List<IMove> getAvailableMoves();
        void setAvailableMoves(List<IMove> availableMoves);
        PieceType getType();
    }
}
