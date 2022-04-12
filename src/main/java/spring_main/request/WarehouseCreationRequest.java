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
public class WarehouseCreationRequest implements Serializable {
    private final Long good_id;
    private final Integer good_count;
}
