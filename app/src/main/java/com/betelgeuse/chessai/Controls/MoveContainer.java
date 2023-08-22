package com.betelgeuse.chessai.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.betelgeuse.chessai.Listeners.MoveItemClickListener;
import com.betelgeuse.chessai.Listeners.MoveChipClickListener;
import com.betelgeuse.chessai.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import chess.backend.codes.abstractions.IPosition;

public class MoveContainer extends FrameLayout implements MoveChipClickListener {
    FlexboxLayout container_for_moves;
    public void setClickListener(MoveItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    MoveItemClickListener clickListener = null;

    public MoveContainer(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.move_container, this);
        init();
    }

    public MoveContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.move_container, this);
        init();
    }

    public MoveContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MoveContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public  void  init(){
        container_for_moves = findViewById(R.id.container_for_moves);
    }

    public void addMoveChip(MoveChip chip){
        chip.setClickListener(this);
        container_for_moves.addView(chip);
    }
    public void addMoveChip(List<MoveChip> chips){
        removeAllViews();
        for (MoveChip c: chips
             ) {
            c.setClickListener(this);
            container_for_moves.addView(c);
        }
    }

    @Override
    public void execute(IPosition position) {

        if (MoveContainer.this.clickListener == null) throw  new RuntimeException();
        clickListener.execute(position);
    }
}
