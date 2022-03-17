package spring_main.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring_main.entity.Warehouse2;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface Warehouse2Repository extends CrudRepository<Warehouse2, Long> {
    @Query("select w from Warehouse2 w where w.good.id = ?1")
    Warehouse2 findByGood_id(Long good_id);

    @Modifying
    @Query("update Warehouse2 w set w.good_count = ?2 where w.good.id = ?1")
    void updateGood_count(Long good_id, int count);

    @Modifying
    @Query("DELETE FROM Warehouse2 w where w.id > 0")
    void dropAll();
}
