package com.jn.primiary.beyondsoft.service;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.dao.CodeInfoRepository;
import com.jn.primiary.beyondsoft.dao.ModuleRepository;
import com.jn.primiary.beyondsoft.dao.BSTbStdglFiletableRepository;
import com.jn.primiary.beyondsoft.entity.AuthType;
import com.jn.primiary.beyondsoft.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StdCountService {

    @Autowired
    private ModuleRepository std_rep;
    @Autowired
    private CodeInfoRepository codeinfo_rep;
    @Autowired
    private BSTbStdglFiletableRepository file_rep;
    public JSONObject getcountmess(){
        //标准数量
        long std_num = std_rep.count();
        //码表数量
        long codeinfo_std = codeinfo_rep.count();
        //文档数量
        long filecount = file_rep.getfilecount(AuthType.SHTG.getCode(), Status.SHIXIAO.getCode());
        //现有模型数
        long modelnum=0;
        //检测数量
        long detectnum=0;
        JSONObject obj = new JSONObject();
        obj.put("std_num",std_num);
        obj.put("codeinfo_std",codeinfo_std);
        obj.put("filecount",codeinfo_std);
        obj.put("modelnum",modelnum);
        obj.put("detectnum",detectnum);
        return obj;
    }
}
