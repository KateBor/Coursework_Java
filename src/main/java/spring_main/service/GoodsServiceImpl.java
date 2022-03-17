package spring_main.service;

import spring_main.entity.*;
import spring_main.exception.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_main.repository.*;
import spring_main.request.*;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Good findGoodById(Long id) {
        Optional<Good> optionalGood = goodsRepository.findById(id);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new entityNotFoundException("Can't find this good");
        }
    }

    @Override
    public Good findGoodByName(String name) {
        Optional<Good> optionalGood = goodsRepository.findGoodByName(name);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new entityNotFoundException("Can't find this good");
        }
    }

    @Override
    public List<Good> listGoods() {
        return (List<Good>) goodsRepository.findAll();
    }

    @Override
    public void createGood(GoodCreationRequest good) {
        Good goodToCreate = new Good();
        BeanUtils.copyProperties(good, goodToCreate);
        goodToCreate.setSales(new LinkedList<>());
        Optional<Good> optionalGood = goodsRepository.findGoodByName(goodToCreate.getName());
        if (optionalGood.isEmpty()) {
            goodsRepository.save(goodToCreate);
        } else {
            System.out.println("This good has already existed");
        }
    }

    @Transactional
    @Override
    public void dropAll() {
        goodsRepository.dropAll();
    }

}
