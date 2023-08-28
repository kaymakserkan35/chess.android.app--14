package chess.backend.codes.concretes.moveTypes;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.concretes.Pieces.PieceType;


public class SimpleMove extends AMove {

    public SimpleMove(Piece.IPiece piece, ICoordinate targetCoor, Piece.IPiece targetPiece) {
        super(piece, targetCoor, targetPiece);

        if (!(piece.getType() == PieceType.IPawn)) {
            moveSymbol += Character.toString(piece.getSymbol()).toUpperCase();
        }
        if (targetPiece != null) {
            moveSymbol += "x";
        }
        moveSymbol += targetCoor.getNotation();
    }

    @Override
    public void execute(IChessBoard chessBoard) {
        super.execute(chessBoard);
    }
}
