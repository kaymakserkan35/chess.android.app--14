package com.betelgeuse.chessai.Controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.betelgeuse.chessai.Listeners.MoveChipClickListener;
import com.betelgeuse.chessai.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import java.util.List;

public class MoveChip extends androidx.appcompat.widget.AppCompatButton {
    MoveChipData data;

    public void setClickListener(MoveChipClickListener clickListener) {
        this.clickListener = clickListener;
    }

    MoveChipClickListener clickListener = null;

    public MoveChip(Context context) {
        super(context, null, R.style.moveChipStyle);
        init();
    }

    public MoveChip(Context context, AttributeSet attrs) {
        super(context, null, R.style.moveChipStyle);
        init();
    }

    public MoveChip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.style.moveChipStyle);
        init();

    }


    public void init() {
        setText("HELLO");
        setTextAlignment(TEXT_ALIGNMENT_CENTER);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        setLayoutParams(layoutParams);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(data.moveSymbol);
                if (MoveChip.this.clickListener == null) throw new RuntimeException("");
                clickListener.execute(MoveChip.this.data.pos);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public MoveChip setData(MoveChipData data) {
        this.data = data;
        setText(data.startTag + " "  +data.moveId+data.moveSymbol + " " + data.endTag);
        return this;
    }

    public MoveChipData getData() {
        return data;
    }

    public boolean dataHasEndTag() {
        return (data.endTag.equals("]"));
    }

    public boolean dataHasStartTag() {
        return (data.endTag.equals("["));
    }


}
