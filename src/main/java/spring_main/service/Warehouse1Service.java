package spring_main.service;

import spring_main.entity.Warehouse1;
import spring_main.request.WarehouseCreationRequest;

import java.util.List;

public interface Warehouse1Service {

    Warehouse1 findWarehouse1ByGoodId(Long id);

    List<Warehouse1> listWarehouse1();

    Warehouse1 addGoodToWarehouse1(WarehouseCreationRequest warehouse); // несколько таблиц

    void dropAll();

    void deleteGoodsFromWarehouse1(Long good_id);
}
