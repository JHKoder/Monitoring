package github.oineh.monitoring.domain.user.pc;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.domain.connect.Connect;
import javax.persistence.CascadeType;
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
public class Pc extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Connect connect;
    private String name;
    private Type pcType;


    public Pc(String name, Type pcType) {
        this.name = name;
        this.pcType = pcType;
    }

    public Pc(String name, Type pcType, Connect connect) {
        this.name = name;
        this.pcType = pcType;
        this.connect = connect;
    }

    public void updateConnect(Connect connect) {
        this.connect = connect;
    }

    public boolean isSameConnectId(Long connectId) {
        return this.getConnect().isSameId(connectId);
    }
}
