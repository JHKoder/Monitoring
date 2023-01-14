package github.oineh.monitoring.domain.user.pc;

import static org.assertj.core.api.Assertions.assertThat;

import github.oineh.monitoring.domain.user.pc.Pc;
import github.oineh.monitoring.domain.user.pc.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PC 정보 entity 테스트")
public class PcTest {

    @Test
    @DisplayName("PC 타입 생성")
    public void create() {
        //given
        String name = "내컴퓨터";

        //when
        Pc pc = new Pc(name, Type.PC);

        //then
        assertThat(pc).isNotNull();
    }

    @Test
    @DisplayName("서버 타입 생성 ")
    public void create2() {
        //given
        String name = "내 서버";

        //when
        Pc pc = new Pc(name, Type.SERVER);

        //then
        assertThat(pc).isNotNull();
    }

}
