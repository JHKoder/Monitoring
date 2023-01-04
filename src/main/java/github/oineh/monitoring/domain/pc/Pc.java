package github.oineh.monitoring.domain.pc;


import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Pc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Connect connect;

    private String name;
    private String mac;
    private Type pcType;


    public Pc(String name, String mac, Type pcType) {
        this.name = name;
        this.mac = mac;
        this.pcType = pcType;
    }

    public void updateConnect(Connect connect) {
        this.connect = connect;
    }

}
