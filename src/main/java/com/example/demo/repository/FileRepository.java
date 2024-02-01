package com.example.demo.repository;

import com.example.demo.domain.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    File save(File file);
    void delete(Long id);
    List<File> findAll();
    Optional<File> findById(Long id);
}
