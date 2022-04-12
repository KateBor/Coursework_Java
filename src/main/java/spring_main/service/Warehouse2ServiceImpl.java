package spring_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_main.entity.Good;
import spring_main.entity.Warehouse2;
import spring_main.exception.entityNotFoundException;
import spring_main.repository.GoodsRepository;
import spring_main.repository.Warehouse2Repository;
import spring_main.request.WarehouseCreationRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class Warehouse2ServiceImpl implements Warehouse2Service{

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private Warehouse2Repository warehouse2Repository;

    @Override
    public Warehouse2 findWarehouse2ByGoodId(Long good_id) {
        return warehouse2Repository.findByGood_id(good_id);
    }

    @Override
    public List<Warehouse2> listWarehouse2() {
        return (List<Warehouse2>) warehouse2Repository.findAll();
    }

    @Transactional
    @Override
    public Warehouse2 addGoodToWarehouse2(WarehouseCreationRequest warehouse) {
        Warehouse2 warehouse2ToCreate = new Warehouse2();
        warehouse2ToCreate.setGood_count(warehouse.getGood_count());
        Optional<Good> optionalGood = goodsRepository.findById(warehouse.getGood_id());
        if (optionalGood.isPresent()) {
            Warehouse2 warehouse2 = warehouse2Repository.findByGood_id(warehouse.getGood_id());
            if (warehouse2 != null) {
                warehouse2Repository.updateGood_count(warehouse2.getGood().getId(), warehouse2ToCreate.getGood_count());
                return warehouse2Repository.findByGood_id(warehouse.getGood_id());
            } else {
                warehouse2ToCreate.setGood(optionalGood.get());
                warehouse2Repository.save(warehouse2ToCreate);
            }
            return warehouse2ToCreate;
        } else {
            throw new entityNotFoundException("Can't store not existing good");
        }
    }

    @Transactional
    @Override
    public void dropAll() {
        warehouse2Repository.dropAll();
    }

    @Transactional
    @Override
    public void deleteGoodsFromWarehouse2(Long good_id) {
        Warehouse2 warehouse2 = warehouse2Repository.findByGood_id(good_id);
        if(warehouse2 != null) {
            warehouse2Repository.deleteById(warehouse2.getId());
        }
    }
}
