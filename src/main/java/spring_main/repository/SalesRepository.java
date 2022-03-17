package spring_main.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring_main.entity.Sale;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository<Sale, Long> {
    @Query("select s from Sale s where s.create_date = ?1")
    List<Sale> findAllByCreate_date(Timestamp create_date);

    @Query("select s from Sale s where s.good.id = ?1")
    List<Sale> findAllByGood_id(Long good_id);

    @Query(value = "select s.good_count, g.name from Sale s join Good g on s.good.id = g.id")
    List<String> joinGood();

    @Modifying
    @Query("DELETE FROM Sale s where s.id > 0")
    void dropAll();

    @Modifying
    @Query("update Sale s set s.good_count = ?2 where s.id = ?1")
    void updateGood_count(Long id, int count);
}
