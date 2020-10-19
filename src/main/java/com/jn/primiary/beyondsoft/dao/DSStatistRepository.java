package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.entity.FieldCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据标准统计dao
 */
@Repository
public interface DSStatistRepository extends JpaRepository<FieldCheckResult, Integer> {

    //获取现有模型数
    @Query(value = "select count(1) from tb_meta_model", nativeQuery = true)
    int getExistModelsNum();

    //获取已检测模型数
    @Query(value = "select count(DISTINCT t3.id) from tb_meta_modelfield_checkresult t1, tb_meta_modelfield t2, tb_meta_model t3 where t1.field_id = t2.id and t2.model_id = t3.id", nativeQuery = true)
    int getDetectedModelsNum();

    //获取现有标准模型数
    @Query(value = "select count(1) from tb_stdgl_schemamodule where status = 1", nativeQuery = true)
    int getStandardModelsNum();

    //获取目录下标准模型数
    @Query(value = "select count(1) from tb_stdgl_schemamodule where status = 1 and categoryid = ?1", nativeQuery = true)
    int getStandardModelsNumByCategoryId(String categoryid);

    //把当前目录的id作为其他目录的父id去查询
    @Query(value = "SELECT id FROM tb_stdgl_category WHERE parent_id = ?1 order BY pxh desc ", nativeQuery = true)
    List<String> findCategoryInfoByParentId(String id);

    //获取现有标准字段数
    @Query(value = "select count(1) from tb_stdgl_schemafield where status = 1", nativeQuery = true)
    int getStandardFieldsNum();
    //根据ID获取现有标准字段数
    @Query(value = "select count(1) from tb_stdgl_schemafield where status = 1 and moudle_id in (select id from tb_stdgl_schemamodule where status = 1 and categoryid = ?1)", nativeQuery = true)
    int getStandardFieldsNumByCategoryId(String CategoryId);


    @Query(value = "select count(DISTINCT codetable_ename) from tb_stdgl_code_info where status = 1", nativeQuery = true)
    int getCodeInfoNum();

    @Query(value = "select count(1) from tb_stdgl_filetable where status = 1", nativeQuery = true)
    int getFileNum();

    @Query(value = "select count(1) from sys_user", nativeQuery = true)
    int getUserNum();
}
