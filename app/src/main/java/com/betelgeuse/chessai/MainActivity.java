package com.betelgeuse.chessai;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.betelgeuse.chessai.Controls.GameUIControl;
import com.betelgeuse.chessai.Controls.MoveChip;
import com.betelgeuse.chessai.Controls.MoveChipData;
import com.betelgeuse.chessai.Controls.MoveContainer;
import com.betelgeuse.chessai.Listeners.CheckMove;
import com.betelgeuse.chessai.Listeners.MoveItemClickListener;
import com.betelgeuse.chessai.board.ChessBoardControl;
import com.betelgeuse.chessai.board.ICellControl;
import com.betelgeuse.chessai.board.IChessBoardControl;
import com.betelgeuse.chessai.chess.pieces.IPieceControl;
import com.betelgeuse.chessai.engines.ChessAIEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Game.Game;
import chess.backend.codes.concretes.Game.MiniChessBoard;
import chess.backend.codes.concretes.Game.Player;

public class MainActivity extends AppCompatActivity {


    GameUIControl gameUIControl;
    IChessBoardControl chessBoardControl;
    Button autoPlay, forward, backward, reverseView, mvs,anl;
    MoveContainer moveContainer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chessBoardControl = findViewById(R.id.chessBoardControl);
        //String fen = "rnbqkb1r/pp2pppp/3p1n2/8/3NP3/8/PPP2PPP/R1BQKBNR w KQkq - 0 6";
        IGame game = new Game( chessBoardControl);
         gameUIControl = new GameUIControl(game);




        IPlayer player1 = new ChessAIEngine(getBaseContext(),  gameUIControl,PieceColor.BLACK);
        IPlayer player2 = new Player(gameUIControl, PieceColor.BLACK);

        Random random = new Random();
        ((ChessBoardControl) chessBoardControl).checkMove = new CheckMove() {
            @Override
            public void isMovePlayedOnGame(IMove move) {

                gameUIControl.makeMove(move);

                /****************************************/

                /******************************************/


                List<IMove> moves = chessBoardControl.getAllPossibleMovesForCurrentPosition();
                int min = 0;
                int max = moves.size()-1;
                int i = random.nextInt(max - min + 1) + min;
                IMove m = moves.get(i);

                if (move.getPiece().getPieceColor()==PieceColor.WHITE) player1.setColor(PieceColor.BLACK);
                else player1.setColor(PieceColor.WHITE);
                gameUIControl.getToPosition(move.getPositionAfterMoveExecuted());
                //gameUIControl.makeMove(m);
                player1.makeMove();




            }
        };



        autoPlay = findViewById(R.id.autoPlay);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        reverseView = findViewById(R.id.reverseView);
        mvs = findViewById(R.id.mvs);
        moveContainer = findViewById(R.id.moveContainer);
        anl = findViewById(R.id.anl);


    }


    @Override
    protected void onStart() {
        super.onStart();
        autoPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                chessBoardControl.randomlyPlay(gameUIControl);
                List<MoveChipData> dts = gameUIControl.getAllLines();
                String res = "";
                for (MoveChipData d : dts
                ) {
                    res += d.startTag;
                    res += d.moveSymbol;
                    res += d.endTag;
                    res += "  ";
                }

                System.out.println(res);
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                gameUIControl.next();


            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                gameUIControl.back();

            }
        });
        reverseView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                chessBoardControl.tooglePlayerView();

            }
        });
        mvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<MoveChipData> chipDatas = gameUIControl.getAllLines();
                for (MoveChipData d : chipDatas
                ) {
                    MoveChip chip = new MoveChip(getBaseContext()).setData(d);
                    moveContainer.addMoveChip(chip);
                }
            }
        });
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              IChessBoard  chessBoard = new MiniChessBoard();
                IGame game = new Game( chessBoardControl);
                List<IMove> mvs;
                IMove m = game.getLastExecutedMove();
                for (int i = 0; i < 6; i++) {
                    long startTime = System.nanoTime();
                    setAllVariants(game,m, i);
                    mvs =   findVariantLeafs(m);
                    long endTime = System.nanoTime(); // Döngü sonu zamanını kaydet
                    long duration = (endTime - startTime) / 1000000; // Zaman farkını hesapla ve milisaniyeye çevir
                    System.out.println("Döngü süresi: " + duration + " milisaniye");
                    System.out.println( String.valueOf(i)+"derinligi icin olasi variant sayisi : "+ String.valueOf( mvs.size() ) );
                }
            }
        });


        moveContainer.setClickListener(new MoveItemClickListener() {
            @Override
            public void execute(IPosition position) {
                gameUIControl.getToPosition(position);
                System.out.println(position.getPositionInString());
            }
        });

        IPosition position = gameUIControl.getLastPosition();

    }



    static void setAllVariants(IGame game,IMove move, int deep) {
        // if (!(move instanceof EmptyMove)) throw  new RuntimeException("");
        if (deep == 0) return;
        game.getToPosition(move.getPositionAfterMoveExecuted());

        List<IMove> mvs =  game.getAvaibleMovesForCurrentPosition();
        for (IMove m : mvs
        ) {
            game.makeMove(m);
            game.back();
        }

        {
            move = move.getNext();

            List<IMove> moves = new ArrayList<>();
            moves.add(move);

            moves.addAll(move.getVariants());
            for (IMove m : moves
            ) {
                setAllVariants( game, m, deep - 1);
            }
        }


    }
    static List<IMove> findVariantLeafs(IMove move) {

        if (move.getNext()==null) {
            List<IMove> leafs = new ArrayList<>();
            leafs.add(move);
            //leafs.addAll(move.getVariants());
            return leafs;
        }


        List<IMove> leafs = new ArrayList<>();
        move = move.getNext();
        List<IMove> arr = new ArrayList<>();
        arr.add(move);arr.addAll(move.getVariants());
        for (IMove mv: arr) {
            List<IMove> r = findVariantLeafs(mv);
            leafs.addAll(r);
        }

        return leafs;

    }
}