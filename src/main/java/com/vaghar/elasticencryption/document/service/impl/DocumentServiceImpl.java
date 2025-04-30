package com.vaghar.elasticencryption.document.service.impl;

import com.vaghar.elasticencryption.document.model.DocumentEntity;
import com.vaghar.elasticencryption.document.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Override
    public DocumentEntity saveDocument(String content) {
        return null;
    }

    @Override
    public List<DocumentEntity> searchDocuments(String keyword) {
        return List.of();
    }

    @Override
    public String decryptDocument(String id) {
        return "";
    }
}
