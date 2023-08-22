package com.betelgeuse.chessai.board;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.betelgeuse.chessai.Listeners.CheckMove;
import com.betelgeuse.chessai.Listeners.PieceDragStartedListener;
import com.betelgeuse.chessai.Listeners.PieceDroppedListener;
import com.betelgeuse.chessai.R;
import com.betelgeuse.chessai.chess.pieces.Bishop;
import com.betelgeuse.chessai.chess.pieces.IPieceControl;
import com.betelgeuse.chessai.chess.pieces.King;
import com.betelgeuse.chessai.chess.pieces.Knight;
import com.betelgeuse.chessai.chess.pieces.Pawn;
import com.betelgeuse.chessai.chess.pieces.PieceControl;
import com.betelgeuse.chessai.chess.pieces.Queen;
import com.betelgeuse.chessai.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Game.Game;
import chess.backend.codes.concretes.Game.MiniChessBoard;
import chess.backend.codes.concretes.Pieces.PieceType;
import chess.backend.codes.concretes.moveTypes.CastlingMove;
import chess.backend.codes.concretes.moveTypes.PromotionMove;
import chess.backend.codes.concretes.moveTypes.SimpleMove;


public class ChessBoardControl extends FrameLayout implements IChessBoardControl, PieceDragStartedListener, PieceDroppedListener {
    static String TAG = "ChessBoardControl";
     public  CheckMove checkMove = null;
    protected IPosition currentPosition;
    PieceColor playerView = PieceColor.WHITE;
    List<com.betelgeuse.chessai.board.ICellControl> cells = new ArrayList<>();
    List<IPieceControl> pieces = new ArrayList<>();
    protected List<IPiece> whitePieces = new ArrayList<>(), blackPieces = new ArrayList<>();
    MiniChessBoard miniChessBoard;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ChessBoardControl(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.board, this);
        init();
        //LayoutInflater.from(context).inflate(R.layout.board, this, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ChessBoardControl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.board, this);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ChessBoardControl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.board, this);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ChessBoardControl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.board, this);
        init();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setPlayerView(PieceColor color) {

        int finalX = 0;
        int finalY = 9;
        if (color == PieceColor.BLACK) {
            finalX = 9;
            finalY = 0;
        }
        GridLayout chessboardLayout = findViewById(R.id.chessboard);
        chessboardLayout.removeAllViews();
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {

                final int final_x = Math.abs(finalX - x);
                final int final_y = Math.abs(finalY - y);
                CellControl c = (CellControl) cells.stream().filter(m -> m.get_X() == final_x && m.get_Y() == final_y).findFirst().get();
                chessboardLayout.addView(c);
            }
        }
        chessboardLayout.invalidate();
        if (finalX == 9) finalX = 0;
        else if (finalX == 0) finalX = 9;


