package com.betelgeuse.chessai.engines;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class AChessModel extends AKerasModel {
    public AChessModel(Context context) {
        super(context);
    }
    protected float evaluatePosition( List<int[][]> pos) {
        List<List<int[][]>> positionsList = new ArrayList<>();
        positionsList.add(pos);
        float[][][][] input = convertToFloat(positionsList);


        float[][] output = new float[1][1];
        interpreter.run(input, output);
        return output[0][0];

    }
    protected float[] evaluatePositions(List<List<int[][]>> positionsList) {
        float[] res = new float[positionsList.size()];
        int i = 0;
        for (List<int[][]> item: positionsList
             ) {
            List<List<int[][]>> pos = new ArrayList<>();
            pos.add(item);
            float[][][][] input = convertToFloat(pos);
            float[][] output = new float[1][1];
            interpreter.run(input, output);
            res[i] = output[0][0];
            i = i+1;
        }
        return res;

    }

    private float[][][][] convertToFloat(List<List<int[][]>> positionsList) {
        float[][][][] floatArray = new float[positionsList.size()][][][];
        for (int i = 0; i < positionsList.size(); i++) {
            List<int[][]> innerList = positionsList.get(i);
            floatArray[i] = new float[innerList.size()][][];
            for (int j = 0; j < innerList.size(); j++) {
                int[][] innerArray = innerList.get(j);
                floatArray[i][j] = new float[innerArray.length][innerArray[0].length];
                for (int k = 0; k < innerArray.length; k++) {
                    for (int l = 0; l < innerArray[k].length; l++) {
                        floatArray[i][j][k][l] = (float) innerArray[k][l]; // int'i float'a dönüştür
                    }
                }
            }
        }
        return floatArray;
    }


}
