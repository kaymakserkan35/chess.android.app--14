package chess.backend.codes.abstractions;

import chess.backend.codes.abstractions.Piece.*;

import androidx.annotation.Nullable;

import java.util.List;

public interface IMove {


    public void setId(int id);
    public int getId();
    IMove getPrevious();

    void setPrevious(IMove previous);

    IMove getNext();

    void setNext(IMove next);

    /**
     * varyant atanır iken previous olarak bu nesne nin previous  atanır. otomatik olarak.
     * move.setPrevious(this.previous);
     * b s  --> s ın variyanlarının previos u b olmalı
     * */
    void  addVariantMove(IMove move);
    List<IMove> getVariants();

    IPiece getPiece();

    IPiece getTargetPiece();

    IPosition getPositionAfterMoveExecuted();
    void setMoveSymbol(String symbol);
    ICoordinate getFromCoor();
    ICoordinate getToCoor();
    ICoordinate getTargetPieceCoor();

    public void simpleExecute(IChessBoard chessBoard);
    /**saadece satranc tahtsındaki tasları oynatır.
     * ve
     * hamleden sonra olusan konumu yaratır.*/
    void execute(IChessBoard chessBoard);
    /**saadece satranc tahtsındaki tasları geri oynatır.*/
    void getBackMove(IChessBoard chessBoard);
    /**Nf3*/
    String getMoveSymbol();
    /**a2-a4*/
    String getSimpleMoveSymbol();
}