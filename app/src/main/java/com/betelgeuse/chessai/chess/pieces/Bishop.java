package com.betelgeuse.chessai.chess.pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.betelgeuse.chessai.R;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;

public class Bishop extends PieceControl implements IBishop  {
    public Bishop(@NonNull Context context, ICoordinate currentCoor, PieceColor pieceColor) {
        super(context, currentCoor,pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'B' : 'b';
        Drawable drawable;
        if(pieceColor==PieceColor.WHITE){
            drawable =getResources().getDrawable(R.drawable.wbishop);
        }
        else {
            drawable =getResources().getDrawable(R.drawable.bbishop);
        }
        setImageDrawable( drawable);
        this.setTag(DRAGGABLE_OBJECT +"B");

    }
    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int difX = targetCoor.get_X() - initialCoor.get_X();
        int difY = targetCoor.get_Y() - initialCoor.get_Y();

        difX = Math.abs(difX);
        difY = Math.abs(difY);
        return difX == difY;
    }

}
