package spring_main.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonSerialize
@AllArgsConstructor
public class SaleCreationRequest implements Serializable {
    private Long good_id;
    private Integer good_count;
    private String create_date;
}
