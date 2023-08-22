package chess.backend.codes.abstractions;
import androidx.annotation.Nullable;

import java.util.List;

import chess.backend.codes.abstractions.Piece.IPiece;
public interface IChessBoard {

    public boolean isCheckMove(IMove move);
    boolean canControl(IPiece piece,ICoordinate coordinate);
    IPosition getCurrentPosition();
    /**satranç tahtasının en son dizil7imindeki konumu*/
    void setCurrentPosition(IPosition currentPosition);

    /**a1 den h8 e kadar konumun string ifadesi*/
    String getPositionInFenFromBoard();
    List<IMove> getAvailableMoves(IPiece piece);
    List< int[][]> getAvailableMoves(List<IMove> moves);
    List<IMove> getAllPossibleMovesForCurrentPosition();

    /** bu methodu sadece konum eklemek için kullan.
     * aynı Game nesnesi ie bu methodu kullanma.
     * hareket ettirildiğinde a1-a2 yerine e7-a2 hareket eder.
     */
    public  void loadPosition(IPosition position);

    /**nihai olarak hamlenin hertürlü satranç kuralına bağlı, legal olup olmadığı */
    boolean isMoveLegal(ICoordinate current, ICoordinate targetCoor);
    ICell getCell(int x, int y);
    /**o-o hamlesinde aynı renkten kale, diger hamlelerde xR xN vs... Null dönebilir.*/

    IPiece getTargetPiece(IPiece piece, ICoordinate targetCoor);
    ICell getCell(ICoordinate coordinate);
    List<IPiece> getPieces();
}