package com.jn.primiary.office.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_dictionary")
@Entity
public class TbDictionary {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    @Column(name = "word", nullable = false, length = 100)
    private String word;

    @Column(name = "phonetic", nullable = false, length = 100)
    private String phonetic;

    @Column(name = "translation", nullable = false, length = 2048)
    private String translation;



    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
