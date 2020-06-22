package com.jn.secondary.service;

import com.jn.secondary.dao.CodeInfoEntityRepository;
import com.jn.secondary.impl.CodeInfoEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeInfoEntityService implements CodeInfoEntityImpl {

    @Autowired
    CodeInfoEntityRepository codeInfoEntityRepository;

    @Override
    public void createCodeInfoTable() {
        codeInfoEntityRepository.createCodeInfoTable();
    }
}
