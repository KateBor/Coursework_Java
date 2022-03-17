package spring_main.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "good_count", nullable = false)
    private Integer good_count;

    @Column(name = "create_date", nullable = false)
    private Timestamp create_date; //???????????????????

    @ManyToOne
    @JoinColumn(name="good_id",referencedColumnName="id")
    private Good good;

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", good_id=" + good.getId() +
                ", good_count=" + good_count +
                ", create_date=" + create_date +
                '}';
    }

}
