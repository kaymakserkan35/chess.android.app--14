package chess.backend.codes.abstractions;

import java.util.List;

public interface IGame {
    /**ana varyantın son hamlesininden sonra olusan son konum*/
    public IPosition getLastPosition();
    public IMove getLastExecutedMove();
    public List<IMove> getAvaibleMovesForCurrentPosition();
    public void makeMove(IMove move);
    public void next();
    public void back();

    /** a b c d e f ,   a b c1 ...  : c1 varyantının nodu c hamlesidir. c hamlesini döndürür.    */
    public IMove getNode(IMove move);
    /** a -> b1, a -> b2  : b1 -> a , b1 -> b2
     * b1 varyantında bulunan hamle ise b1 varyantını ana varyant yap. */
    public void setMainLine(IMove move);
    /** chessboard nesnesinin konumunu ve game  nesnesini position a ayarlar.*/
    public  void  getToPosition(IPosition position);
}
