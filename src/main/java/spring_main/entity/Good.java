package spring_main.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority", nullable = false)
    private Float priority;


    @OneToMany(mappedBy = "good")
    private List<Sale> sales;

    @OneToOne(mappedBy = "good")
    private Warehouse1 warehouse1;

    @OneToOne(mappedBy = "good")
    private Warehouse2 warehouse2;

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
