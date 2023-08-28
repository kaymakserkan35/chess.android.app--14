package com.betelgeuse.chessai.engines;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AKerasModel {
    Interpreter interpreter;
    Context context;

    public AKerasModel(Context context) {

        try {
            this.context = context;
            MappedByteBuffer buffer = null;
            buffer = loadModelFile("fina-1.tflite");
            interpreter = new Interpreter(Objects.requireNonNull(buffer), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MappedByteBuffer loadModelFile(String modelPath) throws IOException {
        String path = "src/main/assets/model.tflite";
        AssetFileDescriptor assetFileDescriptor = null;
        AssetManager assets = context.getAssets();
        assetFileDescriptor = assets.openFd(modelPath);
        assert assetFileDescriptor != null;
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, len);

    }


}
