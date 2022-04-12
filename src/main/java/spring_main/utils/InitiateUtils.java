package spring_main.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring_main.entity.*;
import spring_main.repository.UserRepository;
import spring_main.request.GoodCreationRequest;
import spring_main.request.SaleCreationRequest;
import spring_main.request.WarehouseCreationRequest;
import spring_main.service.GoodsServiceImpl;
import spring_main.service.SalesServiceImpl;
import spring_main.service.Warehouse1ServiceImpl;
import spring_main.service.Warehouse2ServiceImpl;

import java.util.Collections;


@Service
@RequiredArgsConstructor
@Component
public class InitiateUtils implements CommandLineRunner {

    @Autowired
    private final GoodsServiceImpl goodsService;
    @Autowired
    private final SalesServiceImpl salesService;
    @Autowired
    private final Warehouse1ServiceImpl warehouse1Service;
    @Autowired
    private final Warehouse2ServiceImpl warehouse2Service;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) {

        try {
            userRepository.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        } catch (Exception e) {
            System.out.println("This user already exists");
        }
        try {
            userRepository.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
        } catch (Exception e) {
            System.out.println("This admin already exists");
        }

        //Request 0 (Очистка таблиц)
        goodsService.dropAll();
        salesService.dropAll();
        warehouse1Service.dropAll();
        warehouse2Service.dropAll();

        System.out.println("\nТаблица пользователей\n");
        for (User user : userRepository.findAll()) {
            System.out.println(user.toString());
        }

        System.out.println("\nАктивный пользователь\n");
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().toString()); //- null

        //Request 1
        goodsService.createGood(new GoodCreationRequest("Apple", 4.3f));
        goodsService.createGood(new GoodCreationRequest("Strawberry", 5.2f));
        goodsService.createGood(new GoodCreationRequest("Chocolate", 2.7f));
        goodsService.createGood(new GoodCreationRequest("Meat", 8.8f));
        goodsService.createGood(new GoodCreationRequest("Cheese", 7.1f));
        goodsService.createGood(new GoodCreationRequest("Orange", 4.6f));
        goodsService.createGood(new GoodCreationRequest("Bread", 6.3f));
        goodsService.createGood(new GoodCreationRequest("Egg", 5.6f));
        goodsService.createGood(new GoodCreationRequest("Milk", 5.9f));
        goodsService.createGood(new GoodCreationRequest("Chips", 10.0f));

        long startGoodId = goodsService.listGoods().get(0).getId();


        //Request 2
        System.out.println("\nТаблица товаров(продукты)\n");
        for (Good good : goodsService.listGoods() ) {
            System.out.println(good.toString());
        }


        //Request 3
        warehouse1Service.addGoodToWarehouse1(new WarehouseCreationRequest(startGoodId + 1, 15));
        warehouse1Service.addGoodToWarehouse1(new WarehouseCreationRequest(startGoodId + 3, 10));
        warehouse1Service.addGoodToWarehouse1(new WarehouseCreationRequest(startGoodId + 6, 7));
        warehouse1Service.addGoodToWarehouse1(new WarehouseCreationRequest(startGoodId + 9, 2));
        long startWarehouse1Id = warehouse1Service.listWarehouse1().get(0).getId();


        //Request 4
        warehouse2Service.addGoodToWarehouse2(new WarehouseCreationRequest(startGoodId + 1, 4));
        warehouse2Service.addGoodToWarehouse2(new WarehouseCreationRequest(startGoodId + 2, 17));
        warehouse2Service.addGoodToWarehouse2(new WarehouseCreationRequest(startGoodId + 5, 9));
        warehouse2Service.addGoodToWarehouse2(new WarehouseCreationRequest(startGoodId + 8, 12));
        long startWarehouse2Id = warehouse2Service.listWarehouse2().get(0).getId();


        //Request 5
        System.out.println("\nТаблица товаров на 1 складе\n");
        for (Warehouse1 warehouse1 : warehouse1Service.listWarehouse1() ) {
            System.out.println(warehouse1.toString());
        }


        //Request 6
        System.out.println("\nТаблица товаров на 2 складе\n");
        for (Warehouse2 warehouse2 : warehouse2Service.listWarehouse2() ) {
            System.out.println(warehouse2.toString());
        }


        //Request 7
        //salesService.createSale(new SaleCreationRequest(startGoodId + 5, 5, new Timestamp(System.currentTimeMillis())));
        salesService.createSale(new SaleCreationRequest(startGoodId + 5, 5, "11/01/2020-14:30"));
        salesService.createSale(new SaleCreationRequest(startGoodId + 9, 2, "11/01/2020-15:11"));
        salesService.createSale(new SaleCreationRequest(startGoodId + 1, 17, "11/02/2020-11:00"));
        //salesService.createSale(new SaleCreationRequest(goodsService.listGoods().get(4).getId(), 2, new Timestamp(System.currentTimeMillis()))); exception - ok
