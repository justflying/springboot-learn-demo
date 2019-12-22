package com.wanyu.elasticsearch.demo.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@Data
@Document(indexName = "book",type = "novel")
public class Novel {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Integer)
    private Integer wordCount;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Date)
    private Date publishDate;

}
