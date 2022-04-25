package chess.repository.entity;

import chess.domain.board.Position;
import chess.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardEntity {

    private final int chessGameId;
    private final String positionColumnValue;
    private final int positionRowValue;
    private final String pieceName;
    private final String pieceTeamValue;

    public BoardEntity(int chessGameId,
                       String positionColumnValue,
                       int positionRowValue,
                       String pieceName,
                       String pieceTeamValue) {
        this.chessGameId = chessGameId;
        this.positionColumnValue = positionColumnValue;
        this.positionRowValue = positionRowValue;
        this.pieceName = pieceName;
        this.pieceTeamValue = pieceTeamValue;
    }

    public BoardEntity(int chessGameId,
                       char columnValue,
                       int rowValue,
                       Map<Position, Piece> currentBoard) {
        this.chessGameId = chessGameId;
        positionColumnValue = String.valueOf(columnValue);
        positionRowValue = rowValue;
        Piece piece = currentBoard.get(Position.of(columnValue, positionRowValue));
        pieceName = piece.getName();
        pieceTeamValue = piece.getTeam().getValue();
    }

    public static List<BoardEntity> generateBoardEntities(int chessGameId, Map<Position, Piece> board) {
        List<BoardEntity> boardEntities = new ArrayList<>();
        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            BoardEntity boardEntity = new BoardEntity(chessGameId,
                    String.valueOf(position.getColumn().getValue()),
                    position.getRow().getValue(),
                    piece.getName(),
                    piece.getTeam().getValue()
            );
            boardEntities.add(boardEntity);
        }
        return boardEntities;
    }

    public int getChessGameId() {
        return chessGameId;
    }

    public String getPositionColumnValue() {
        return positionColumnValue;
    }

    public int getPositionRowValue() {
        return positionRowValue;
    }

    public String getPieceName() {
        return pieceName;
    }

    public String getPieceTeamValue() {
        return pieceTeamValue;
    }
}
