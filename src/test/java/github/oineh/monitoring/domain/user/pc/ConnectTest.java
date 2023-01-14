package github.oineh.monitoring.domain.user.pc;

import static org.assertj.core.api.Assertions.assertThat;

import github.oineh.monitoring.domain.connect.Connect;
import github.oineh.monitoring.domain.user.pc.Pc;
import github.oineh.monitoring.domain.user.pc.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pc Connect entity 테스트")
public class ConnectTest {


    @Test
    @DisplayName("PC Icmp 연결 타입 생성")
    public void icmpCreate() {
        //given
        Pc pc = new Pc("name", Type.PC);
        Connect connect = Connect.icmp("내컴", "127.0.0.1");

        //when
        pc.updateConnect(connect);

        //then
        assertThat(connect).isNotNull();
    }

    @Test
    @DisplayName("PC tcp-port 연결 타입 생성")
    public void tcpPortCreate() {
        //given
        Pc pc = new Pc("name", Type.PC);
        Connect connect = Connect.tcp("내컴", "127.0.0.1", 8089);

        //when
        pc.updateConnect(connect);

        //then
        assertThat(connect).isNotNull();
    }

    @Test
    @DisplayName("PC tcp-create 연결 타입 생성")
    public void tcpUrlCreate() {
        //given
        Pc pc = new Pc("name", Type.SERVER);
        Connect connect = Connect.tcp("네이버", "www.naver.com");

        //when
        pc.updateConnect(connect);

        //then
        assertThat(connect).isNotNull();
    }

}
