package com.betelgeuse.chessai.chess.pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.betelgeuse.chessai.R;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;

public class King extends PieceControl implements Piece.IKing {
    public King(@NonNull Context context,ICoordinate currentCoor, PieceColor pieceColor ) {
        super(context, currentCoor,pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'K' : 'k';
        Drawable drawable;
        if(pieceColor==PieceColor.WHITE){
            drawable =getResources().getDrawable(R.drawable.wking);
        }
        else {
            drawable =getResources().getDrawable(R.drawable.bking);
        }
        setImageDrawable( drawable);
        this.setTag(DRAGGABLE_OBJECT +"K");
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

        if (getPieceColor() == PieceColor.WHITE) {
            if (initialCoor.get_Y() == 1 && initialCoor.get_X() == 5 && Math.abs(difX) == 2 && difY == 0) {
                return true;
            }
            return Math.abs(difX) <= 1 && Math.abs(difY) <= 1;
        }

        if (getPieceColor() == PieceColor.BLACK) {
            if (initialCoor.get_Y() == 8 && initialCoor.get_X() == 5 && Math.abs(difX) == 2 && difY == 0) {
                return true;
            }
            return Math.abs(difX) <= 1 && Math.abs(difY) <= 1;
        }

        return false;
    }
}
