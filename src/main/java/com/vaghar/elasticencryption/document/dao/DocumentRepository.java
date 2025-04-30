package com.vaghar.elasticencryption.document.dao;

import com.vaghar.elasticencryption.document.model.DocumentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<DocumentEntity, String> {
}
