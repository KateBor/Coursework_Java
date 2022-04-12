package spring_main.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest implements Serializable {
    private String userName;
    private  String password;
}
