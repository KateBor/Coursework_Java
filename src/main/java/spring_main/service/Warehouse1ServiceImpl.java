package spring_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_main.entity.Good;
import spring_main.entity.Warehouse1;
import spring_main.exception.entityNotFoundException;
import spring_main.repository.GoodsRepository;
import spring_main.repository.Warehouse1Repository;
import spring_main.request.WarehouseCreationRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class Warehouse1ServiceImpl implements Warehouse1Service{

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private Warehouse1Repository warehouse1Repository;

    @Override
    public Warehouse1 findWarehouse1ByGoodId(Long good_id) {
        return warehouse1Repository.findByGood_id(good_id);
    }

    @Override
    public List<Warehouse1> listWarehouse1() {
        return (List<Warehouse1>) warehouse1Repository.findAll();
    }

    @Transactional
    @Override
    public Warehouse1 addGoodToWarehouse1(WarehouseCreationRequest warehouse) {
        Warehouse1 warehouse1ToCreate = new Warehouse1();
        warehouse1ToCreate.setGood_count(warehouse.getGood_count());

        Optional<Good> optionalGood = goodsRepository.findById(warehouse.getGood_id());
        if (optionalGood.isPresent()) {
            Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(warehouse.getGood_id());
            if (warehouse1 != null) {
                warehouse1Repository.updateGood_count(warehouse1.getGood().getId(), warehouse1ToCreate.getGood_count());
                return warehouse1Repository.findByGood_id(warehouse.getGood_id());
            } else {
                warehouse1ToCreate.setGood(optionalGood.get());
                warehouse1Repository.save(warehouse1ToCreate);
            }
            return warehouse1ToCreate;
        } else {
            throw new entityNotFoundException("Can't store not existing good");
        }
    }

    @Transactional
    @Override
    public void dropAll() {
        warehouse1Repository.dropAll();
    }

    @Transactional
    @Override
    public void deleteGoodsFromWarehouse1(Long good_id) {
        Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(good_id);
        if(warehouse1 != null) {
            warehouse1Repository.deleteById(warehouse1.getId());
        }
    }
}
