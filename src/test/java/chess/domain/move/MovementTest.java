package chess.domain.move;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovementTest {

    @Test
    @DisplayName("Movement 가 수직 이동이다.")
    void isVertical() {
        Movement movement = new Movement(Position.of('a', 7), Position.of('a', 1));

        assertThat(movement.isVertical()).isTrue();
    }

    @Test
    @DisplayName("Movement 가 수평 이동이다.")
    void isHorizontal() {
        Movement movement = new Movement(Position.of('a', 7), Position.of('e', 7));

        assertThat(movement.isHorizontal()).isTrue();
    }

    @Test
    @DisplayName("Movement 가 기울기 1 인 대각선 형태이다.")
    void isPositiveDiagonal() {
        Movement movement = new Movement(Position.of('d', 4), Position.of('f', 6));

        assertThat(movement.isPositiveDiagonal()).isTrue();
    }

    @Test
    @DisplayName("Movement 가 기울기 -1 인 대각선 형태이다.")
    void isNegativeDiagonal() {
        Movement movement = new Movement(Position.of('d', 4), Position.of('f', 2));

        assertThat(movement.isNegativeDiagonal()).isTrue();
    }
}