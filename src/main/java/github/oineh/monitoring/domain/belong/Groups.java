package github.oineh.monitoring.domain.belong;


import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Division division;

    @OneToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    private Team team;


    public Groups(Division division, Department department, Team team) {
        this.division = division;
        this.department = department;
        this.team = team;
    }
}
