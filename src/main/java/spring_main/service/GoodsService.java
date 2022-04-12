package spring_main.service;

import spring_main.entity.Good;
import spring_main.request.GoodCreationRequest;

import java.util.List;


public interface GoodsService {

    Good findGoodById(Long id);

    Good findGoodByName(String name);

    List<Good> listGoods();

    Good createGood(GoodCreationRequest good);

    void dropAll();
}
