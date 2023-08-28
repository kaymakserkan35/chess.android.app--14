package com.betelgeuse.chessai.chess.pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.betelgeuse.chessai.R;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Pieces.PieceType;

public class Pawn extends PieceControl implements Piece.IPawn {


    public Pawn(@NonNull Context context, ICoordinate currentCoor, PieceColor pieceColor) {
        super(context,  currentCoor,  pieceColor);
        symbol = (pieceColor == PieceColor.WHITE) ? 'P' : 'p';
        dePromote();
        this.setTag(DRAGGABLE_OBJECT + "P");
    }

    @Override
    public boolean canMove(ICoordinate targetCoor) {
        if (getCurrentCoor() == null) return false;
        return canMove(getCurrentCoor(), targetCoor);
    }

    @Override
    public boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor) {
        if (initialCoor == null) return false;

        if (type == PieceType.IPawn) {
            int deltaX = targetCoor.get_X() - initialCoor.get_X();
            int deltaY = targetCoor.get_Y() - initialCoor.get_Y();

            if (getPieceColor() == PieceColor.WHITE) {
                if (deltaX == 0) {
                    if (deltaY == 1) return true;
                    if (initialCoor.get_Y() == 2 && deltaY == 2) return true;
                }
                if (Math.abs(deltaX) == 1 && deltaY == 1) return true;
            } else if (getPieceColor() == PieceColor.BLACK) {
                if (deltaX == 0) {
                    if (deltaY == -1) return true;
                    if (initialCoor.get_Y() == 7 && deltaY == -2) return true;
                }
                if (Math.abs(deltaX) == 1 && deltaY == -1) return true;
            }
            return false;
        }
        else if (type == PieceType.IRook) {
            int deltaX = targetCoor.get_X() - initialCoor.get_X();
            int deltaY = targetCoor.get_Y() - initialCoor.get_Y();
            return deltaX * deltaY == 0;
        }
        else if (type == PieceType.IKnight) {
            int difX = getCurrentCoor().get_X() - targetCoor.get_X();
            int difY = getCurrentCoor().get_Y() - targetCoor.get_Y();
            difX = Math.abs(difX);
            difY = Math.abs(difY);
            return difX * difY == 2;
        }
        else if (type == PieceType.IBishop) {
            int difX = targetCoor.get_X() - getCurrentCoor().get_X();
            int difY = targetCoor.get_Y() - getCurrentCoor().get_Y();
            difX = Math.abs(difX);
            difY = Math.abs(difY);
            return difX == difY;
        }
        else if (type == PieceType.IQueen) {
            int difX = targetCoor.get_X() - getCurrentCoor().get_X();
            int difY = targetCoor.get_Y() - getCurrentCoor().get_Y();

            boolean asRook = difX * difY == 0;
            boolean asBishop = Math.abs(difX) == Math.abs(difY);

            return asRook || asBishop;
        } else {
            throw new RuntimeException("piece type is incorrect!");
        }
    }

    @Override
    public void promote(PieceType type) {
        Drawable drawable = null;
        if (pieceColor == PieceColor.WHITE) {
            if (type == PieceType.IRook) {
                drawable = getResources().getDrawable(R.drawable.wrook);
            }
            if (type == PieceType.IKnight) {
                drawable = getResources().getDrawable(R.drawable.wknight);
            }
            if (type == PieceType.IBishop) {
                drawable = getResources().getDrawable(R.drawable.wbishop);
            }
            if (type == PieceType.IQueen) {
                drawable = getResources().getDrawable(R.drawable.wqueen);
            }

        } else {
            if (type == PieceType.IRook) {
                drawable = getResources().getDrawable(R.drawable.brook);
            }
            if (type == PieceType.IKnight) {
                drawable = getResources().getDrawable(R.drawable.bknight);
            }
            if (type == PieceType.IBishop) {
                drawable = getResources().getDrawable(R.drawable.bbishop);
            }
            if (type == PieceType.IQueen) {
                drawable = getResources().getDrawable(R.drawable.bqueen);
            }
        }
        setImageDrawable(drawable);



        if (type == PieceType.IQueen) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'Q' : 'q';
        } else if (type ==  PieceType.IRook) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'R' : 'r';
        } else if (type ==  PieceType.IBishop) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'B' : 'b';
        } else if (type ==  PieceType.IKnight) {
            symbol = (getPieceColor() == PieceColor.WHITE) ? 'N' : 'n';
        } else {
            throw new RuntimeException("");
        }

        this.type = type;
    }

    @Override
    public void dePromote() {
        type = PieceType.IPawn;
        symbol = (getPieceColor() == PieceColor.WHITE) ? 'P' : 'p';

        Drawable drawable;
        if (pieceColor == PieceColor.WHITE) {
            drawable = getResources().getDrawable(R.drawable.wpawn);
        } else {
            drawable = getResources().getDrawable(R.drawable.bpawn);
        }
        setImageDrawable(drawable);
    }
}
