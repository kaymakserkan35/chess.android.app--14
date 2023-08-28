package com.betelgeuse.chessai.chess.pieces;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;

import com.betelgeuse.chessai.Listeners.PieceDragStartedListener;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Pieces.PieceType;

public abstract class PieceControl extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener, IPieceControl {
    @Override
    public void setAPieceDragStartedListener(PieceDragStartedListener pieceDragStartedListener) {
        this.pieceDragStartedListener = pieceDragStartedListener;
    }

    PieceDragStartedListener pieceDragStartedListener;

    protected static final String DRAGGABLE_OBJECT = "DRAGGABLE_OBJECT";
    char symbol;
    PieceType type;
    ICoordinate currentCoor;
    PieceColor pieceColor;
    List<IMove> avaibleMoves;

    public PieceControl(@NonNull Context context, ICoordinate currentCoor, PieceColor pieceColor) {
        super(context);

        this.currentCoor = currentCoor;
        this.pieceColor = pieceColor;
        if (this instanceof IPawn) type = PieceType.IPawn;
        else if (this instanceof IRook) type = PieceType.IRook;
        else if (this instanceof IKnight) type = PieceType.IKnight;
        else if (this instanceof IBishop) type = PieceType.IBishop;
        else if (this instanceof IQueen) type = PieceType.IQueen;
        else if (this instanceof IKing) type = PieceType.IKing;
        else throw new IllegalStateException();
        avaibleMoves = new ArrayList<IMove>();

        this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        setOnTouchListener(this);
        this.setTag(DRAGGABLE_OBJECT + "_");

    }

    @Override
    public PieceType getType() {
        return  type;
    }

    public abstract boolean canMove(ICoordinate targetCoor);
    public abstract boolean canMove(ICoordinate initialCoor, ICoordinate targetCoor);

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int action = motionEvent.getAction();
        Log.w("Piece Action",String.valueOf(action));

        System.out.println(motionEvent.getAction());

        if (motionEvent.getAction() != MotionEvent.ACTION_UP) {

            if (pieceDragStartedListener != null) pieceDragStartedListener.whenPieceStartDraggingOnCells(this);
            ClipData.Item itemClipData = new ClipData.Item((CharSequence) view.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData clipData = new ClipData(view.getTag().toString(), mimeTypes, itemClipData);
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData, dragShadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    public char getSymbol() {
        return   symbol ;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }



    public ICoordinate getCurrentCoor() {
        return currentCoor;
    }

    public void setCurrentCoor(ICoordinate currentCoor) {
        this.currentCoor = currentCoor;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public List<IMove> getAvailableMoves() {
        return avaibleMoves;
    }

    public void setAvailableMoves(List<IMove> availableMoves) {
        this.avaibleMoves = availableMoves;
    }

}
