package chess.backend.codes.concretes.Game;

import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IPlayer;
import chess.backend.codes.abstractions.PieceColor;

public class Player extends APlayer {
    public Player(IGame game, PieceColor color) {
        super(game, color);
    }

    @Override
    public void setColor(PieceColor color) {
        this.color = color;
    }
}
