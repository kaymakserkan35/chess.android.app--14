package chess.backend.codes.concretes.Game;

import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IMove;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.PieceColor;

public abstract class APlayer implements IPlayer {
    PieceColor color;
    IGame game;

    @Override
    public void makeMove() {

    }

    public APlayer(IGame game, PieceColor color) {
        this.game = game;
        this.color = color;
    }
}
