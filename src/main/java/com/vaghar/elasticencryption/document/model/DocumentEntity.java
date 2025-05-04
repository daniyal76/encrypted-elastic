package com.vaghar.elasticencryption.document.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@Document(indexName="secure-document")
@EqualsAndHashCode
public class DocumentEntity {

    @Id
    private String id;

    private List<String> keywords;

    private String content;

}
