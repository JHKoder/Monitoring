package github.oineh.monitoring.domain.authority;

import lombok.Getter;

@Getter
public enum GradeLevel {
    SSS(1, "SSS 레벨"),
    SS(2, "SS 레벨"),
    S(3, "S 레벨"),
    AAA(4, "AAA 레벨"),
    AA(5, "AA 레벨"),
    A(6, "A 레벨"),
    B(7, "B 레벨"),
    C(8, "C 레벨"),
    D(9, "D 레벨"),
    E(10, "E 레벨"),
    F(11, "F 레벨");

    private final int number;
    private final String name;

    GradeLevel(int number, String name) {
        this.name = name;
        this.number = number;
    }
}
