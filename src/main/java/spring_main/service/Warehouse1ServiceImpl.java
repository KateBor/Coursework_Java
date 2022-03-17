package spring_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_main.entity.Good;
import spring_main.entity.Warehouse1;
import spring_main.exception.*;
import spring_main.repository.*;
import spring_main.request.*;

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
    public Warehouse1 findWarehouse1ById(Long id) {
        Optional<Warehouse1> optionalWarehouse1 = warehouse1Repository.findById(id);
        if (optionalWarehouse1.isPresent()) {
            return optionalWarehouse1.get();
        } else {
            throw new entityNotFoundException("Can't find this good in warehouse1");
        }
    }

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
    public void addGoodsToWarehouse1(WarehouseCreationRequest warehouse) {
        Warehouse1 warehouse1ToCreate = new Warehouse1();
        warehouse1ToCreate.setGood_count(warehouse.getGood_count());

        Optional<Good> optionalGood = goodsRepository.findById(warehouse.getGood_id());
        if (optionalGood.isPresent()) {
            Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(warehouse.getGood_id());
            if (warehouse1 != null) {
                warehouse1Repository.updateGood_count(warehouse1.getId(), warehouse1ToCreate.getGood_count());
                warehouse1Repository.findByGood_id(warehouse.getGood_id());
            } else {
                warehouse1ToCreate.setGood(optionalGood.get());
                warehouse1Repository.save(warehouse1ToCreate);
            }
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
    public void deleteGoodsFromWarehouse1(Long id) {
        warehouse1Repository.deleteById(id);
    }

    @Override
    public void updateGoodsCount(Long good_id, int count) {
        Warehouse1 opWarehouse1 = warehouse1Repository.findByGood_id(good_id);
        if (opWarehouse1 != null) {
            warehouse1Repository.updateGood_count(good_id, count);
        } else {
            Optional<Good> optionalGood = goodsRepository.findById(good_id);
            if (optionalGood.isPresent()) {
                Warehouse1 warehouse1 = new Warehouse1();
                warehouse1.setGood(optionalGood.get());
                warehouse1.setGood_count(count);
                warehouse1Repository.save(warehouse1);
            } else {
                throw new entityNotFoundException("Can't update not existing good");
            }
        }
    }
}
