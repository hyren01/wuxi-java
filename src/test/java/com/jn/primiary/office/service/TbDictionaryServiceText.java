package com.jn.primiary.office.service;

import com.alibaba.fastjson.JSONArray;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.jn.primiary.office.dao.TbDictionaryRepository;
import com.jn.primiary.office.entity.TbDictionary;
import com.jn.stdglApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={stdglApplication.class})
public class TbDictionaryServiceText {
    @Autowired
    private TbDictionaryRepository tbDictionaryRepository;
    @Autowired
    private TbDictionaryService tbDictionaryService;

    @Test
    public void getename(){
        List<Term> termList = StandardTokenizer.segment("中国");
        List<String> list = new ArrayList<>();
        JSONArray array = new JSONArray();
        String ename ="";
        for (Term item :termList) {
            String word = item.word;
            List<TbDictionary> dictionarylist = tbDictionaryRepository.findenamebycnamefuzzy(word);
            ename += dictionarylist.get(0);
        }
        //return ename;
    }

}
