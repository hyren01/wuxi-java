package com.jn.secondary.controller;

import com.jn.secondary.service.CodeInfoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/codeinfoentity")
public class CodeInfoEntityController {

    @Autowired
    CodeInfoEntityService codeInfoEntityService;

    @ResponseBody
    @RequestMapping(value = "/createcodeinfotable",method = RequestMethod.POST)
    public void createCodeInfoTable(){
        codeInfoEntityService.createCodeInfoTable();
    }
}
