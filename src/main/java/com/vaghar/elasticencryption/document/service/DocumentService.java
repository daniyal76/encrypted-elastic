package com.vaghar.elasticencryption.document.service;

import com.vaghar.elasticencryption.document.model.DocumentEntity;

import java.util.List;

public interface DocumentService {
    DocumentEntity saveDocument(String content);

    List<DocumentEntity> searchDocuments(String keyword);

    String decryptDocument(String id);
}
