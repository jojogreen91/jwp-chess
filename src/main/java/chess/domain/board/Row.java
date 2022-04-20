package chess.domain.board;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Row {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int value;

    Row(final int value) {
        this.value = value;
    }

    private static final Map<Integer, Row> CACHE =
            Arrays.stream(values()).collect(Collectors.toMap(Row::getValue, Function.identity()));

    private static Row of(final int value) {
        return Optional
                .ofNullable(CACHE.get(value))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 Row 입니다."));
    }

    public Row move(final int vertical) {
        return of(this.value + vertical);
    }

    public int subtractValue(final Row row) {
        return row.value - this.value;
    }

    public int getValue() {
        return value;
    }
}
