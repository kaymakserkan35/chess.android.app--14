package com.betelgeuse.chessai.engines;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.Piece;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Game.Game;
import chess.backend.codes.concretes.Game.MiniChessBoard;
import chess.backend.codes.concretes.Game.Position;
import chess.backend.codes.concretes.Pieces.PieceType;

public class ChessPositionAnalyzer {
    private IChessBoard chessBoard = null;
    private IGame game = null;
    private Dictionary<String, int[][]> dictionary = null;

    public ChessPositionAnalyzer() {

        chessBoard = new MiniChessBoard();

        dictionary = new Hashtable<>();

    }

    /**
     * sadece bir konum için her taşın tahtadaki hakimiyetlerini getirir.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<int[][]> analyse(String fen) {
        chessBoard.loadPosition(new Position(fen));
        PieceType[] types = PieceType.values();
        for (int i = 0; i < types.length; i++) {
            PieceType type = types[i];

            List<Piece.IPiece> pieces = chessBoard.getPieces().stream().filter(x -> x.getPieceColor() == PieceColor.WHITE && x.getType() == type).collect(Collectors.toList());
            int[][] v = analyse(pieces);
            dictionary.put(String.valueOf(pieces.get(0).getSymbol()), v);

            pieces = chessBoard.getPieces().stream().filter(x -> x.getPieceColor() == PieceColor.BLACK && x.getType() == type).collect(Collectors.toList());
            v = analyse(pieces);
            dictionary.put(String.valueOf(pieces.get(0).getSymbol()), v);
        }
        System.out.println("");
        /*-------------------------------------------*/
        String[] order = {"P", "R", "N", "B", "Q", "K", "p", "r", "n", "b", "q", "k"};
        List<int[][]> positionValues = new ArrayList<>();
        for (String key : order) {
            positionValues.add(dictionary.get(key));
        }
        System.out.println("");
        return positionValues;
    }

    // sadece bir tas türü için konum analizi yapar. mesela beyaz 8 piyon için....
    private int[][] analyse(List<Piece.IPiece> pieces) {
        PieceType type = null;
        for (Piece.IPiece p : pieces
        ) {
            if (type == null) {
                type = p.getType();
                continue;
            }
            if (type != p.getType()) throw new RuntimeException("");
        }
        int[][] vals = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int val = 0;
                int x_ind = x + 1;
                int y_ind = y + 1;
                ICell cell = chessBoard.getCell(x_ind, y_ind);
                for (Piece.IPiece p : pieces) {

                    if (p.getCurrentCoor() == null) continue;
                    if (p.getCurrentCoor().get_X() == cell.get_X() && p.getCurrentCoor().get_Y() == cell.get_Y()) {
                        if (p instanceof Piece.IKing && chessBoard.getCurrentPosition().getWhosTurn() == p.getPieceColor())
                            val += 2 * posVal(p);
                        else val += posVal(p);

                        continue;
                    }

                    if (p.canMove(cell)) val += 1;
                    if (chessBoard.canControl(p, cell)) val += 5;
                }

                vals[7 - y][x] = val;
            }

        }

        return vals;
    }

    private int posVal(Piece.IPiece p) {
        PieceType type = p.getType();
        if (type == PieceType.IPawn) return 10;
        if (type == PieceType.IRook) return 50;
        if (type == PieceType.IKnight) return 30;
        if (type == PieceType.IBishop) return 35;
        if (type == PieceType.IQueen) return 90;
        if (type == PieceType.IKing) return 100;
        return 0;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<List<int[][]>> analyze(String fen) {
        String[] fields = fen.split(" ");
        if (fields.length<2) throw  new RuntimeException("need full fen!!");
        List<List<int[][]>> allMovesPositions = new ArrayList<>();
        this.game = new Game(fen, chessBoard);
        setAllVariants(game.getLastExecutedMove(),1);
        List<IMove> mvs =findVariantLeafs(game.getLastExecutedMove());


        for (IMove m: mvs) {
            // burada butun hamleler yapıldı. ve ai hamlelr yapıldıktan sonraki konumları degerlendiriyor. yani ai siyah ise, annaliz edien konumlarda hamle sirasi beyazda. ve tam tersi!
            List<int[][]> pos =  analyse(m.getPositionAfterMoveExecuted().getFullFen());
            allMovesPositions.add(pos);
        }
        return allMovesPositions;
    }


    void setAllVariants(IMove move, int deep) {
        if (deep == 0) return;
        game.getToPosition(move.getPositionAfterMoveExecuted());

        List<IMove> mvs = chessBoard.getAllPossibleMovesForCurrentPosition();
        for (IMove m : mvs) { game.makeMove(m);game.back(); }
        {
            move = move.getNext();
            List<IMove> moves = new ArrayList<>();
            moves.add(move);

            moves.addAll(move.getVariants());
            for (IMove m : moves
            ) {
                setAllVariants(m, deep - 1);
            }
        }
    }
    List<IMove> findVariantLeafs(IMove move) {

        if (move.getNext()==null) {
            List<IMove> leafs = new ArrayList<>();
            leafs.add(move);
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
