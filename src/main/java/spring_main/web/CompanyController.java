package spring_main.web;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spring_main.entity.*;
import spring_main.request.*;
import spring_main.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Setter
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SalesService salesService;
    @Autowired
    private Warehouse1Service warehouse1Service;
    @Autowired
    private Warehouse2Service warehouse2Service;

    //Goods
    @GetMapping("/goods")
    public ResponseEntity<List<Good>> getAllGoods() {
        List<Good> list = goodsService.listGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/goods/byId={id}")
    public ResponseEntity<Good> getGoodById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(goodsService.findGoodById(id), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good is not found");
        }
    }

    @GetMapping("/goods/byName={name}")
    public ResponseEntity<Good> getGoodByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(goodsService.findGoodByName(name), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good is not found");
        }
    }

    @PostMapping(value = "/goods/createGood", consumes = "application/json", produces = "application/json")
    public Good createGood(@RequestBody GoodCreationRequest newGood) {
        return goodsService.createGood(newGood);
    }




//Sales
    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> list = salesService.listSales();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/sales/byGoodId={good_id}")
    public ResponseEntity<List<Sale>> getAllSalesByGoodId(@PathVariable("good_id") long good_id) {
        List<Sale> list = salesService.findSalesByGood_id(good_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/sales/byId={id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable("id") long id) {
        Sale list = salesService.findSaleById(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/sales/deleteAll")
    public void dropAllSales() {
        salesService.dropAll();
    }

    @PostMapping(value = "/sales/createSale", consumes = "application/json", produces = "application/json")
    public Sale createSale(@RequestBody SaleCreationRequest newSale) {
        return salesService.createSale(newSale);
    }

    @PostMapping(value = "/sales/updateSaleCount", consumes = "application/json", produces = "application/json")
    public void updateSaleCountGoods(@RequestBody UpdateSaleRequest saleRequest) {
        salesService.updateSaleCount(saleRequest.getId(), saleRequest.getGood_count());
    }

    @PutMapping("/sales/deleteById={id}")
    public void deleteSale(@PathVariable("id") long id) {
        salesService.deleteSale(id);
    }

    @GetMapping("/sales/salesAndGoods")
    public ResponseEntity<List<String>> getSalesAndGoods() {
        List<String> list = salesService.joinGood();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }




//Warehouse1
    @GetMapping("/warehouse1")
    public ResponseEntity<List<Warehouse1>> getAllGoodsFromWarehouse1() {
        List<Warehouse1> list = warehouse1Service.listWarehouse1();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/warehouse1/byGoodId={good_id}")
    public ResponseEntity<Warehouse1> getWarehouse1ByGoodId(@PathVariable("good_id") long good_id) {
        Warehouse1 list = warehouse1Service.findWarehouse1ByGoodId(good_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/warehouse1/addGoodToWarehouse1", consumes = "application/json", produces = "application/json")
    public Warehouse1 addGoodToWarehouse1(@RequestBody WarehouseCreationRequest newWarehouse) {
        return warehouse1Service.addGoodToWarehouse1(newWarehouse);
    }

    @PutMapping("/warehouse1/deleteAll")
    public void dropAllFromWarehouse1() {
        warehouse1Service.dropAll();
    }


    @PutMapping("/warehouse1/deleteByGoodId={good_id}")
    public void deleteWarehouse1(@PathVariable("good_id") long good_id) {
        warehouse1Service.deleteGoodsFromWarehouse1(good_id);
    }



//Warehouse2
    @GetMapping("/warehouse2")
    public ResponseEntity<List<Warehouse2>> getAllGoodsFromWarehouse2() {
        List<Warehouse2> list = warehouse2Service.listWarehouse2();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/warehouse2/byGoodId={good_id}")
    public ResponseEntity<Warehouse2> getWarehouse2ByGoodId(@PathVariable("good_id") long good_id) {
        Warehouse2 list = warehouse2Service.findWarehouse2ByGoodId(good_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/warehouse2/addGoodToWarehouse2", consumes = "application/json", produces = "application/json")
    public Warehouse2 addGoodToWarehouse2(@RequestBody WarehouseCreationRequest newWarehouse) {
        return warehouse2Service.addGoodToWarehouse2(newWarehouse);
    }

    @PutMapping("/warehouse2/deleteAll")
    public void dropAllFromWarehouse2() {
        warehouse2Service.dropAll();
    }


    @PutMapping("/warehouse2/deleteByGoodId={good_id}")
    public void deleteWarehouse2(@PathVariable("good_id") long good_id) {
        warehouse2Service.deleteGoodsFromWarehouse2(good_id);
    }
}
