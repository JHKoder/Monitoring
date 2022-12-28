package github.oineh.monitoring.domain.pc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pc Connect entity 테스트")
public class ConnectTest {


    @Test
    @DisplayName("PC Icmp 연결 타입 생성")
    public void icmpCreate() {
        //given
        Pc pc = new Pc("name", "mac", Type.PC);

        //when
        Connect connect = Connect.icmp(pc, "127.0.0.1");

        //then
        assertThat(connect).isNotNull();
    }

    @Test
    @DisplayName("PC tcp-port 연결 타입 생성")
    public void tcpPortCreate() {
        //given
        Pc pc = new Pc("name", "mac", Type.PC);

        //when
        Connect connect = Connect.tcp(pc, "127.0.0.1",8089);

        //then
        assertThat(connect).isNotNull();
    }

    @Test
    @DisplayName("PC tcp-create 연결 타입 생성")
    public void tcpUrlCreate() {
        //given
        Pc pc = new Pc("name", "mac", Type.SERVER);

        //when
        Connect connect = Connect.tcp(pc, "www.naver.com");

        //then
        assertThat(connect).isNotNull();
    }

}
