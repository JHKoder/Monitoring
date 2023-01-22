package github.oineh.monitoring.pc.web.req;

import github.oineh.monitoring.pc.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PcAddReq {

    private String pcName;
    private Type type;
}
