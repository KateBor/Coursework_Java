package spring_main.service;

import spring_main.entity.*;
import spring_main.request.*;
import java.util.List;

public interface GoodsService {

    public Good findGoodById(Long id);
    public Good findGoodByName(String name);
    public List<Good> listGoods();
    public void createGood(GoodCreationRequest good);
    public void dropAll();
}
