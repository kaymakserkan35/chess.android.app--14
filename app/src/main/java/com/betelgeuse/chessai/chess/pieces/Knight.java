package com.betelgeuse.chessai.chess.pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.betelgeuse.chessai.R;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;

public class Knight extends PieceControl implements Piece.IKnight {
    public Knight(@NonNull Context context,ICoordinate currentCoor, PieceColor pieceColor) {
        super(context, currentCoor,pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'N' : 'n';
        Drawable drawable;
        if(pieceColor==PieceColor.WHITE){
             drawable =getResources().getDrawable(R.drawable.wknight);
        }
        else {
             drawable =getResources().getDrawable(R.drawable.bknight);
        }
        setImageDrawable( drawable);
        this.setTag(DRAGGABLE_OBJECT +"N");



    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;
        int difX = initialCoor.get_X() - targetCoor.get_X();
        int difY = initialCoor.get_Y() - targetCoor.get_Y();
        difX = Math.abs(difX);
        difY = Math.abs(difY);

        return difX * difY == 2;
    }
}
