package spring_main.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SaleCreationRequest {
    private Long good_id;
    private Integer good_count;
    private Timestamp create_date;

    public SaleCreationRequest(long good_id, Integer good_count, Timestamp create_date) {
        this.good_id = good_id;
        this.good_count = good_count;
        this.create_date = create_date;
    }


}
