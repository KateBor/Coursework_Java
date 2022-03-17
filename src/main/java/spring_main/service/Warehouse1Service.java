package spring_main.service;

import spring_main.entity.Warehouse1;
import spring_main.request.*;

import java.util.List;

public interface Warehouse1Service {

    public Warehouse1 findWarehouse1ById(Long id);
    public Warehouse1 findWarehouse1ByGoodId(Long id);
    public List<Warehouse1> listWarehouse1();
    public void addGoodsToWarehouse1(WarehouseCreationRequest warehouse); // несколько таблиц
    public void dropAll();
    public void deleteGoodsFromWarehouse1(Long id);
    public void updateGoodsCount(Long good_id, int count);
}
