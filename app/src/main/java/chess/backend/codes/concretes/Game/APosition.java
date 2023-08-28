package chess.backend.codes.concretes.Game;

import android.util.Log;

import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.PieceColor;

public abstract class APosition implements IPosition {

    IPosition nextPosition, previousPosition;
    protected String[] rowPositions = new String[8];
    protected PieceColor whosTurn;
    protected String enpassent;
    protected boolean ooForWhite;
    protected boolean oooForWhite;
    protected boolean ooForBlack;
    protected boolean oooForBlack;



    /** fen stringini bastan sona ver. kimin kazandigi ile birlikte.*/
    @Override
    public String getFullFen() {
        StringBuilder fenBuilder = new StringBuilder();

        for (String row : rowPositions) {
            fenBuilder.append(row).append("/");
        }
        // Son '/' karakterini kaldırın
        fenBuilder.setLength(fenBuilder.length() - 1);

        fenBuilder.append(" ");
        fenBuilder.append(whosTurn == PieceColor.WHITE ? "w" : "b");

        fenBuilder.append(" ");
        fenBuilder.append(ooForWhite ? "K" : "");
        fenBuilder.append(oooForWhite ? "Q" : "");
        fenBuilder.append(ooForBlack ? "k" : "");
        fenBuilder.append(oooForBlack ? "q" : "");

        if (!ooForWhite && !oooForWhite && !ooForBlack && !oooForBlack)
            fenBuilder.append("-");


        fenBuilder.append(" ");

        if (enpassent != null) fenBuilder.append(enpassent);
        else  fenBuilder.append("-");
        return fenBuilder.toString();
    }

    @Override
    public String getBoardFenRepresentation() {
        StringBuilder fenBuilder = new StringBuilder();
        for (String row : rowPositions) {
            fenBuilder.append(row).append("/");
        }
        // Son '/' karakterini kaldırın
        fenBuilder.setLength(fenBuilder.length() - 1);
        return fenBuilder.toString();
    }

    @Override
    public void setOooForWhite(boolean b) {
        this.oooForWhite = b;
    }

    @Override
    public void setOoForWhite(boolean b) {
        this.ooForWhite = b;
    }

    @Override
    public void setOoForBlack(boolean b) {
        this.ooForBlack = b;
    }

    @Override
    public void setOooForBlack(boolean b) {
        this.oooForBlack = b;
    }

    @Override
    public void setEnpassent( String enpassent) {
        this.enpassent = enpassent;
    }

    @Override
    public void setNextPosition(IPosition positionAfterMoveExecuted) {
        if (this.whosTurn==positionAfterMoveExecuted.getWhosTurn()) throw  new RuntimeException("");
        nextPosition = positionAfterMoveExecuted;

    }

    @Override
    public void setPreviousPosition(IPosition position) {
        if (this.whosTurn==position.getWhosTurn()) throw  new RuntimeException("");
        previousPosition = position;
    }

    public PieceColor getWhosTurn() {
        return whosTurn;
    }

    @Override
    public void setWhosTurn(PieceColor color) {
        whosTurn = color;
    }

    public String getEnpassent() {
        return enpassent;
    }

    public boolean isOoForWhite() {
        return ooForWhite;
    }

    public boolean isOooForWhite() {
        return oooForWhite;
    }

    public boolean isOoForBlack() {
        return ooForBlack;
    }

    public boolean isOooForBlack() {
        return oooForBlack;
    }

    public APosition() {
        rowPositions[0] = "rnbqkbnr";
        rowPositions[1] = "pppppppp";
        rowPositions[2] = "8";
        rowPositions[3] = "8";
        rowPositions[4] = "8";
        rowPositions[5] = "8";
        rowPositions[6] = "PPPPPPPP";
        rowPositions[7] = "RNBQKBNR";
        ooForWhite = true;
        oooForWhite = true;
        ooForBlack = true;
        oooForBlack = true;
        whosTurn = PieceColor.WHITE;
    }

    public APosition(String fenPos) {
        if (fenPos == null) throw new RuntimeException("");
        setFenPosition(fenPos);
        if (getBoardFenRepresentation() == null) throw  new RuntimeException();

    }

    public void setFenPosition(String fenPos) {
        String[] fields = fenPos.split(" ");
        rowPositions = fields[0].split("/");
        if (fields.length == 1) {
            return;
        }
        switch (fields[1]) {
            case "w":
                whosTurn = PieceColor.WHITE;
                break;
            case "b":
                whosTurn = PieceColor.BLACK;
                break;
        }

        String castleRights = fields[2];
        ooForWhite = castleRights.contains("K");
        oooForWhite = castleRights.contains("Q");
        ooForBlack = castleRights.contains("k");
        oooForBlack = castleRights.contains("q");
        if (fields.length<4) return;
        if (!fields[3].equals("-")) {
            if (!(fields[3].contains("3") || fields[3].contains("6"))) {
                throw new RuntimeException("");
            }
            enpassent = fields[3];
        } else {
            enpassent = null;
        }
    }

    public String useThisMethodJustWannaSeePositionInCode() {
        StringBuilder returningValue = new StringBuilder();
        returningValue.append("hamle : "+ whosTurn.toString()+" 'larin"+" \n");
        String[] arr = new String[8];
        for (int y = 7; y >= 0; y--) {
            StringBuilder res = new StringBuilder(" ");
            String row = rowPositions[y];

            for (int x = 0; x < row.length(); x++) {
                int num = 0;
                char c = row.charAt(x);
                if (Character.isDigit(c)) {
                    num = Character.getNumericValue(c);
                    for (int j = 0; j < num; j++) {
                        res.append(" 0 ");
                    }
                } else {
                    res.append(" ").append(c).append(" ");
                }
            }
            res.append(" \n");
            arr[y] = res.toString();
        }
        for (String s : arr) {
            returningValue.append(s);
        }

        return returningValue.toString();
    }

    public String getPositionInString() {
        StringBuilder res = new StringBuilder();
        for (int y = 7; y >= 0; y--) {
            String row = rowPositions[y];

            for (int x = 0; x < row.length(); x++) {
                int num = 0;
                char c = row.charAt(x);
                if (Character.isDigit(c)) {
                    num = Character.getNumericValue(c);
                    for (int j = 0; j < num; j++) {
                        res.append("1");
                    }
                } else {
                    res.append(c);
                }
            }
        }
        return res.toString();
    }

    public abstract APosition copyInShallowForNextPosition(String fen);
}