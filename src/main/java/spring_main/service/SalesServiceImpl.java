package spring_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_main.entity.Good;
import spring_main.entity.Sale;
import spring_main.entity.Warehouse1;
import spring_main.entity.Warehouse2;
import spring_main.exception.InvalidDateException;
import spring_main.exception.entityNotFoundException;
import spring_main.repository.*;
import spring_main.request.SaleCreationRequest;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalesServiceImpl implements SalesService{

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private Warehouse1Repository warehouse1Repository;
    @Autowired
    private Warehouse2Repository warehouse2Repository;

    @Override
    public Sale findSaleById(Long id) {
        Optional<Sale> optionalSale = salesRepository.findById(id);
        if (optionalSale.isPresent()) {
            return optionalSale.get();
        } else {
            throw new entityNotFoundException("Can't find this sale");
        }
    }

    @Override
    public List<Sale> findSalesByGood_id(Long good_id) {
        return salesRepository.findAllByGood_id(good_id);
    }

    @Override
    public List<Sale> listSales() {
        return (List<Sale>) salesRepository.findAll();
    }

    @Transactional
    @Override
    public void dropAll() {
        salesRepository.dropAll();
    }

    @Transactional
    @Override
    public Sale createSale(SaleCreationRequest sale) {
        Sale saleToCreate = new Sale();
        saleToCreate.setGood_count(sale.getGood_count());
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy-H:m").parse(sale.getCreate_date());
        } catch (ParseException e) {
            throw new InvalidDateException("Invalid data type, please fill all gaps");
        }
        saleToCreate.setCreate_date(date);
        Optional<Good> optionalGood = goodsRepository.findById(sale.getGood_id());

        if (optionalGood.isPresent()) {
            Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(optionalGood.get().getId());
            Warehouse2 warehouse2 = warehouse2Repository.findByGood_id(optionalGood.get().getId());

            if (warehouse1 != null && warehouse1.getGood_count() >= sale.getGood_count()
                || warehouse2 != null && warehouse2.getGood_count() >= sale.getGood_count()
                || (warehouse1 != null && warehouse2 != null && (warehouse1.getGood_count() + warehouse2.getGood_count()) >= sale.getGood_count())) {

                saleToCreate.setGood(optionalGood.get());

                //вычесть из склада
                int count = sale.getGood_count();
                if (warehouse1 != null) {
                    if (warehouse1.getGood_count() > count) {
                        warehouse1Repository.updateGood_count(warehouse1.getGood().getId(), warehouse1.getGood_count() - count);
                        count = 0;
                    } else if (warehouse1.getGood_count() == count) {
                        warehouse1Repository.delete(warehouse1);
                        count = 0;
                    } else {
                        count -= warehouse1.getGood_count();
                        warehouse1Repository.delete(warehouse1);
                    }
                }
                if (count > 0) {
                    if (warehouse2.getGood_count() > count) {
                        warehouse2Repository.updateGood_count(warehouse2.getGood().getId(), warehouse2.getGood_count() - count);

                    } else {
                        warehouse2Repository.delete(warehouse2);
                    }
                }
                //

                return salesRepository.save(saleToCreate);
            } else {
                throw new entityNotFoundException("There aren't enough goods in warehouses");
            }
        } else {
            throw new entityNotFoundException("Can't sale not existing good");
        }
    }

    @Transactional
    @Override
    public void deleteSale(Long id) {
        Optional<Sale> optionalSale = salesRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(sale.getGood().getId());
            Warehouse2 warehouse2 = warehouse2Repository.findByGood_id(sale.getGood().getId());
            int count = sale.getGood_count();
            if (warehouse1 != null) {
                warehouse1.setGood_count(warehouse1.getGood_count() + count);
            } else if (warehouse2 != null) {
                warehouse2.setGood_count(warehouse2.getGood_count() + count);
            } else {
                Warehouse1 wh = new Warehouse1();
                wh.setGood_count(count);
                Optional<Good> optionalGood = goodsRepository.findById(sale.getGood().getId());
                if (optionalGood.isPresent()) {
                    wh.setGood(optionalGood.get());
                    warehouse1Repository.save(wh);
                }
            }
            salesRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void updateSaleCount(Long id, int count) {
        Optional<Sale> optionalSale = salesRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();

            int diff = count - sale.getGood_count();
            Warehouse1 warehouse1 = warehouse1Repository.findByGood_id(sale.getGood().getId());
            Warehouse2 warehouse2 = warehouse2Repository.findByGood_id(sale.getGood().getId());

            if (diff == 0) {
                return;
            }
            if (diff < 0) { //возврат на склад
                salesRepository.updateGood_count(id, count);
                if (warehouse1 != null) {
                    warehouse1Repository.updateGood_count(warehouse1.getGood().getId(), warehouse1.getGood_count() - diff);
                } else if (warehouse2 != null) {
                    warehouse2Repository.updateGood_count(warehouse2.getGood().getId(), warehouse2.getGood_count() - diff);
                } else {
                    Warehouse1 wh = new Warehouse1();
                    wh.setGood_count(-diff);
                    Optional<Good> optionalGood = goodsRepository.findById(sale.getGood().getId());
                    if (optionalGood.isPresent()) {
                        wh.setGood(optionalGood.get());
                        warehouse1Repository.save(wh);
                    }
                }
            } else {
                if (warehouse1 != null && warehouse1.getGood_count() >= sale.getGood_count()
                        || warehouse2 != null && warehouse2.getGood_count() >= sale.getGood_count()
                        || (warehouse1 != null && warehouse2 != null && (warehouse1.getGood_count() + warehouse2.getGood_count()) >= sale.getGood_count())) {

                    salesRepository.updateGood_count(sale.getId(), count);
                    //вычитание со склада
                    if (warehouse1 != null) {
                        if (warehouse1.getGood_count() > diff) {
                            warehouse1Repository.updateGood_count(warehouse1.getGood().getId(), warehouse1.getGood_count() - diff);
                            diff = 0;
                        } else if (warehouse1.getGood_count() == diff) {
                            diff = 0;
                            warehouse1Repository.delete(warehouse1);
                        } else {
                            diff -= warehouse1.getGood_count();
                            warehouse1Repository.delete(warehouse1);
                        }
                    }
                    if (diff != 0 && warehouse2 != null) { //берем со 2 склада
                        if (warehouse2.getGood_count() > diff) {
                            warehouse2Repository.updateGood_count(warehouse2.getGood().getId(), warehouse2.getGood_count() - diff);
                        } else {
                            warehouse2Repository.delete(warehouse2);
                        }
                    }
                } else {
                    throw new entityNotFoundException("There aren't enough goods in warehouses");
                }
            }
        } else {
            throw new entityNotFoundException("Can't find this sale");
        }
    }

    @Override
    public List<String> joinGood() {
        return salesRepository.joinGood();
    }
}
