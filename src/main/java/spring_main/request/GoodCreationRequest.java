package spring_main.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodCreationRequest {
    private final String name;
    private final float priority;

    public GoodCreationRequest(String name, float priority) {
        this.name = name;
        this.priority = priority;
    }
}
