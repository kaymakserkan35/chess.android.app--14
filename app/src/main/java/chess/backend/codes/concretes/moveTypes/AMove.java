package chess.backend.codes.concretes.moveTypes;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.CoorExtensions;
import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Pieces.PieceType;

public abstract class AMove implements IMove {

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    protected int id;
    public String simpleSymbol = "";
    String moveSymbol = "";
    IMove previous;
    IMove next;
    List<IMove> variants;


    public AMove() {
    }

    @Override
    public ICoordinate getFromCoor() {
        return fromCoor;
    }

    @Override
    public ICoordinate getToCoor() {
        return toCoor;
    }

    @Override
    public ICoordinate getTargetPieceCoor() {
        return targetPieceCoor;
    }

    @Override
    public void setMoveSymbol(String symbol) {
        this.moveSymbol = symbol;
    }

    @Override
    public String getSimpleMoveSymbol() {
        return simpleSymbol;
    }

    @Override
    public String getMoveSymbol() {
        return moveSymbol;
    }


    IPiece piece = null;

    IPiece targetPiece;
    ICoordinate fromCoor, toCoor;

    ICoordinate targetPieceCoor;
    IPosition positionAfterMoveExecuted;

    protected AMove(IPiece piece, ICoordinate targetCoor, IPiece targetPiece) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException("");
        variants = new ArrayList<>();
        this.piece = piece;
        fromCoor = piece.getCurrentCoor();
        toCoor = targetCoor;

        if (targetPiece != null) {
            if (targetPiece.getCurrentCoor() == null) throw new RuntimeException("");
            this.targetPiece = targetPiece;
            targetPieceCoor = targetPiece.getCurrentCoor();
        }

        this.simpleSymbol = CoorExtensions.get(piece.getCurrentCoor(), targetCoor);
    }


    public void simpleExecute(IChessBoard chessBoard) {
        if (targetPiece != null) {
            chessBoard.getCell(targetPieceCoor).removePiece();
            targetPiece.setCurrentCoor(null);
        }

        chessBoard.getCell(fromCoor).removePiece();
        ICell cell = chessBoard.getCell(toCoor);
        cell.addPiece(piece);
        piece.setCurrentCoor(cell);

    }

    @Override
    public void addVariantMove(IMove move) {
        if (variants == null) variants = new ArrayList<>();
        move.setPrevious(this.previous);
        this.variants.add(move);
    }

    public void execute(IChessBoard chessBoard) {

        simpleExecute(chessBoard);

        String fen = chessBoard.getPositionInFenFromBoard();
        positionAfterMoveExecuted = chessBoard.getCurrentPosition().copyInShallowForNextPosition(fen);
        setCastleRights(positionAfterMoveExecuted);


        if (piece instanceof IPawn && Math.abs(fromCoor.get_Y() - toCoor.get_Y()) == 2) {
            positionAfterMoveExecuted.setEnpassent(piece.getPieceColor() == PieceColor.WHITE ?
                    CoorExtensions.get(toCoor.get_X(), toCoor.get_Y() - 1) :
                    CoorExtensions.get(toCoor.get_X(), toCoor.get_Y() + 1));
        } else {
            positionAfterMoveExecuted.setEnpassent(null);
        }


    }


    public void getBackMove(IChessBoard chessBoard) {

        ICell cell = chessBoard.getCell(toCoor);
        if (cell.getPiece() == null) throw new RuntimeException("");
        cell.removePiece();

        if (targetPiece != null) {
            ICell tC = chessBoard.getCell(targetPieceCoor);
            tC.addPiece(targetPiece);
            targetPiece.setCurrentCoor(tC);
        }

        cell = chessBoard.getCell(fromCoor);
        cell.addPiece(piece);
        piece.setCurrentCoor(cell);
    }


    @Override
    public IMove getPrevious() {
        return previous;
    }

    @Override
    public void setPrevious(IMove previous) {
        this.previous = previous;
        if (previous instanceof  EmptyMove) this.id = 1;
        else if (previous.getPiece().getPieceColor()==PieceColor.WHITE)  this.setId(previous.getId());
        else if (previous.getPiece().getPieceColor()==PieceColor.BLACK) this.setId(previous.getId()+1);


    }

    @Override
    public IMove getNext() {
        return next;
    }

    @Override
    public void setNext(IMove next) {
        this.next = next;
        if (this.id == 0) next.setId(1);

    }

    @Override
    public List<IMove> getVariants() {
        return variants;
    }

    @Override
    public Piece.IPiece getPiece() {
        return piece;
    }


    @Override
    public Piece.IPiece getTargetPiece() {
        return targetPiece;
    }

    @Override
    public IPosition getPositionAfterMoveExecuted() {
        return positionAfterMoveExecuted;
    }

    protected void setCastleRights(IPosition positionForWhiteOrBlack) {
        boolean oooCastlForWhite = positionForWhiteOrBlack.isOooForWhite();
        boolean oooCastlForBlack = positionForWhiteOrBlack.isOooForBlack();
        boolean ooCastleForWhite = positionForWhiteOrBlack.isOoForWhite();
        boolean ooCastleForBlack = positionForWhiteOrBlack.isOoForBlack();

        if (oooCastlForWhite || ooCastleForWhite) {
            if (piece.getType() == PieceType.IKing && piece.getPieceColor() == PieceColor.WHITE) {
                positionForWhiteOrBlack.setOoForWhite(false);
                positionForWhiteOrBlack.setOooForWhite(false);
            }
        }

        if (oooCastlForBlack || ooCastleForBlack) {
            if (piece.getType() == PieceType.IKing && piece.getPieceColor() == PieceColor.BLACK) {
                positionForWhiteOrBlack.setOoForBlack(false);
                positionForWhiteOrBlack.setOooForBlack(false);
            }
        }


        if (positionForWhiteOrBlack.isOoForWhite()) {
            boolean v = (fromCoor.get_X() == 8 && fromCoor.get_Y() == 1) || (toCoor.get_X() == 8 && toCoor.get_Y() == 1) ? false : true;
            positionForWhiteOrBlack.setOoForWhite(v);
        }

        if (positionForWhiteOrBlack.isOooForWhite()) {
            boolean v = (fromCoor.get_X() == 1 && fromCoor.get_Y() == 1) || (toCoor.get_X() == 1 && toCoor.get_Y() == 1) ? false : true;
            positionForWhiteOrBlack.setOooForWhite(v);
        }

        if (positionForWhiteOrBlack.isOoForBlack()) {

            boolean v = (fromCoor.get_X() == 8 && fromCoor.get_Y() == 8) || (toCoor.get_X() == 8 && toCoor.get_Y() == 8) ? false : true;
            positionForWhiteOrBlack.setOoForBlack(v);
        }

        if (positionForWhiteOrBlack.isOooForBlack()) {
            boolean v = (fromCoor.get_X() == 1 && fromCoor.get_Y() == 8) || (toCoor.get_X() == 1 && toCoor.get_Y() == 8) ? false : true;
            positionForWhiteOrBlack.setOooForBlack(v);
        }
    }


}