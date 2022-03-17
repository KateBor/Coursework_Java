package spring_main.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCreationRequest {
    private final Long good_id;
    private final Integer good_count;

    public WarehouseCreationRequest(Long good_id, int good_count) {
        this.good_id = good_id;
        this.good_count = good_count;
    }
}
