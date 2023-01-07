package github.oineh.monitoring.domain.connect;

import github.oineh.monitoring.controller.pc.req.ByPcReq;
import github.oineh.monitoring.controller.pc.res.ConnectStatusRes;
import io.github.sno.network.Host;
import io.github.sno.network.Monitoring;
import io.github.sno.network.NetProtocolDto;
import io.github.sno.network.NetProtocolType;
import org.springframework.stereotype.Service;

@Service
public class ConnectService {

    private final Monitoring monitoring = new Monitoring();

    // 연결 가능한 상태 확인
    public ConnectStatusRes findConnectStatus(ByPcReq byPcReq, Host host) {
        NetProtocolDto netProtocolDto = new NetProtocolDto(host, byPcReq.getUrl(), byPcReq.getPort());

        NetProtocolType netProtocolType = monitoring.connectNetProtocol(netProtocolDto);

        return new ConnectStatusRes(netProtocolType);
    }

    //연결 상태 리스트
}
