package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.connect.domain.Connect;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressResponse {

    private String name;
    private Long id;

    public AddressResponse(Connect connect) {
        this.name = connect.getName();
        this.id = connect.getId();
    }
}
