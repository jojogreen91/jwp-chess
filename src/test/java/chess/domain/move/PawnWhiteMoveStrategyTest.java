package chess.domain.move;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnWhiteMoveStrategyTest {

    private Board board;
    private PawnMoveStrategy pawnWhiteMoveStrategy;

    @BeforeEach
    void setUp() {
        board = BoardFactory.createInitChessBoard();
        pawnWhiteMoveStrategy = new PawnWhiteMoveStrategy();
    }

    @Test
    @DisplayName("폰을 이동 할 수 있다.")
    void isMovable() {
        Position source = Position.of('c', 2);
        Position target = Position.of('c', 3);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isTrue();
    }

    @Test
    @DisplayName("폰 움직임 전략에 맞지 않는 경우 false")
    void isMovable_NotPawnMovePattern() {
        Position source = Position.of('a', 2);
        Position target = Position.of('a', 1);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isFalse();
    }

    @Test
    @DisplayName("2칸 전진시 Pawn 이 사작위치가 아닌경우 false")
    void isMovable_StartMove_NotStartPosition() {
        board.movePiece(Position.of('a', 2), Position.of('a', 3));

        Position source = Position.of('a', 3);
        Position target = Position.of('a', 5);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isFalse();
    }

    @Test
    @DisplayName("2칸 전진시 기물이 있을 경우 false")
    void isMovable_StartMove_HasPieceOnTarget() {
        board.movePiece(Position.of('a', 7), Position.of('a', 4));

        Position source = Position.of('a', 2);
        Position target = Position.of('a', 4);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isFalse();
    }

    @Test
    @DisplayName("전진시 상대 팀 기물이 있을 경우 false")
    void isMovable_North_HasPieceOnTarget() {
        board.movePiece(Position.of('a', 7), Position.of('a', 3));

        Position source = Position.of('a', 2);
        Position target = Position.of('a', 3);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isFalse();
    }

    @Test
    @DisplayName("대각선 이동시 상대편 기물이 없을 경우 false")
    void isMovable_Diagonal_HasNoOppositePieceOnTarget() {
        Position source = Position.of('a', 2);
        Position target = Position.of('b', 3);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isFalse();
    }

    @Test
    @DisplayName("대각선 이동시 상대편 기물이 있을 경우 true")
    void isMovable_Diagonal_HasOppositePieceOnTarget() {
        board.movePiece(Position.of('a', 7), Position.of('b', 3));

        Position source = Position.of('a', 2);
        Position target = Position.of('b', 3);

        assertThat(pawnWhiteMoveStrategy.isMovable(board, source, target)).isTrue();
    }
}
