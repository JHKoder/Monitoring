package github.oineh.monitoring.domain.pc;


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
public class Connect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Pc pc;

    private String ip;
    private String url;
    private int port;
    private ConnectType connectType;

    public Connect(Pc pc, String ip, String url, int port, ConnectType connectType) {
        this.pc = pc;
        this.ip = ip;
        this.url = url;
        this.port = port;
        this.connectType = connectType;
    }

    public static Connect icmp(Pc pc,String ip){
        return new Connect(pc,ip,null,0,ConnectType.ICMP);
    }

    public static Connect tcp(Pc pc,String ip,int port){
        return new Connect(pc,ip,null,port,ConnectType.TCP_PORT);
    }

    public static Connect tcp(Pc pc,String url){
        return new Connect(pc,null,url,0,ConnectType.TCP_URL);
    }

}
