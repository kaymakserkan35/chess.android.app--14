package chess.backend.codes.concretes.moveTypes;

import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.concretes.Pieces.PieceType;

public class PromotionMove extends AMove {
    public PieceType typePromoted;

    public PromotionMove(IPawn piece, ICoordinate targetCoor, IPiece targetPiece, PieceType promotionType) {
        super(piece, targetCoor, targetPiece);

        typePromoted = promotionType;

        if (targetPiece != null) {
            moveSymbol += "x";
        }
        moveSymbol += targetCoor.getNotation();

        if (typePromoted == PieceType.IQueen) {
            moveSymbol += "=Q";
        } else if (typePromoted == PieceType.IRook) {
            moveSymbol += "=R";
        } else if (typePromoted == PieceType.IBishop) {
            moveSymbol += "=B";
        } else if (typePromoted == PieceType.IKnight) {
            moveSymbol += "=N";
        }
    }

    @Override
    public void simpleExecute(IChessBoard chessBoard) {
        super.simpleExecute(chessBoard);
        ((IPawn) piece).promote(typePromoted);
    }

    @Override
    public void execute(IChessBoard chessBoard) {
        simpleExecute(chessBoard);
        String fen = chessBoard.getPositionInFenFromBoard();
        positionAfterMoveExecuted = chessBoard.getCurrentPosition().copyInShallowForNextPosition(fen);
        setCastleRights(positionAfterMoveExecuted);

    }

    @Override
    public void getBackMove(IChessBoard chessBoard) {
        ((IPawn) piece).dePromote();
        ICell cell = chessBoard.getCell(toCoor);
        if (cell.getPiece() == null) throw new RuntimeException("");
        cell.removePiece();

        if (targetPiece != null) {
            assert targetPieceCoor != null;
            ICell c = chessBoard.getCell(targetPieceCoor);
            c.addPiece(targetPiece);
            targetPiece.setCurrentCoor(c);
        }

        cell = chessBoard.getCell(fromCoor.get_X(), fromCoor.get_Y());
        cell.addPiece(piece);
        piece.setCurrentCoor(cell);
    }
}