package com.vaghar.elasticencryption.document.service.impl;

import com.vaghar.elasticencryption.document.dao.DocumentRepository;
import com.vaghar.elasticencryption.document.model.DocumentEntity;
import com.vaghar.elasticencryption.document.service.DocumentResult;
import com.vaghar.elasticencryption.document.service.DocumentService;
import com.vaghar.elasticencryption.encryption.EncryptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final EncryptionServiceImpl encryptionService;

    private final DocumentRepository dao;

    @Override
    public DocumentEntity saveDocument(String content) throws Exception {
        DocumentEntity doc = new DocumentEntity();

        List<String> keywords = extractKeywords(content);

        List<String> encryptedKeywords = keywords.stream()
                .map(keyword -> {
                    try {
                        return encryptionService.hashForSearch(keyword.toLowerCase());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        String encryptedContent = encryptionService.encrypt(content);

        doc.setKeywords(encryptedKeywords);
        doc.setContent(encryptedContent);

        return dao.save(doc);
    }

    @Override
    public List<DocumentResult> searchDocuments(String keyword) throws Exception {
        String encryptedKeyword = encryptionService.hashForSearch(keyword);
        return dao.findByKeywordsIn(encryptedKeyword).stream().map(encrypted -> {
            DocumentResult result;
            try {
                result = new DocumentResult();
                result.setContent(encryptionService.decrypt(encrypted.getContent()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return result;
        }).toList();
    }

    @Override
    public String decryptDocument(String id) throws Exception {
        DocumentEntity doc = dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return encryptionService.decrypt(doc.getContent());
    }

    private List<String> extractKeywords(String content) {
        // Very simple keyword extraction: split by non-word characters
        return Arrays.stream(content.split("\\s+"))
                .filter(word -> word.length() > 2) // ignore tiny words
                .distinct()
                .collect(Collectors.toList());
    }
}
