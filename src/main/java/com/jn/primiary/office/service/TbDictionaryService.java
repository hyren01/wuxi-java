package com.jn.primiary.office.service;

import au.com.bytecode.opencsv.CSVReader;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.dao.*;
import com.jn.primiary.office.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TbDictionaryService {
    @Autowired
    private TbDictionaryRepository tbDictionaryRepository;
    @Autowired
    private TbStdglCnameTableRepository tbStdglCnameTableRepository;
    @Autowired
    TbStdglDatatypeTableRepository tbStdglDatatypeTableRepository;
    @Autowired
    TbStdglCodetitlemessTableRepository tbStdglCodetitlemessTableRepository;
    @Autowired
    private TbStdglCodePrefixRepository tbStdglCodePrefixRepository;

    public void adddictionary(String url) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(url), ',');
        String[] nextLine;
        List<TbDictionary> list = new ArrayList<>();
        Iterable iter = list;

        while ((nextLine = reader.readNext()) != null) {

            try {
                TbDictionary dictionary = new TbDictionary();
                dictionary.setId(CommonUtil.getUUID());
                dictionary.setWord(nextLine[0]);
                dictionary.setPhonetic(nextLine[1]);
                dictionary.setTranslation(nextLine[3]);
                list.add(dictionary);

                if (list.size() > 5000) {
                    tbDictionaryRepository.save(iter);
                    list.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                list.clear();
            }
        }
        tbDictionaryRepository.save(iter);


    }

    public String getename(String cname) {
        List<Term> termList = StandardTokenizer.segment(cname);
        String ename = "";
        for (Term item : termList) {
            String word = item.word;
            List<TbDictionary> dictionarylist = tbDictionaryRepository.findenamebycname(word);
            if (dictionarylist.size() > 0) {
                ename += dictionarylist.get(0).getWord();
                continue;
            }

            List<TbDictionary> dictionary_fuzzylist = tbDictionaryRepository.findenamebycnamefuzzy(word);
            if (dictionary_fuzzylist.size() > 0) {
                ename += dictionary_fuzzylist.get(0).getWord();
                continue;
            }
        }
        return ename;
    }

    public String getEname(String cname, String duanluo) {
        String ename = "";
        //先去库里找，有没有全匹配的中文
        TbStdglCnameTable tbStdglCnameTable = tbStdglCnameTableRepository.getEname(cname);
        if (!ObjectUtils.isEmpty(tbStdglCnameTable)) {
            ename = tbStdglCnameTable.getEname();
        } else {
            //没有全匹配的，就按中文名%,模糊搜索
            List<TbStdglCnameTable> list = tbStdglCnameTableRepository.getSimpleEname(cname);
            if (list != null && list.size() == 1) {
                ename = list.get(0).getEname();
            } else if (list.size() == 0) {
                //如果码表英文名翻译出来是空的话，就随机取10个字母，作为英文名
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    char c = (char) (Math.random() * 26 + 'a');
                    sb.append(c);
                }
                ename = sb.toString();
            } else {
                for (int i = 0; i < list.size(); i++) {
                    String tmpcname = list.get(i).getCname();
                    String tmp_duanluo = parseDuanLuo(duanluo);
                    if (tmp_duanluo.equals(tmpcname)) {
                        ename = list.get(i).getEname();
                        break;
                    }
                }
            }
        }
        return ename;
    }

    public String getCode(String cname) {
        String code = "";
        TbStdglCnameTable tbStdglCnameTable = tbStdglCnameTableRepository.getEname(cname);
        if (!ObjectUtils.isEmpty(tbStdglCnameTable)) {
            code = tbStdglCnameTable.getCode();
        } else {
            List<TbStdglCnameTable> list = tbStdglCnameTableRepository.getSimpleEname(cname);
            if (list != null && list.size() == 1) {
                code = list.get(0).getCode();
            } else if (list.size() == 0) {
                //如果码表英文名翻译出来是空的话，就随机取10个字母，作为code
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    char c = (char) (Math.random() * 26 + 'a');
                    sb.append(c);
                }
                code = sb.toString();
            }
        }
        return code;
    }

    public String getSimpleEname(String cname) {
        String ename = "";
        List<TbStdglCnameTable> list = tbStdglCnameTableRepository.getSimpleEname(cname);
        if (list != null && list.size() == 1) {
            ename = list.get(0).getEname();
        }
        return ename;
    }

    public String getType(String type) {
        String finalcname = "";
        TbStdglDatatypeTable tbStdglDatatypeTable = tbStdglDatatypeTableRepository.getType(type);
        if (!ObjectUtils.isEmpty(tbStdglDatatypeTable)) {
            finalcname = tbStdglDatatypeTable.getFinal_cname();
        } else {
            finalcname = type;
        }
        return finalcname;
    }

    public String getvalue(String title) {
        String titlevalue = "";
        TbStdglCodetitlemessTable tb = tbStdglCodetitlemessTableRepository.getcodevalue(title);
        if (!ObjectUtils.isEmpty(tb)) {
            titlevalue = tb.getCodeinfo_title_value();
        }
        return titlevalue;
    }

    public String parseDuanLuo(String str) {
        str = str.replaceAll(" ", "");
        str = str.replaceFirst("\\.", "");
        str = str.replaceFirst("-", "");
        String result = "";

        if (str.contains(" ")) {
            //比如：表1 火车元数据
            String pre = str.split(" ")[0];
            if (pre.startsWith("表")) {
                result = str.split(" ")[1];
            } else {
                result = str;
            }
        } else {
            if (str.startsWith("表")) {
                String tmpstr = str.substring(2);
                char[] chars = tmpstr.toCharArray();
                for (char aChar : chars) {
                    if (aChar == '.') {
                        tmpstr = tmpstr.substring(1);
                        continue;
                    }
                    Pattern p = Pattern.compile("[0-9]");
                    Matcher m = p.matcher(String.valueOf(aChar));
                    if (m.find()) {
                        tmpstr = tmpstr.substring(1);
                    } else {
                        break;
                    }
                }
                result = tmpstr;
            } else {
                result = str;
            }
        }
        return result;
    }


    public String getPrefixByfilename(String prefix) {
        TbStdglCodePrefix tbStdglCodePrefix = tbStdglCodePrefixRepository.getPrefixByfilename(prefix);
        if (tbStdglCodePrefix == null || StringUtils.isBlank(tbStdglCodePrefix.getPrefix())) {
            return "my";
        }
        String result = tbStdglCodePrefix.getPrefix();
        return result;
    }

}
