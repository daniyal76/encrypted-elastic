package com.vaghar.elasticencryption.document.resource;

import com.vaghar.elasticencryption.document.model.DocumentEntity;
import com.vaghar.elasticencryption.document.service.DocumentResult;
import com.vaghar.elasticencryption.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentResource {

    private final DocumentService service;

    @PostMapping("/upload")
    public DocumentEntity upload(@RequestParam String content) throws Exception {
        return service.saveDocument(content);
    }

    @GetMapping("/search")
    public List<DocumentResult> search(@RequestParam String keyword) throws Exception {
        return service.searchDocuments(keyword);
    }

    @GetMapping("/{id}/decrypt")
    public String decrypt(@PathVariable String id) throws Exception {
        return service.decryptDocument(id);
    }
}
