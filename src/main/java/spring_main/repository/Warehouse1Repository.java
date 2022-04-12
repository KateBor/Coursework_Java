package spring_main.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring_main.entity.Warehouse1;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface Warehouse1Repository extends CrudRepository<Warehouse1, Long> {
    @Query("select w from Warehouse1 w where w.good.id = ?1")
    Warehouse1 findByGood_id(Long good_id);

    @Modifying
    @Query("update Warehouse1 w set w.good_count = w.good_count + ?2 where w.good.id = ?1")
    void updateGood_count(Long good_id, int count);

    @Modifying
    @Query("DELETE FROM Warehouse1 w where w.id > 0")
    void dropAll();
}
