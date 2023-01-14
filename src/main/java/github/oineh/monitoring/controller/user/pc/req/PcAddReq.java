package github.oineh.monitoring.controller.user.pc.req;

import github.oineh.monitoring.domain.user.pc.Type;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PcAddReq {

    private String pcName;
    private Type type;
}