//        salesService.createSale(new SaleCreationRequest(startGoodId + 9, 2, new Timestamp(new GregorianCalendar(2021, Calendar.JANUARY , 25, 18, 30, 55).getTimeInMillis())));
//        salesService.createSale(new SaleCreationRequest(startGoodId + 1, 17, new Timestamp(new GregorianCalendar(2022, Calendar.MARCH, 14, 18, 31, 56).getTimeInMillis())));
//        salesService.createSale(new SaleCreationRequest(startGoodId + 6, 3, new Timestamp(new GregorianCalendar(2021, Calendar.APRIL, 4, 18, 32, 57).getTimeInMillis())));
//        long startSaleId = salesService.listSales().get(0).getId();

        System.out.println("\nПродажи\n");
        for (Sale sale : salesService.listSales()) {
            System.out.println(sale.toString());
        }

//        //Request 8
//        System.out.println("\nТаблица продаж\n");
//        for (Sale sale : salesService.listSales()) {
//            System.out.println(sale.toString());
//        }
//        //Проверка
//        System.out.println("\nТаблица товаров на 1 складе\n");
//        for (Warehouse1 warehouse1 : warehouse1Service.listWarehouse1() ) {
//            System.out.println(warehouse1.toString());
//        }
//        System.out.println("\nТаблица товаров на 2 складе\n");
//        for (Warehouse2 warehouse2 : warehouse2Service.listWarehouse2() ) {
//            System.out.println(warehouse2.toString());
//        }
//
//
//        //Request 9
//        System.out.println("\nТаблица товаров и продаж\n");
//        for (String sale : salesService.joinGood()) {
//            System.out.println(sale);
//        }
//
//        //Request 10
//        salesService.deleteSale(salesService.listSales().get(0).getId());
//        //Проверка
////        System.out.println("\nТаблица продаж\n");
////        for (Sale sale : salesService.listSales()) {
////            System.out.println(sale.toString());
////        }
//
//
//        //Request 11
//        salesService.updateSaleCount(salesService.listSales().get(2).getId(), 6);
//        //Проверка
//        System.out.println(salesService.listSales().get(2).toString());
//        System.out.println("\nТаблица товаров на 1 складе\n");
//        for (Warehouse1 warehouse1 : warehouse1Service.listWarehouse1() ) {
//            System.out.println(warehouse1.toString());
//        }
//
//
//        //Request 12-15 (findById)
//        System.out.println("\nПоиск по Id\n");
//        System.out.println(goodsService.findGoodById(startGoodId + 2).toString());
//        System.out.println(salesService.findSaleById(startSaleId + 1).toString());
//        System.out.println(warehouse1Service.findWarehouse1ById(startWarehouse1Id + 1).toString());
//        System.out.println(warehouse2Service.findWarehouse2ById(startWarehouse2Id + 3).toString());
//
//
//        //Request 16
//        System.out.println("\nПоиск по имени товара\n");
//        System.out.println(goodsService.findGoodByName("Chocolate").toString());
//
//
//        //Request 17 - 19 (findByGoodId)
//        salesService.findSalesByGood_id(startGoodId + 1);
//        warehouse1Service.findWarehouse1ByGoodId(startGoodId + 3);
//        warehouse2Service.findWarehouse2ByGoodId(startGoodId + 5);
//
//
//        //Проверка до
////        System.out.println("\nТаблица товаров на 1 складе\n");
////        for (Warehouse1 warehouse1 : warehouse1Service.listWarehouse1() ) {
////            System.out.println(warehouse1.toString());
////        }
////        System.out.println("\nТаблица товаров на 2 складе\n");
////        for (Warehouse2 warehouse2 : warehouse2Service.listWarehouse2() ) {
////            System.out.println(warehouse2.toString());
////        }
//
//        //Request 20 - 21 (updateGoodCount)
//        warehouse1Service.updateGoodsCount(startGoodId + 8, 10);
//        warehouse2Service.updateGoodsCount(startGoodId + 9, 11);
//
//
//        //Request 22 - 23 (deleteGoodCount)
//        warehouse1Service.deleteGoodsFromWarehouse1(startWarehouse1Id + 2);
//        warehouse2Service.deleteGoodsFromWarehouse2(startWarehouse2Id + 3);

        //Проверка
//        System.out.println("\nТаблица товаров на 1 складе\n");
//        for (Warehouse1 warehouse1 : warehouse1Service.listWarehouse1() ) {
//            System.out.println(warehouse1.toString());
//        }
//        System.out.println("\nТаблица товаров на 2 складе\n");
//        for (Warehouse2 warehouse2 : warehouse2Service.listWarehouse2() ) {
//            System.out.println(warehouse2.toString());
//        }
    }
}
