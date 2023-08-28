package com.betelgeuse.chessai.chess.pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.betelgeuse.chessai.R;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;

public class Rook  extends PieceControl implements Piece.IRook {
    public Rook(@NonNull Context context, ICoordinate currentCoor, PieceColor pieceColor) {
        super(context, currentCoor,pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'R' : 'r';
        Drawable drawable;
        if(pieceColor==PieceColor.WHITE){
            drawable =getResources().getDrawable(R.drawable.wrook);
        }
        else {
            drawable =getResources().getDrawable(R.drawable.brook);
        }
        setImageDrawable( drawable);
        this.setTag(DRAGGABLE_OBJECT +"R");
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int deltaX = targetCoor.get_X() - initialCoor.get_X();
        int deltaY = targetCoor.get_Y() - initialCoor.get_Y();
        return deltaX * deltaY == 0;
    }
}
