package chess.backend.codes.concretes.Game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import chess.backend.codes.abstractions.CoorExtensions;
import chess.backend.codes.abstractions.ICell;
import chess.backend.codes.abstractions.IChessBoard;
import chess.backend.codes.abstractions.ICoordinate;
import chess.backend.codes.abstractions.IGame;
import chess.backend.codes.abstractions.IPosition;
import chess.backend.codes.abstractions.Piece.*;
import chess.backend.codes.abstractions.PieceColor;
import chess.backend.codes.concretes.Pieces.Bishop;
import chess.backend.codes.concretes.Pieces.King;
import chess.backend.codes.concretes.Pieces.Knight;
import chess.backend.codes.concretes.Pieces.Pawn;
import chess.backend.codes.concretes.Pieces.PieceType;
import chess.backend.codes.concretes.Pieces.Queen;
import chess.backend.codes.concretes.Pieces.Rook;

public abstract class AChessBoard implements IChessBoard {
    protected List<ICell> cells = new ArrayList<>();
    protected List<IPiece> whitePieces = new ArrayList<>(), blackPieces = new ArrayList<>();
    protected IPosition currentPosition;
   // protected IGame game;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public AChessBoard() {
        initializeBoard();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeBoard() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                cells.add(new Cell(x, y));
            }
        }
        IPiece p = new King(null, PieceColor.WHITE);
        whitePieces.add(p);
        p = new King(null, PieceColor.BLACK);
        blackPieces.add(p);
        p = new Queen(null, PieceColor.WHITE);
        whitePieces.add(p);
        p = new Queen(null, PieceColor.BLACK);
        blackPieces.add(p);

        for (int i = 0; i < 2; i++) {
            p = new Rook(null, PieceColor.WHITE);
            whitePieces.add(p);
            p = new Knight(null, PieceColor.WHITE);
            whitePieces.add(p);
            p = new Bishop(null, PieceColor.WHITE);
            whitePieces.add(p);

            p = new Rook(null, PieceColor.BLACK);
            blackPieces.add(p);
            p = new Knight(null, PieceColor.BLACK);
            blackPieces.add(p);
            p = new Bishop(null, PieceColor.BLACK);
            blackPieces.add(p);

        }


        for (int i = 0; i < 8; i++) {
            p = new Pawn(null, PieceColor.WHITE);
            whitePieces.add(p);
            p = new Pawn(null, PieceColor.BLACK);
            blackPieces.add(p);
        }

        loadPosition(new Position());

    }

    private void clearBoard() {
        for (ICell cell : cells) {
            cell.removePiece();
        }
        List<IPiece> pieces = new ArrayList<>();
        pieces.addAll(whitePieces);
        pieces.addAll(blackPieces);
        for (IPiece piece : pieces) {
            piece.setCurrentCoor(null);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadPosition(IPosition position) {
        clearBoard();
        currentPosition = position;

        String fen = position.getPositionInString();
        int count = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                char c = fen.charAt(count);
                count++;
                switch (c) {
                    case 'p': {
                        ICell cell = this.getCell(x, y);
                        IPawn piece = (IPawn) blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                .findFirst().orElse(null);
                        if (piece != null) {
                            piece.dePromote();
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'r': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IRook)
                                .findFirst()
                                .orElseGet(() -> blackPieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece.getType() == PieceType.IPawn) {
                                ((IPawn) piece).promote(PieceType.IRook);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'n': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IKnight)
                                .findFirst()
                                .orElseGet(() -> blackPieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece.getType() == PieceType.IPawn) {
                                ((IPawn) piece).promote(PieceType.IKnight);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'b': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IBishop)
                                .findFirst()
                                .orElseGet(() -> blackPieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece.getType() == PieceType.IPawn) {
                                ((IPawn) piece).promote(PieceType.IBishop);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'q': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IQueen)
                                .findFirst()
                                .orElseGet(() -> blackPieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece.getType() == PieceType.IPawn) {
                                ((IPawn) piece).promote(PieceType.IQueen);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'k': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = blackPieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IKing)
                                .findFirst().get();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    //---------------------------------------------------------------------------
                    case 'P': {
                        ICell cell = this.getCell(x, y);
                        IPawn piece = (IPawn) whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                .findFirst().orElse(null);
                        if (piece != null) {
                            piece.dePromote();
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'R': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IRook)
                                .findFirst()
                                .orElseGet(() -> whitePieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece instanceof IPawn) {
                                ((IPawn) piece).promote(PieceType.IRook);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'N': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IKnight)
                                .findFirst()
                                .orElseGet(() -> whitePieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece instanceof IPawn) {
                                ((IPawn) piece).promote(PieceType.IKnight);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'B': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IBishop)
                                .findFirst()
                                .orElseGet(() -> whitePieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece instanceof IPawn) {
                                ((IPawn) piece).promote(PieceType.IBishop);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'Q': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IQueen)
                                .findFirst()
                                .orElseGet(() -> whitePieces.stream()
                                        .filter(p -> p.getCurrentCoor() == null && p instanceof IPawn)
                                        .findFirst().orElse(null));
                        if (piece != null) {
                            if (piece instanceof IPawn) {
                                ((IPawn) piece).promote(PieceType.IQueen);
                            }
                            cell.addPiece(piece);
                            piece.setCurrentCoor(cell);
                        }
                        break;
                    }
                    case 'K': {
                        ICell cell = this.getCell(x, y);
                        IPiece piece = whitePieces.stream()
                                .filter(p -> p.getCurrentCoor() == null && p instanceof IKing)
                                .findFirst().get();
                        cell.addPiece(piece);
                        piece.setCurrentCoor(cell);
                        break;
                    }
                    case '1':
                        continue;
                    default:
                        throw new RuntimeException(c + "geldi ???");
                }
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForKnight(IPiece piece, ICoordinate targetCoor) {
        if ((piece.getType() != PieceType.IKnight)) throw new RuntimeException();
        if (!piece.canMove(targetCoor)) return false;
        IPiece p = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
        return p == null || p.getPieceColor() != piece.getPieceColor();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForRook(IPiece piece, ICoordinate targetCoor) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException();
        if (!(piece.getType() == PieceType.IRook) && !(piece.getType() == PieceType.IQueen))
            throw new RuntimeException();
        if (!piece.canMove(targetCoor)) return false;

        IPiece targetPiece = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
        if (targetPiece != null && piece.getPieceColor() == targetPiece.getPieceColor()) return false;

        if (piece.getCurrentCoor().get_X() == targetCoor.get_X()) {
            int x = piece.getCurrentCoor().get_X();
            int y = piece.getCurrentCoor().get_Y();

            while (y != targetCoor.get_Y()) {
                IPiece p = getCell(x, y).getPiece();
                if (p != piece && p != null) return false;
                if (piece.getCurrentCoor().get_Y() > targetCoor.get_Y()) y--;
                else y++;
            }
        }

        if (piece.getCurrentCoor().get_Y() == targetCoor.get_Y()) {
            int y = piece.getCurrentCoor().get_Y();
            int x = piece.getCurrentCoor().get_X();

            while (x != targetCoor.get_X()) {
                IPiece p = getCell(x, y).getPiece();
                if (p != piece && p != null) return false;
                if (piece.getCurrentCoor().get_X() > targetCoor.get_X()) x--;
                else x++;
            }
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForKing(IPiece piece, ICoordinate targetCoor) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException("");
        if (!(piece instanceof IKing)) throw new RuntimeException();

        IPiece targetPiece = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
        if (targetPiece != null && piece.getPieceColor() == targetPiece.getPieceColor()) return false;

        int y = piece.getCurrentCoor().get_Y();
        int x = piece.getCurrentCoor().get_X();

        if (Math.abs(piece.getCurrentCoor().get_X() - targetCoor.get_X()) == 2) {
            if (x < targetCoor.get_X()) {
                if (piece.getPieceColor() == PieceColor.WHITE && !currentPosition.isOoForWhite()) return false;
                if (piece.getPieceColor() == PieceColor.BLACK && !currentPosition.isOoForBlack()) return false;
            } else {
                if (piece.getPieceColor() == PieceColor.WHITE && !currentPosition.isOooForWhite()) return false;
                if (piece.getPieceColor() == PieceColor.BLACK && !currentPosition.isOooForBlack()) return false;
            }

            if (getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece() != null) return false;

            if (piece.getPieceColor() == PieceColor.WHITE && blackPieces.stream().anyMatch(p -> checkMoveIsLegal(p, getCell(piece.getCurrentCoor().get_X(), y))))
                return false;
            if (piece.getPieceColor() == PieceColor.BLACK && whitePieces.stream().anyMatch(p -> checkMoveIsLegal(p, getCell(piece.getCurrentCoor().get_X(), y))))
                return false;

            while (x != targetCoor.get_X()) {
                final int final_x = x;
                if (piece.getPieceColor() == PieceColor.WHITE) {
                    if (blackPieces.stream().anyMatch(p -> checkMoveIsLegal(p, getCell(final_x, y)))) return false;
                }
                if (piece.getPieceColor() == PieceColor.BLACK) {
                    if (whitePieces.stream().anyMatch(p -> checkMoveIsLegal(p, getCell(final_x, y)))) return false;
                }

                IPiece p = getCell(x, y).getPiece();
                if (p != piece && p != null) return false;

                if (piece.getCurrentCoor().get_X() > targetCoor.get_X()) x--;
                else x++;
            }

            return targetCoor.get_X() >= piece.getCurrentCoor().get_X() || (getCell(2, y).getPiece() == null);
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForBishop(IPiece piece, ICoordinate targetCoor) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException("");
        if (!(piece.getType() == PieceType.IBishop) && !(piece.getType() == PieceType.IQueen))
            throw new RuntimeException();

        IPiece targetPiece = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
        if (targetPiece != null && targetPiece.getPieceColor() == piece.getPieceColor()) return false;

        int x = piece.getCurrentCoor().get_X();
        int y = piece.getCurrentCoor().get_Y();

        while (x != targetCoor.get_X()) {
            IPiece p = getCell(x, y).getPiece();
            if (p != piece && p != null) return false;

            if (piece.getCurrentCoor().get_X() > targetCoor.get_X()) x--;
            else x++;

            if (piece.getCurrentCoor().get_Y() > targetCoor.get_Y()) y--;
            else y++;
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForQueen(IPiece piece, ICoordinate targetCoor) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException("");

        int currentX = piece.getCurrentCoor().get_X();
        int currentY = piece.getCurrentCoor().get_Y();

        if (currentX == targetCoor.get_X() || currentY == targetCoor.get_Y()) {
            return legalMoveForRook(piece, targetCoor);
        } else {
            return legalMoveForBishop(piece, targetCoor);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected boolean legalMoveForPawn(IPiece piece, ICoordinate targetCoor) {
        if (piece.getCurrentCoor() == null) throw new RuntimeException("");
        if (!(piece instanceof IPawn) || piece.getType() != PieceType.IPawn) throw new RuntimeException();

        int currentX = piece.getCurrentCoor().get_X();
        int currentY = piece.getCurrentCoor().get_Y();

        if (currentX == targetCoor.get_X()) {
            int y = currentY;
            while (y != targetCoor.get_Y()) {
                if (y > targetCoor.get_Y()) y--;
                else y++;
                IPiece p = getCell(currentX, y).getPiece();
                if (p != null) return false;
            }
        } else {
            IPiece p = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
            if (p != null) {
                if (piece.getPieceColor() == p.getPieceColor()) return false;
                if (piece.getPieceColor() != p.getPieceColor()) return true;
            }

            if (currentPosition.getEnpassent() == null) return false;
            else {
                int[] xy = CoorExtensions.get(currentPosition.getEnpassent());
                ICoordinate coor = getCell(xy[0], xy[1]);

                if (targetCoor != coor) return false;
                return true;
            }
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean checkMoveIsLegal(IPiece piece, ICoordinate targetCoor) {

        if (piece.getCurrentCoor() == targetCoor) return false;
        if (!piece.canMove(targetCoor)) return false;
        if (piece.getType() == PieceType.IPawn) return legalMoveForPawn(piece, targetCoor);
        if (piece.getType() == PieceType.IRook) return legalMoveForRook(piece, targetCoor);
        if (piece.getType() == PieceType.IBishop) return legalMoveForBishop(piece, targetCoor);
        if (piece.getType() == PieceType.IQueen) return legalMoveForQueen(piece, targetCoor);
        if (piece.getType() == PieceType.IKnight) return legalMoveForKnight(piece, targetCoor);
        if (piece.getType() == PieceType.IKing) return legalMoveForKing(piece, targetCoor);

        throw new RuntimeException("");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ICell getCell(int x, int y) {
        return cells.stream()
                .filter(cell -> cell.get_X() == x && cell.get_Y() == y)
                .findFirst()
                .orElse(null);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getPositionInFenFromBoard() {
        if (cells.size()>64) throw new RuntimeException("");
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

        String returningVal =  fen.toString();


        char[]arr = returningVal.toCharArray();
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c != '1' && c != '2'  && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '/' ) count++;
        }
        if (count>32)
            throw  new RuntimeException();
        return returningVal;


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public IPiece getTargetPiece(IPiece piece, ICoordinate targetCoor) {

        if (this.getCurrentPosition().getEnpassent()!=null){
            System.out.println("");
        }
        if (piece.getCurrentCoor() == null) throw new RuntimeException();
        if (!piece.canMove(piece.getCurrentCoor(), targetCoor)) throw new RuntimeException();

        if (piece.getType() == PieceType.IPawn && piece.getCurrentCoor().get_X() != targetCoor.get_X()) {
            IPiece p = getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
            if (p != null) {
                return p;
            } else {
                if (currentPosition.getEnpassent() != null) {
                    int[] xy = CoorExtensions.get(currentPosition.getEnpassent());
                    ICoordinate c;
                    if (piece.getPieceColor() == PieceColor.WHITE) {
                        c = getCell(xy[0], xy[1] - 1);
                    } else {
                        c = getCell(xy[0], xy[1] + 1);
                    }
                    IPiece p2 = getCell(c.get_X(), c.get_Y()).getPiece();
                    if (p2 != null) {
                        return p2;
                    } else throw new RuntimeException();
                } else throw new RuntimeException();

            }
        }

        if (piece.getType() == PieceType.IKing && Math.abs(targetCoor.get_X() - piece.getCurrentCoor().get_X()) == 2) {
            IPiece p = null;
            if (piece.getPieceColor() == PieceColor.WHITE) {
                if (targetCoor.get_X() == 7) {
                    p = getCell(8, 1).getPiece();
                } else if (targetCoor.get_X() == 3) {
                    p = getCell(1, 1).getPiece();
                } else throw new RuntimeException();
            } else {
                if (targetCoor.get_X() == 7) {
                    p = getCell(8, 8).getPiece();
                } else if (targetCoor.get_X() == 3) {
                    p = getCell(1, 8).getPiece();
                } else throw new RuntimeException();
            }

            if (p == null) throw new RuntimeException();

            if (!(p.getType() == PieceType.IRook)) throw new RuntimeException();

            return p;
        } else {
            return getCell(targetCoor.get_X(), targetCoor.get_Y()).getPiece();
        }
    }

}