package com.betelgeuse.chessai.Controls;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.concretes.moveTypes.EmptyMove;

public class GameUIControl implements IGame {
    IGame game;


    public GameUIControl(IGame game) {
        this.game = game;
    }

    private EmptyMove getFirstNode() {

        IMove m = getLastExecutedMove();
        while (!(m instanceof EmptyMove)) {
            m = m.getPrevious();
        }

        return (EmptyMove) m;

    }

    private List<MoveChipData> getAllLines(IMove firstNode) {

        List<MoveChipData> moveChipDataList = new ArrayList<>();
        IMove m = firstNode;
        MoveChipData moveChipData = new MoveChipData(m);
        if (m instanceof  EmptyMove){
            moveChipData.startTag = "[ - ";
        }
       else  moveChipData.startTag = "[";

        moveChipDataList.add(moveChipData);

        while (m.getNext() != null || (m.getVariants() != null)) {
            @Nullable List<IMove> vrants = m.getVariants();
            if (vrants != null) {
                for (IMove mv : vrants
                ) {
                    moveChipDataList.addAll(Objects.requireNonNull(getAllLines(mv)));
                }
            }

            m = m.getNext();
            if (m == null) break;

            moveChipData = new MoveChipData(m);
            moveChipDataList.add(moveChipData);

        }
        moveChipData.endTag = "]";
        return moveChipDataList;

    }


    public List<MoveChipData> getAllLines() {
        return getAllLines(getFirstNode());
    }

    @Override
    public IPosition getLastPosition() {
        return game.getLastPosition();
    }

    @Override
    public IMove getLastExecutedMove() {
        return game.getLastExecutedMove();
    }

    @Override
    public List<IMove> getAvaibleMovesForCurrentPosition() {
        return game.getAvaibleMovesForCurrentPosition();
    }

    @Override
    public void makeMove(IMove move) {
        game.makeMove(move);
    }


    @Override
    public void next() {
        game.next();
    }

    @Override
    public void back() {
        game.back();
    }

    @Override
    public IMove getNode(IMove move) {
        return game.getNode(move);
    }

    @Override
    public void setMainLine(IMove move) {
        game.setMainLine(move);
    }

    @Override
    public void getToPosition(IPosition position) {
        game.getToPosition(position);
    }
}
