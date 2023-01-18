package github.oineh.monitoring.user.pc.web.req;

import github.oineh.monitoring.user.pc.domain.Type;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PcAddReq {

    private String pcName;
    private Type type;
}
