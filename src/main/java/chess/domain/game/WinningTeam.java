package chess.domain.game;

import chess.domain.piece.Team;

public class WinningTeam {

    private static final String WIN_BY_SCORE = "Win By Score";
    private static final String WIN_BY_KING_DEAD = "Win By Slaying King";

    private final String value;
    private final String winningType;

    public WinningTeam(final double blackScore, final double whiteScore, final Team teamOfDeadKing) {
        Team winTeam = Team.NONE;
        String winType = WIN_BY_SCORE;
        if (whiteScore > blackScore) {
            winTeam = Team.WHITE;
        }
        if (whiteScore <= blackScore) {
            winTeam = Team.BLACK;
        }
        if (teamOfDeadKing != Team.NONE) {
            winTeam = teamOfDeadKing.oppositeTeam();
            winType = WIN_BY_KING_DEAD;
        }
        this.value = winTeam.getValue();
        this.winningType = winType;
    }

    public boolean isWinByKingDead() {
        return winningType.equals(WIN_BY_KING_DEAD);
    }

    public String getValue() {
        return value;
    }

    public String getWinningType() {
        return winningType;
    }
}
