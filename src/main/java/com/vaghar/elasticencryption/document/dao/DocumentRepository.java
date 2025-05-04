package com.vaghar.elasticencryption.document.dao;

import com.vaghar.elasticencryption.document.model.DocumentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<DocumentEntity, String> {
    List<DocumentEntity> findByKeywordsIn(String keyword);

    List<DocumentEntity> findByKeywordsContains(String keyword);
}
