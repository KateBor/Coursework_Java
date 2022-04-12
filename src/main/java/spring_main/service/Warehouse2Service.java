package spring_main.service;

import spring_main.entity.Good;
import spring_main.entity.Warehouse2;
import spring_main.request.WarehouseCreationRequest;

import java.util.List;

public interface Warehouse2Service {
    Warehouse2 findWarehouse2ByGoodId(Long id);

    List<Warehouse2> listWarehouse2();

    Warehouse2 addGoodToWarehouse2(WarehouseCreationRequest warehouse); // несколько таблиц

    void dropAll();

    void deleteGoodsFromWarehouse2(Long good_id);
}
