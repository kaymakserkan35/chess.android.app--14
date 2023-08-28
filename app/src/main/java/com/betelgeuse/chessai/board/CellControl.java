package com.betelgeuse.chessai.board;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.gridlayout.widget.GridLayout;

import com.betelgeuse.chessai.Listeners.PieceDroppedListener;
import com.betelgeuse.chessai.chess.pieces.PieceControl;

import chess.backend.codes.abstractions.CoorExtensions;
import chess.backend.codes.abstractions.Piece;

public class CellControl extends FrameLayout implements ICellControl, View.OnDragListener {
    public void setPieceDroppedListener(PieceDroppedListener pieceDroppedListener) {
        this.pieceDroppedListener = pieceDroppedListener;
    }

    PieceDroppedListener pieceDroppedListener = null;
    @Nullable
    private PieceControl piece = null;
    private int x;
    private int y;
    private String notation;


    public CellControl(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        this.notation = CoorExtensions.get(x, y);


        defaultColor();


        this.setTag(String.valueOf(x) + String.valueOf(y));

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 0;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
        params.setMargins(0, 0, 0, 0); // Set margins to zero
        this.setLayoutParams(params);


        setOnDragListener(this);

        Log.d("ChessCellCreated", "");
    }

    public CellControl(Context context) {
        super(context);


        Log.d("ChessCellCreated", "");
    }


    @Override
    public void addPiece(Piece.IPiece piece) {

        try {
            if (this.piece != null) {
                if (this.piece.getPieceColor() == piece.getPieceColor())
                    throw new RuntimeException();
                removePiece();
            }
            addView((PieceControl) piece);
            this.piece = (PieceControl) piece;
            invalidate();
        } catch (Exception e) {
            Log.e("CELCONTROL", this.getNotation());

        }

    }

    public void removePiece() {
        if (piece != null) {
            //removeAllViews();
            removeView(piece);
            this.piece = null;
            invalidate();

        }
    }

    @Nullable
    @Override
    public PieceControl getPiece() {
        if (piece != null) {
            return piece;
        }
        return null;
    }

    @Override
    public void setColor(int color) {
        this.setBackgroundColor(color);
    }

    @Override
    public void defaultColor() {
        if ((x + y) % 2 == 1) {
            this.setBackgroundColor(Color.WHITE);
        } else {
            this.setBackgroundColor(Color.GRAY);
        }
    }


    @Override
    public String getNotation() {
        return notation;
    }

    @Override
    public int get_X() {
        return x;
    }

    @Override
    public int get_Y() {
        return y;
    }


    @Override
    public boolean onDrag(View view, DragEvent event) {
        CellControl cursorView = (CellControl) view;
        PieceControl draggableObject = (PieceControl) event.getLocalState();
        CellControl parentView = (CellControl) draggableObject.getParent();

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED: {
                //draggableObject.setVisibility(View.INVISIBLE);
            }
            case DragEvent.ACTION_DRAG_ENTERED: {
                break;
            }
            case DragEvent.ACTION_DROP: {
                // buradan butun tası ekleme kodlarını kaldırdım. zaten game nesnesi bunu yapıyor.

                {
                    if (pieceDroppedListener != null) pieceDroppedListener.whenPieceDroppedOnSomeCell(this, draggableObject);
                    else throw  new RuntimeException("");
                }
                break;
            }
            case DragEvent.ACTION_DRAG_ENDED: {
                draggableObject.setVisibility(View.VISIBLE); // Görünürlüğü geri getir
                break;
            }
        }
        return true;
    }
}
