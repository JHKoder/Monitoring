package github.oineh.monitoring.domain.pc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PC 정보 entity 테스트")
public class PcTest {

    @Test
    @DisplayName("PC 타입 생성")
    public void create(){
        //given
        String name="내컴퓨터";
        String mac ="ad:12:12:vd:sa:as";

        //when
        Pc pc = new Pc(name,mac, Type.PC);

        //then
        assertThat(pc).isNotNull();
    }

    @Test
    @DisplayName("서버 타입 생성 ")
    public void create2(){
        //given
        String name="내 서버";
        String mac ="BC:1A:F2:CD:D8:A7";

        //when
        Pc pc = new Pc(name,mac, Type.SERVER);

        //then
        assertThat(pc).isNotNull();
    }

}
