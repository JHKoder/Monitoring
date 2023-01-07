package github.oineh.monitoring.controller.pc.res;

import io.github.sno.network.NetProtocolType;

public class ConnectStatusRes {

    private boolean icmpStatus;
    private boolean tcpUrlStatus;
    private boolean tcpPortStatus;

    public ConnectStatusRes(NetProtocolType type) {
        this.icmpStatus = type.isIcmpStatus();
        this.tcpUrlStatus = type.isTcpUrlStatus();
        this.tcpPortStatus = type.isTcpPortStatus();
    }
}
