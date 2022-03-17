package spring_main.service;

import spring_main.entity.Sale;
import spring_main.request.*;

import java.sql.Timestamp;
import java.util.List;

public interface SalesService {

    public Sale findSaleById(Long id);
    //public List<Sale> findSaleByDate(Timestamp date); //переделать !!!!!!!!!!!!!!!
    public List<Sale> findSaleByGood_id(Long good_id);
    public List<Sale> listSales();
    public void dropAll();
    public Sale createSale(SaleCreationRequest sale);   //несколько таблиц
    public void deleteSale(Long id);                    // несколько таблиц
    public void updateSaleCount(Long id, int count);    // несколько таблиц
    public List<String> joinGood();                     //несколько таблиц

}