        List<IPiece> pieces = miniChessBoard.getPieces();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void tooglePlayerView() {

        playerView = (playerView == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        setPlayerView(playerView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init() {
        GridLayout chessboardLayout = findViewById(R.id.chessboard);
        miniChessBoard = new MiniChessBoard();
        IGame game = new Game(miniChessBoard);


        List<IPiece> pieces = miniChessBoard.getPieces();

        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                CellControl chessCell = new CellControl(getContext(), Math.abs(x), Math.abs(y));
                cells.add(chessCell);
            }
        }

        for (IPiece p : pieces) {
            PieceControl piece = createPiece(p);
            if (piece.getPieceColor() == PieceColor.WHITE) {
                whitePieces.add(piece);
            } else blackPieces.add(piece);

            this.pieces.add(piece);
            getCell(p.getCurrentCoor().get_X(), p.getCurrentCoor().get_Y()).addPiece(piece);


            for (ICellControl c : cells
            ) {
                c.setPieceDroppedListener(this);
            }
            piece.setAPieceDragStartedListener(this);

        }
        setPlayerView(PieceColor.WHITE);

    }


    private PieceControl createPiece(IPiece piece) {
        if (piece.getType() == PieceType.IBishop) {
            return new Bishop(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        if (piece.getType() == PieceType.IPawn) {
            return new Pawn(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        if (piece.getType() == PieceType.IKnight) {
            return new Knight(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        if (piece.getType() == PieceType.IRook) {
            return new Rook(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        if (piece.getType() == PieceType.IQueen) {
            return new Queen(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        if (piece.getType() == PieceType.IKing) {
            return new King(getContext(), piece.getCurrentCoor(), piece.getPieceColor());
        }
        return null;

    }

    @Override
    public boolean isCheckMove(IMove move) {throw  new RuntimeException();
    }

    @Override
    public boolean canControl(IPiece piece, ICoordinate coordinate) {
        throw new RuntimeException();
    }

    @Override
    public IPosition getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public void setCurrentPosition(IPosition currentPosition) {
        this.currentPosition = currentPosition;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadPosition(IPosition position) {
        miniChessBoard.loadPosition(position);
        clearBoard();
        currentPosition = position;

        String fen = position.getPositionInString();
        int count = 0;
        for (int y = 1; y <= 8; y++) {

            for (int x = 1; x <= 8; x++) {
                char c = fen.charAt(count);
                count++;
                switch (c) {
                    /*----------------------------------------------------------------------------*/
                    case 'K': {
                        ICell cell = getCell(x, y);
                        IKing piece = (IKing) whitePieces.stream().filter(piece1 -> piece1.getCurrentCoor() == null && piece1.getType() == PieceType.IKing).findFirst().get();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'k': {
                        ICell cell = getCell(x, y);
                        IKing piece = (IKing) blackPieces.stream().filter(piece1 -> piece1.getCurrentCoor() == null && piece1.getType() == PieceType.IKing).findFirst().get();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'p': {
                        ICell cell = getCell(x, y);
                        IPawn piece = (IPawn) blackPieces.stream().filter(piece1 -> piece1.getCurrentCoor() == null && piece1.getType() == PieceType.IPawn).findFirst().get();
                        piece.dePromote();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'P': {
                        ICell cell = getCell(x, y);
                        IPawn piece = (IPawn) whitePieces.stream().filter(piece1 -> piece1.getCurrentCoor() == null && piece1.getType() == PieceType.IPawn).findFirst().get();
                        piece.dePromote();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }

                    /*----------------------------------------------------------------------------*/
                    case 'r': {
                        ICell cell = getCell(x, y);
                        IPiece piece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IRook && p.getType() == PieceType.IRook).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IRook);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'n': {
                        ICell cell = getCell(x, y);
                        IPiece piece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IKnight && p.getType() == PieceType.IKnight).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IKnight);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'b': {
                        ICell cell = getCell(x, y);
                        IPiece piece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IBishop && p.getType() == PieceType.IBishop).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IBishop);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'q': {
                        ICell cell = getCell(x, y);
                        IPiece piece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IQueen && p.getType() == PieceType.IQueen).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = blackPieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IQueen);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }



                    /*----------------------------------------------------------------------------*/


                    case 'R': {
                        ICell cell = getCell(x, y);
                        IPiece piece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IRook && p.getType() == PieceType.IRook).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IRook);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'N': {
                        ICell cell = getCell(x, y);
                        IPiece piece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IKnight && p.getType() == PieceType.IKnight).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IKnight);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'B': {
                        ICell cell = getCell(x, y);
                        IPiece piece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IBishop && p.getType() == PieceType.IBishop).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IBishop);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case 'Q': {
                        ICell cell = getCell(x, y);
                        IPiece piece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IQueen && p.getType() == PieceType.IQueen).findFirst().orElseGet(() -> {
                            IPiece pawnPiece = whitePieces.stream().filter(p -> p.getCurrentCoor() == null && p instanceof IPawn).findFirst().get();
                            ((IPawn) pawnPiece).promote(PieceType.IQueen);
                            return pawnPiece;
                        });
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case '1': {
                        continue;
                    }

                    default:
                        throw new RuntimeException("");
                }
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String getPositionInFenFromBoard() {
        StringBuilder fen = new StringBuilder();

        for (int y = 8; y > 0; y--) {
            int sum = 0;
            for (int x = 1; x <= 8; x++) {
                IPiece piece = getCell(x, y).getPiece();

                if (piece == null) {
                    sum += 1;
                } else {
                    if (sum != 0) {
                        fen.append(sum);
                        sum = 0;
                    }
                    fen.append(piece.getSymbol());
                }
            }

            if (sum != 0) {
                fen.append(sum);
                sum = 0;
            }

            if (y != 1) fen.append("/");
        }

        return fen.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public IMove generateMove(ICoordinate current, ICoordinate targetCoor) {

        IMove move;
        IPiece piece = getCell(current.get_X(), current.get_Y()).getPiece();
        @Nullable IPiece targetPiece = getTargetPiece(piece, targetCoor);

        if (piece.getType() == PieceType.IPawn && (targetCoor.get_Y() == 1 || targetCoor.get_Y() == 8)) {
            move = new PromotionMove((IPawn) piece, targetCoor, targetPiece, PieceType.IQueen);
        } else if (targetPiece != null && targetPiece.getPieceColor() == piece.getPieceColor()) {
            move = new CastlingMove(piece, targetCoor, targetPiece);
        } else {
            move = new SimpleMove(piece, targetCoor, targetPiece);
        }


        return move;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<IMove> getAvailableMoves(IPiece piece) {
        System.out.println(currentPosition.getFullFen());
        if (piece.getCurrentCoor() == null) {
           throw  new RuntimeException("");
        }
        if (currentPosition.getEnpassent()!=null){
            System.out.println("");
        }
        List<IMove> moves = new ArrayList<>();
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {

                ICell targetCoordinate = getCell(x, y);

                if (!isMoveLegal(piece.getCurrentCoor(), targetCoordinate)) {
                    continue;
                }
                IMove move = generateMove(piece.getCurrentCoor(), targetCoordinate);
                moves.add(move);
                // default olarak vezir çıkışı vardır.
                if (move instanceof PromotionMove) {
                    IMove m;
                    m = new PromotionMove((IPawn) piece, targetCoordinate, getTargetPiece(piece, targetCoordinate), PieceType.IRook);
                    moves.add(m);
                    m = new PromotionMove((IPawn) piece, targetCoordinate, getTargetPiece(piece, targetCoordinate), PieceType.IKnight);
                    moves.add(m);
                    m = new PromotionMove((IPawn) piece, targetCoordinate, getTargetPiece(piece, targetCoordinate), PieceType.IBishop);
                    moves.add(m);
                }
            }
        }
        return moves;
    }

    @Override
    public List<int[][]> getAvailableMoves(List<IMove> moves) {
        throw new RuntimeException("");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<IMove> getAllPossibleMovesForCurrentPosition() {

        List<IMove> moves = new ArrayList<>();

        if (this.currentPosition.getWhosTurn() == PieceColor.WHITE) {
            for (int i = 0; i < whitePieces.size(); i++) {
                IPiece p = whitePieces.get(i);
                if (p.getCurrentCoor() == null) continue;
                List<IMove> movs = getAvailableMoves(whitePieces.get(i));
                moves.addAll(movs);
            }
        } else {
            for (int i = 0; i < blackPieces.size(); i++) {
                IPiece p = blackPieces.get(i);
                if (p.getCurrentCoor() == null) continue;
                List<IMove> mvs = getAvailableMoves(blackPieces.get(i));
                moves.addAll(mvs);
            }
        }

        return moves;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean isMoveLegal(ICoordinate current, ICoordinate targetCoor) {
        miniChessBoard.loadPosition(this.getCurrentPosition());
        return miniChessBoard.isMoveLegal(current, targetCoor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ICell getCell(int x, int y) {
        return cells.stream().filter(m -> m.get_X() == x && m.get_Y() == y).findFirst().get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public @Nullable
    IPiece getTargetPiece(IPiece piece, ICoordinate targetCoor) {
        miniChessBoard.loadPosition(this.currentPosition);
        @Nullable IPiece p = miniChessBoard.getTargetPiece(piece, targetCoor);
        if (p != null)
            return getCell(p.getCurrentCoor().get_X(), p.getCurrentCoor().get_Y()).getPiece();
        else return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ICell getCell(ICoordinate coordinate) {
        return getCell(coordinate.get_X(), coordinate.get_Y());
    }

    @Override
    public List<IPiece> getPieces() {
        List<IPiece> returning = new ArrayList<>();
        for (IPieceControl p : pieces
        ) {
            returning.add((IPiece) p);
        }
        return returning;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void randomlyPlay(IGame game) {
        game.getLastPosition();
        IMove lastMove = null;
        List<IMove> moves = getAllPossibleMovesForCurrentPosition();
        if (moves.isEmpty()) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            lastMove = moves.stream().filter(m -> m instanceof CastlingMove).findFirst().orElse(null);
            if (lastMove == null)
                lastMove = moves.stream().filter(m -> m instanceof PromotionMove).findFirst().orElse(null);
            if (lastMove == null) {
                int a = random.nextInt(moves.size());
                lastMove = moves.get(a);
            }
            game.makeMove(lastMove);
            moves = getAllPossibleMovesForCurrentPosition();
            if (moves.isEmpty()) break;
        }
    }

    private void clearBoard() {
        for (ICellControl c : cells
        ) {
            c.removePiece();
        }
        for (IPieceControl p : this.pieces
        ) {
            p.setCurrentCoor(null);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void whenPieceStartDraggingOnCells(IPieceControl piece) {
        List<IMove> mvs = getAvailableMoves(piece);
        piece.setAvailableMoves(mvs);
        for (IMove m : mvs
        ) {
            ICellControl c = (ICellControl) getCell(m.getToCoor().get_X(), m.getToCoor().get_Y());
            c.setColor(Color.YELLOW);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void whenPieceDroppedOnSomeCell(ICellControl cell, IPieceControl pieceControl) {

        for (IMove m : pieceControl.getAvailableMoves()
        ) {
            ICellControl c = (ICellControl) getCell(m.getToCoor().get_X(), m.getToCoor().get_Y());
            c.defaultColor();

        }
        pieceControl.setAvailableMoves(null);



        boolean res = isMoveLegal(pieceControl.getCurrentCoor(), cell);
        if (!res) return ;
        if (checkMove == null) throw new RuntimeException();
        checkMove.isMovePlayedOnGame(generateMove(pieceControl.getCurrentCoor(), cell));
    }




}