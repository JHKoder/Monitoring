package github.oineh.monitoring.domain.authority;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Grade implements GrantedAuthority {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final int number;
    private final String name;

    Grade(int number, String name) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
