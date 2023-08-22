package chess.backend.codes.abstractions;

public interface ICell extends ICoordinate {
    void addPiece(Piece.IPiece piece);
    void removePiece();
    Piece.IPiece getPiece();
}