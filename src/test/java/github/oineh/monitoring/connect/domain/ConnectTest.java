package github.oineh.monitoring.connect.domain;

import github.oineh.monitoring.pc.domain.Pc;
import github.oineh.monitoring.pc.domain.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pc Connect entity 테스트")
public class ConnectTest {


    @Test
    @DisplayName("PC Icmp 연결 타입 생성")
    public void icmpCreate() {
        //given
        Pc pc = new Pc("name", Type.PC);
        String ip = "127.0.0.1";

        //when
        pc.updateConnect("내컴", ip);

        //then
        assertThat(pc.getConnectHost()).isEqualTo(ip);
        assertThat(pc.getConnectNickName()).isEqualTo("내컴");
    }

    @Test
    @DisplayName("PC tcp-port 연결 타입 생성")
    public void tcpPortCreate() {
        //given
        Pc pc = new Pc("name", Type.PC);

        //when
        pc.updateConnect("내컴", "127.0.0.1", 8089);

        //then
        assertThat(pc).isNotNull();
    }

    @Test
    @DisplayName("PC tcp-create 연결 타입 생성")
    public void tcpUrlCreate() {
        //given
        Pc pc = new Pc("name", Type.SERVER);
        Connect connect = Connect.tcp("네이버", "https://www.naver.com");

        //when
        pc.updateConnectUrl("네이버", connect.getUrl());

        //then
        assertThat(connect).isNotNull();
    }

}
