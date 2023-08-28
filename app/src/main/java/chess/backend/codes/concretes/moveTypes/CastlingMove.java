package chess.backend.codes.concretes.moveTypes;

import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.concretes.Pieces.PieceType;


public class CastlingMove extends AMove {
    private int rookNewX, rookNewY;

    public CastlingMove(Piece.IPiece king, ICoordinate targetCoor, IPiece rook) {
        super(king, targetCoor, rook);
        if (!(king instanceof IKing) || (rook.getType() != PieceType.IRook))
            throw new RuntimeException("");
        if (rook == null) throw new RuntimeException("");
        if (rook.getCurrentCoor() == null) throw new RuntimeException("");


        if (toCoor.get_X() == 7) {
            rookNewX = toCoor.get_X() - 1;
            rookNewY = toCoor.get_Y();
            moveSymbol = ("0-0");
        }
        if (toCoor.get_X() == 3) {
            rookNewX = toCoor.get_X() + 1;
            rookNewY = toCoor.get_Y();
            moveSymbol = ("0-0-0");
        }
    }

    @Override
    public void simpleExecute(IChessBoard chessBoard) {
        chessBoard.getCell(fromCoor).removePiece();
        ICell cell = chessBoard.getCell(toCoor);
        cell.addPiece(piece);
        getPiece().setCurrentCoor(cell);

        assert targetPieceCoor != null;
        chessBoard.getCell(targetPieceCoor).removePiece();
        ICell cellForRook = chessBoard.getCell(rookNewX, rookNewY);
        cellForRook.addPiece(getTargetPiece());
        assert getTargetPiece() != null;
        getTargetPiece().setCurrentCoor(cellForRook);

    }

    @Override
    public void execute(IChessBoard chessBoard) {

        simpleExecute(chessBoard);

        // SET POSITION
        String fen = chessBoard.getPositionInFenFromBoard();
        positionAfterMoveExecuted = (chessBoard.getCurrentPosition().copyInShallowForNextPosition(fen));


        setCastleRights(positionAfterMoveExecuted);


    }

    @Override
    public void getBackMove(IChessBoard chessBoard) {
        ICell cell = chessBoard.getCell(toCoor.get_X(), toCoor.get_Y());
        if (cell.getPiece() == null) throw new RuntimeException("");
        cell.removePiece();
        cell = chessBoard.getCell(fromCoor.get_X(), fromCoor.get_Y());
        getPiece().setCurrentCoor(cell);
        cell.addPiece(getPiece());

        chessBoard.getCell(rookNewX, rookNewY).removePiece();
        assert targetPieceCoor != null;
        ICell cellForRook = chessBoard.getCell(targetPieceCoor.get_X(), targetPieceCoor.get_Y());
        cellForRook.addPiece(getTargetPiece());
        targetPiece.setCurrentCoor(targetPieceCoor);
    }
}