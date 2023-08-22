package com.betelgeuse.chessai.engines;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.PieceColor;

public class ChessAIEngine extends AChessModel implements IChessEngine, IPlayer {
    PieceColor color;
    IGame game;
    ChessPositionAnalyzer analyzer;

    public ChessAIEngine(Context context, IGame game, PieceColor color) {
        super(context);
        this.color = color;

        this.game = game;
        analyzer = new ChessPositionAnalyzer();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void makeMove() {
        game.getLastExecutedMove().getPositionAfterMoveExecuted().getBoardFenRepresentation();
        String fen = game.getLastExecutedMove().getPositionAfterMoveExecuted().getFullFen();
        List<List<int[][]>> positions = analyzer.analyze(fen);
        float[] evaluations =  evaluatePositions(positions);
        int i = -1;
        if (color == PieceColor.WHITE)
            i = findIndexOfMaxValue(evaluations);
        else i = findIndexOfMinValue(evaluations);

        IMove m = game.getAvaibleMovesForCurrentPosition().get(i);
        game.makeMove(m);

    }

    @Override
    public void setColor(PieceColor color) {
        this.color = color;
    }

    public static int findIndexOfMinValue(float[] array) {
        float minValue = 16;
        int minIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public static int findIndexOfMaxValue(float[] array) {
        float maxValue = -16;
        int maxIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
