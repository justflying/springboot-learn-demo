package com.wanyu.elasticsearch.demo.controller;

import com.google.common.collect.Lists;
import com.wanyu.elasticsearch.demo.common.CommonResult;
import com.wanyu.elasticsearch.demo.dao.NovelRepository;
import com.wanyu.elasticsearch.demo.domain.Novel;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/book/novel")
public class NovelController {

    @Autowired
    private NovelRepository novelRepository;

    /**
     * 新增记录，前端使用postman 直接调用
     * {
     * 	"id" : 1,
     * 	"author":"瓦力",
     * 	"title":"ElasticSearch入门到放弃",
     * 	"wordCount": 1000,
     * 	"publishDate":"2009-12-12"
     *
     * }
     * @param novel 小说
     */
    @PostMapping(value = "/add")
    public CommonResult save(@RequestBody Novel novel){
        return CommonResult.success(novelRepository.save(novel));
    }

    @PostMapping(value = "/batch/add")
    public CommonResult batchSave(@RequestBody List<Novel> novels){
        return CommonResult.success(Lists.newArrayList(novelRepository.saveAll(novels)));
    }

    @GetMapping(value = "/list")
    public CommonResult list(){
        Iterable<Novel> novels = novelRepository.findAll(Sort.by("publishDate").ascending());
        return CommonResult.success(Lists.newArrayList(novels));
    }

    @GetMapping(value = "/author")
    public CommonResult findByAuthor(@RequestParam("name") String name){
        return CommonResult.success(novelRepository.findByAuthor(name));
    }

    @GetMapping(value = "/word-count/{left}/{right}")
    public CommonResult findByWordCountBetween(@PathVariable("left") Integer left,
                                               @PathVariable("right")Integer right){
        return CommonResult.success(novelRepository.findByWordCountBetween(left,right));
    }
}
