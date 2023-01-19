package github.oineh.monitoring.pc.web.req;

import github.oineh.monitoring.pc.domain.Type;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PcAddReq {

    private String pcName;
    private Type type;
}
