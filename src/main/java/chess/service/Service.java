package chess.service;

import chess.domain.game.ChessGame;
import chess.domain.game.GameRoom;
import chess.domain.game.GameSwitch;
import chess.domain.game.Turn;
import chess.domain.piece.Team;
import chess.repository.GameRoomDao;
import chess.repository.entity.BoardEntity;
import chess.repository.entity.ChessGameEntity;
import chess.repository.BoardDao;
import chess.repository.ChessGameDao;
import chess.repository.entity.GameRoomEntity;
import chess.service.util.BoardEntitiesToBoardConvertor;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final GameRoomDao gameRoomDao;
    private final ChessGameDao chessGameDao;
    private final BoardDao boardDao;

    public Service(GameRoomDao gameRoomDao, ChessGameDao chessGameDao, BoardDao boardDao) {
        this.gameRoomDao = gameRoomDao;
        this.chessGameDao = chessGameDao;
        this.boardDao = boardDao;
    }

    public void createGameRoom(final String gameRoomId, final String name, final String password) {
        GameRoom gameRoom = new GameRoom(gameRoomId, name, password, ChessGame.createBasic());
        gameRoomDao.delete(gameRoomId, password);
        gameRoomDao.save(new GameRoomEntity(gameRoom));
        chessGameDao.save(new ChessGameEntity(gameRoomId, gameRoom.getChessGame()));
        boardDao.save(BoardEntity.generateBoardEntities(gameRoomId, gameRoom.getChessGame().getCurrentBoard()));
    }

    public GameRoom loadGameRoom(final String gameRoomId) {
        GameRoomEntity gameRoomEntity = gameRoomDao.load(gameRoomId);
        return new GameRoom(
                gameRoomEntity.getGameRoomId(),
                gameRoomEntity.getName(),
                gameRoomEntity.getPassword(),
                loadChessGame(gameRoomEntity.getGameRoomId())
        );
    }

    private ChessGame loadChessGame(final String gameRoomId) {
        ChessGameEntity chessGameEntity = chessGameDao.load(gameRoomId);
        return new ChessGame(
                BoardEntitiesToBoardConvertor.convert(boardDao.load(gameRoomId)),
                new GameSwitch(chessGameEntity.getIsOn()),
                new Turn(Team.of(chessGameEntity.getTeamValueOfTurn()))
        );
    }

    public List<GameRoom> loadAllGameRooms() {
        List<GameRoom> gameRooms = new ArrayList<>();
        List<GameRoomEntity> gameRoomEntities = gameRoomDao.loadAll();
        for (GameRoomEntity gameRoomEntity : gameRoomEntities) {
            GameRoom gameRoom = loadGameRoom(gameRoomEntity.getGameRoomId());
            gameRooms.add(gameRoom);
        }
        return gameRooms;
    }

    public void deleteGameRoom(final String gameRoomId, final String password) {
        gameRoomDao.delete(gameRoomId, password);
    }

    public void movePiece(
            final String gameRoomId,
            final char sourceColumnValue,
            final int sourceRowValue,
            final char targetColumnValue,
            final int targetRowValue
    ) {
        ChessGame chessGame = loadChessGame(gameRoomId);
        chessGame.move(sourceColumnValue, sourceRowValue, targetColumnValue, targetRowValue);
        chessGameDao.updateChessGame(new ChessGameEntity(gameRoomId, chessGame));
        boardDao.updateBoard(
                new BoardEntity(gameRoomId, sourceColumnValue, sourceRowValue, chessGame.getCurrentBoard())
        );
        boardDao.updateBoard(
                new BoardEntity(gameRoomId, targetColumnValue, targetRowValue, chessGame.getCurrentBoard())
        );
    }

    public void resetChessRoom(final String gameRoomId) {
        GameRoomEntity gameRoomEntity = gameRoomDao.load(gameRoomId);
        createGameRoom(gameRoomId, gameRoomEntity.getName(), gameRoomEntity.getPassword());
    }
}
