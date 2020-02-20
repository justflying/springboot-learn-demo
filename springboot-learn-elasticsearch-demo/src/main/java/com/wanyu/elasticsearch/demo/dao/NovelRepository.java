package com.wanyu.elasticsearch.demo.dao;

import com.wanyu.elasticsearch.demo.domain.Novel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends ElasticsearchRepository<Novel,Long> {

    /**
     * 根据某个属性查找，无需写实现，这要归功于spring data 的强大
     * 具体对应请看 readme.md里面的表格
     * @param name
     * @return
     */
    List<Novel> findByAuthor(String name);

    List<Novel> findByWordCountBetween(Integer left , Integer right);


}
