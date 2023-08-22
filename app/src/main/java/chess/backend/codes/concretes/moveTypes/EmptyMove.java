package chess.backend.codes.concretes.moveTypes;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.concretes.Game.Position;


public class EmptyMove extends AMove {

    public EmptyMove(IPosition position) {
        positionAfterMoveExecuted = position;
        id = 0;
    }

    public EmptyMove() {
        positionAfterMoveExecuted = new Position();    id = 0;
    }

    public EmptyMove(IPiece piece, ICoordinate targetCoor, IPiece targetPiece) {
        super(piece, targetCoor, targetPiece);    id = 0;
    }

    @Override
    public void simpleExecute(IChessBoard chessBoard) {
        // No action needed for an empty move.
    }

    @Override
    public void execute(IChessBoard chessBoard) {
        // No action needed for an empty move.
    }

    @Override
    public void getBackMove(IChessBoard chessBoard) {
        // No action needed for an empty move.
    }
}