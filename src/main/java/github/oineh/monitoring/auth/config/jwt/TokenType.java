package github.oineh.monitoring.auth.config.jwt;

public enum TokenType {
    ACCESS,
    REFRESH;

    public long life() {
        if (this == ACCESS) {
            return 600 * 2000;
        }
        if (this == REFRESH) {
            return 24 * 60 * 60;
        }
        return 0;
    }
}
