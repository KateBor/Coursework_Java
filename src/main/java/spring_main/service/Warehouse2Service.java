package spring_main.service;

import spring_main.entity.Warehouse2;
import spring_main.request.*;

import java.util.List;

public interface Warehouse2Service {
    public Warehouse2 findWarehouse2ById(Long id);
    public Warehouse2 findWarehouse2ByGoodId(Long id);
    public List<Warehouse2> listWarehouse2();
    public void addGoodsToWarehouse2(WarehouseCreationRequest warehouse); // несколько таблиц
    public void dropAll();
    public void deleteGoodsFromWarehouse2(Long id);
    public void updateGoodsCount(Long id, int count);
}
