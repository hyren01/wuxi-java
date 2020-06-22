package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 用于封装数据检测结果信息
 */
public class DataCheckResultVo {

    private String id;
    private String name;
    private Integer nullNum;
    private Integer overLenNum;
	private Integer notInCodeNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNullNum() {
        return nullNum;
    }

    public void setNullNum(Integer nullNum) {
        this.nullNum = nullNum;
    }

    public Integer getOverLenNum() {
        return overLenNum;
    }

    public void setOverLenNum(Integer overLenNum) {
        this.overLenNum = overLenNum;
    }

    public Integer getNotInCodeNum() {
        return notInCodeNum;
    }

    public void setNotInCodeNum(Integer notInCodeNum) {
        this.notInCodeNum = notInCodeNum;
    }

    public DataCheckResultVo(String id, Integer nullNum, Integer overLenNum, Integer notInCodeNum) {
        this.id = id;
        this.nullNum = nullNum;
        this.overLenNum = overLenNum;
        this.notInCodeNum = notInCodeNum;
    }

    public DataCheckResultVo() {
        super();
    }
}

	
