package chess.backend.codes.abstractions;

import androidx.annotation.Nullable;




public interface IPosition {
   PieceColor getWhosTurn();
   String getEnpassent();
   /** sadece tahta konumunu yansıtacak fen i ver.*/
   String getBoardFenRepresentation();
   String getFullFen();
   String useThisMethodJustWannaSeePositionInCode();
   boolean isOoForWhite();
   boolean isOooForWhite();
   boolean isOoForBlack();
   boolean isOooForBlack();
   void setOooForWhite(boolean b);
   void setOoForWhite(boolean b);
   void setOoForBlack(boolean b);
   void setOooForBlack(boolean b);
   IPosition copyInShallowForNextPosition(String fen);
   void setEnpassent( String enpassent);
   void setFenPosition(String fenPosition);
   void setNextPosition(IPosition positionAfterMoveExecuted);
   void setPreviousPosition(IPosition currentPosition);
   void  setWhosTurn(PieceColor color);
   String getPositionInString();
}