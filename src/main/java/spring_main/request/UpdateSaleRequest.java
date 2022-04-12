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
public class UpdateSaleRequest implements Serializable {
    private Long id;
    private Integer good_count;
}
