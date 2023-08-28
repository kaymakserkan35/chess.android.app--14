package chess.backend.codes.concretes.Game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import chess.backend.codes.abstractions.CoorExtensions;
import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.Piece.*;
public class Cell implements ICell {
    private IPiece piece;
    private int x;
    private int y;
    private String notation;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.notation = CoorExtensions.get(x, y);
    }

    public int get_X() {
        return x;
    }

    public int get_Y() {
        return y;
    }

    public String getNotation() {
        return notation;
    }

    public void addPiece(IPiece piece) {
        this.piece = piece;
    }

    public IPiece getPiece() {
        return piece;
    }

    public void removePiece() {
        piece = null;
    }
}
