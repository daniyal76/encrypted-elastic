package com.vaghar.elasticencryption.document.service;

import com.vaghar.elasticencryption.document.model.DocumentEntity;

import java.util.List;

public interface DocumentService {
    DocumentEntity saveDocument(String content) throws Exception;

    List<DocumentResult> searchDocuments(String keyword) throws Exception;

    String decryptDocument(String id) throws Exception;
}
