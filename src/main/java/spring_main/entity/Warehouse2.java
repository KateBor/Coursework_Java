package spring_main.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "warehouse2")
public class Warehouse2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "good_count", nullable = false)
    private Integer good_count;

    @OneToOne
    @JoinColumn(name="good_id",referencedColumnName="id")

    @JsonManagedReference
    private Good good;

    @Override
    public String toString() {
        return "Warehouse2{" +
                "id=" + id +
                ", good_id=" + good.getId() +
                ", good_count=" + good_count +
                '}';
    }
}
