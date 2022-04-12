package spring_main.service;

import spring_main.entity.Sale;
import spring_main.request.SaleCreationRequest;

import java.util.List;

public interface SalesService {

    Sale findSaleById(Long id);

    List<Sale> findSalesByGood_id(Long good_id);

    List<Sale> listSales();

    void dropAll();

    Sale createSale(SaleCreationRequest sale);   //несколько таблиц

    void deleteSale(Long id);                    // несколько таблиц

    void updateSaleCount(Long id, int count);    // несколько таблиц

    List<String> joinGood();                     //несколько таблиц

}
