package spring_main.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spring_main.entity.Good;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends CrudRepository<Good, Long> {

    @Query("select g from Good g where g.name = ?1")
    Optional<Good> findGoodByName(String name);

    @Modifying
    @Query("DELETE FROM Sale s where s.id > 0")
    void dropAll();
}
