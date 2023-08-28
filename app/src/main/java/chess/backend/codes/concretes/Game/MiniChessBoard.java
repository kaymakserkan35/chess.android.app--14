package chess.backend.codes.concretes.Game;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.betelgeuse.chessai.board.ICellControl;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Pieces.PieceType;
import chess.backend.codes.concretes.moveTypes.CastlingMove;
import chess.backend.codes.concretes.moveTypes.PromotionMove;
import chess.backend.codes.concretes.moveTypes.SimpleMove;

public class MiniChessBoard extends AChessBoard {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean canControl(IPiece piece, ICoordinate coordinate) {
        return isMoveLegal(piece.getCurrentCoor(),coordinate);
    }

    @Override
    public IPosition getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void setCurrentPosition(IPosition currentPosition) {
        this.currentPosition = currentPosition;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<IMove> getAvailableMoves(IPiece piece) {
        Log.w("",getPositionInFenFromBoard() );
        if (piece == null) throw  new RuntimeException();
        if (piece.getCurrentCoor() == null) throw new RuntimeException();
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
        List<int[][]> returningVal = new ArrayList<>();
        for (IMove m: moves
        ) {
            int[][] arrays = new int[2][2];
            arrays[0][0] = m.getFromCoor().get_X();
            arrays[0][1] = m.getFromCoor().get_Y();
            arrays[1][0] = m.getToCoor().get_X();
            arrays[1][1] = m.getToCoor().get_Y();
            returningVal.add(arrays);
        }
        return returningVal;
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
                moves.addAll(getAvailableMoves(blackPieces.get(i)));
            }
        }

        return moves;
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean isMoveLegal(ICoordinate current, ICoordinate targetCoor) {
        PieceColor whosTurn = currentPosition.getWhosTurn();
        IPiece piece = getCell(current.get_X(), current.get_Y()).getPiece();

        if (piece == null) return false;
        if (piece.getCurrentCoor() == null) return false;
        if (whosTurn != piece.getPieceColor()) return false;
        boolean tf = checkMoveIsLegal(piece, targetCoor);
        if (!tf) return false;

        IPosition p = currentPosition;
        IMove move = null;
        move = generateMove(current, targetCoor);
        move.execute(this);
        this.currentPosition = move.getPositionAfterMoveExecuted();
        IPiece whiteKing = whitePieces.stream().filter(bp -> bp instanceof IKing).findFirst().orElse(null);
        if (whiteKing == null || whiteKing.getCurrentCoor() == null)  throw new RuntimeException();

        IPiece blackKing = blackPieces.stream().filter(bp -> bp instanceof IKing).findFirst().orElse(null);
        if (blackKing == null || blackKing.getCurrentCoor() == null) throw new RuntimeException();


        boolean whiteKingWillKill,blackKingWillKill;

        blackKingWillKill = whitePieces.stream().anyMatch(x -> {
            assert blackKing != null;
            return checkMoveIsLegal(x, blackKing.getCurrentCoor());
        });

        whiteKingWillKill = blackPieces.stream().anyMatch(x -> {
            assert whiteKing != null;
            return checkMoveIsLegal(x, whiteKing.getCurrentCoor());
        });

        boolean anyKingWillKill  = (whiteKingWillKill ||  blackKingWillKill );

        move.getBackMove(this);
        move = null;
        this.currentPosition = p;
        p.setNextPosition(null);



        if (piece.getPieceColor() == PieceColor.WHITE) return !whiteKingWillKill;
        if (piece.getPieceColor()== PieceColor.BLACK) return !blackKingWillKill;

        return false;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isCheckMove(IMove move){
        IPosition p = currentPosition;
        move.execute(this);
        this.currentPosition = move.getPositionAfterMoveExecuted();
        IPiece whiteKing = whitePieces.stream().filter(bp -> bp instanceof IKing).findFirst().orElse(null);
        if (whiteKing == null || whiteKing.getCurrentCoor() == null)  throw new RuntimeException();

        IPiece blackKing = blackPieces.stream().filter(bp -> bp instanceof IKing).findFirst().orElse(null);
        if (blackKing == null || blackKing.getCurrentCoor() == null) throw new RuntimeException();

        boolean whiteKingWillKill,blackKingWillKill;

        if (move.getPiece().getPieceColor() == PieceColor.WHITE){
            blackKingWillKill = whitePieces.stream().anyMatch(x -> {
                return checkMoveIsLegal(x, blackKing.getCurrentCoor());
            });
            this.currentPosition = p;
            p.setNextPosition(null);

            return  blackKingWillKill;
        }
        else    {
            whiteKingWillKill = blackPieces.stream().anyMatch(x -> {
                return checkMoveIsLegal(x, whiteKing.getCurrentCoor());
            });
            this.currentPosition = p;
            p.setNextPosition(null);

            return  whiteKingWillKill;
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public IMove generateMove(ICoordinate current, ICoordinate targetCoor) {

        IMove move;
        IPiece piece = getCell(current.get_X(), current.get_Y()).getPiece();
        IPiece targetPiece = getTargetPiece(piece, targetCoor);

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
    public ICell getCell(ICoordinate coordinate) {
        return getCell(coordinate.get_X(), coordinate.get_Y());
    }

    @Override
    public List<IPiece> getPieces() {
        ArrayList<IPiece> pieces =  new ArrayList<IPiece>();
        pieces.addAll(whitePieces);
        pieces.addAll(blackPieces);
        return  pieces;
    }

}
