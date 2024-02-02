package com.example.demo.service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.File;
import com.example.demo.repository.FileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Long create(File file) {
        fileRepository.save(file);
        return file.getId();
    }

    public Optional<List<File>> getFilesByArticleId(Long articleId) {
        return fileRepository.findByArticleId(articleId);
    }
}
