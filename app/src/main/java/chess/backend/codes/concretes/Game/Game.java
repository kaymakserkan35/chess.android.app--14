package chess.backend.codes.concretes.Game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.concretes.moveTypes.EmptyMove;

public class Game extends AGame {


    public Game(IChessBoard chessBoard) {
        super(chessBoard);
        Position p = new Position();
        lastExecutedMove = new EmptyMove(p);
        chessBoard.loadPosition(p);
    }

    public Game(String fenPosition, IChessBoard chessBoard) {
        super(chessBoard);
        Position p = new Position(fenPosition);
        lastExecutedMove = new EmptyMove(p);
        chessBoard.loadPosition(p);
    }



    @Override
    public IMove getLastExecutedMove() {
        return lastExecutedMove;
    }

    @Override
    public IMove getNode(IMove move) {
        IMove m = move.getPrevious();
        if (m.getNext() != move) return m;
        else return getNode(m);
    }

    @Override
    public void setMainLine(IMove move) {
        IMove node = getNode(move);
        node.setNext(move);
    }

    private IMove getPositionsMove(  IMove move ,IPosition position) {
        if (move == null) return  null;
        if (move.getPositionAfterMoveExecuted().equals(position)) return move;
        if ( move.getVariants() != null  &&  ! move.getVariants().isEmpty()){
            for (IMove m: move.getVariants()
            ) {
                IMove result =  getPositionsMove(m,position);
                if (result != null) return result;
            }
        }
        return getPositionsMove(move.getNext(),position);
    }

    private IMove getPositionsMove(IPosition position) {
        IMove m =   getPositionsMove(getFirstNode(),position);
        if (m==null) throw  new RuntimeException();
        if (!m.getPositionAfterMoveExecuted().getPositionInString().equals(position.getPositionInString())) throw  new RuntimeException();
        return m;
    }


    public void getToPosition(IPosition position) {

        Stack<IMove> stack = new Stack<>();
        IMove move = getPositionsMove(position);
        // konuz zaten baslangıc konumudur. empyty move baslangıcı temsil ediyor. dogrudan buradan don. yoksa empyti move back ediyor ama geri oynatmıyor.



        while (move.getPrevious()!=null){
            stack.push(move);
            move = move.getPrevious();
        } // empyty move için :
        stack.push(move);

        backBack();

        while (!stack.isEmpty()) {
            IMove m = stack.pop();
            m.simpleExecute(chessBoard);
            this.lastExecutedMove = m;

            //normal executee de bu kod farklılık yaratır. cunki, hamleye yeni bir after position ekleniyor.
            chessBoard.setCurrentPosition(m.getPositionAfterMoveExecuted());
        }

    }
    public void getToPosition222(IPosition position) {

        Stack<IMove> stack = new Stack<>();
        IMove move = getPositionsMove(position);


    }
}