package spring_main.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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


    @JsonBackReference
    @OneToMany(mappedBy = "good")
    private List<Sale> sales;

    @JsonBackReference
    @OneToOne(mappedBy = "good")
    private Warehouse1 warehouse1;

    @JsonBackReference
    @OneToOne(mappedBy = "good")
    private Warehouse2 warehouse2;

    public Good(String name, float priority){
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
