package chess.backend.codes.concretes.Game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.concretes.moveTypes.EmptyMove;


public abstract class AGame implements IGame {
    @Override
    public List<IMove> getAvaibleMovesForCurrentPosition() {
        return chessBoard.getAllPossibleMovesForCurrentPosition();
    }

    protected IMove lastExecutedMove;
    protected IChessBoard chessBoard;




    public AGame(IChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }




    /**
     * oyundaki hamlelerin ilkini getir.
     */
    protected EmptyMove getFirstNode() {
        IMove m = lastExecutedMove;
        while (!(m instanceof EmptyMove)) {
            m = m.getPrevious();
        }
        return (EmptyMove) m;
    }

    private String getAllLines(IMove firstNode) {
        StringBuilder res = new StringBuilder();
        IMove m = firstNode;
        while (m.getNext() != null) {
            res.append(m.getMoveSymbol()).append(" ");
            if (!m.getVariants().isEmpty()) {
                res.append("\n").append("[");
                for (IMove vr : m.getVariants()) {
                    res.append(getAllLines(vr));
                }
                res.append("]").append("\n");
            }
            m = m.getNext();
        }

        res.append(m.getMoveSymbol());
        if (!m.getVariants().isEmpty()) {
            res.append("\n").append("[");
            for (IMove vr : m.getVariants()) {
                res.append(getAllLines(vr));
            }
            res.append("]").append("\n");
        }
        res.append(";");
        return res.toString();
    }

    public String getAllLines() {
        return getAllLines(getFirstNode());
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void makeMove(IMove move) {
        move.execute(chessBoard);
        // hamle yeni bir hamle ise
        if (addMove(move)) {
            lastExecutedMove.getPositionAfterMoveExecuted().setPreviousPosition(chessBoard.getCurrentPosition());
            chessBoard.getCurrentPosition().setNextPosition(lastExecutedMove.getPositionAfterMoveExecuted());

            lastExecutedMove.getPositionAfterMoveExecuted().setFenPosition(chessBoard.getPositionInFenFromBoard());
        }

        chessBoard.setCurrentPosition(lastExecutedMove.getPositionAfterMoveExecuted());


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean addMove(IMove move) {
        // son hamle nin next i yok ise bu  son konumun hamlesidir.
        if (lastExecutedMove.getNext() == null) {
            lastExecutedMove.setNext(move);
            move.setPrevious(lastExecutedMove);
            lastExecutedMove = move;
            return true;
        }
        // eğer son hamlenin next i var ise bu hamle, orta konumların bir  hamlesidir.
        // move1 move2 lastExeMove  move4 move5 ...
        // son execute edilen hamleden sonraki hamlenin bir varyantı olabilir.
        IMove moveForVariants = lastExecutedMove.getNext();
        List<IMove> variants = moveForVariants.getVariants();
        if (variants == null) {
            moveForVariants.addVariantMove(move);
            lastExecutedMove = move;
            return true;
        }
        IMove move_varyantta_varmi_yokmi = variants.stream().filter(m -> m.getMoveSymbol().equals(move.getMoveSymbol())).findFirst().orElse(null);
        // varyantlarda bu hamle var ise :
        if (move_varyantta_varmi_yokmi != null) {
            lastExecutedMove = move_varyantta_varmi_yokmi;
            // şimdi  bu hamle var  lastexecuted bu var olan hamle yaptık. yani gelen move nesnesi bellekte bir sure tutunur sonra yok olur. olusturdugu position ile birlikte.
            return false;
        }
        // varyantlarda bu hamle yok ise :
        else {
            moveForVariants.addVariantMove(move);
            lastExecutedMove = move;
            return true;
        }


    }

    public void back( ) {
        if (lastExecutedMove instanceof EmptyMove) {
            return;
        }
        lastExecutedMove.getBackMove(chessBoard);
        lastExecutedMove = lastExecutedMove.getPrevious();
        chessBoard.setCurrentPosition(lastExecutedMove.getPositionAfterMoveExecuted());
    }

    public void next( ) {
        if (lastExecutedMove.getNext() == null) {
            return;
        }

        lastExecutedMove.getNext().simpleExecute(chessBoard);
        lastExecutedMove = lastExecutedMove.getNext();
        chessBoard.setCurrentPosition(lastExecutedMove.getPositionAfterMoveExecuted());
    }

    public void backBack( ) {
        while (!(lastExecutedMove instanceof EmptyMove)) {
            back();
        }
    }

    public void nextNext( ) {
        while (lastExecutedMove.getNext() != null) {
            next();
        }
    }

    public IPosition getLastPosition() {
        return lastExecutedMove.getPositionAfterMoveExecuted();
    }
}