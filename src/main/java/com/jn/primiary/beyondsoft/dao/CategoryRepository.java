package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryInfo,String> {
    //把当前目录的id作为其他目录的父id去查询
    @Query(value="SELECT id,name,parent_id,creator,create_time,pxh,status,ismodel FROM tb_stdgl_category WHERE parent_id = ?1 order BY pxh desc ", nativeQuery = true)
    List<CategoryInfo> findCategoryInfoByParentId(String id);

    //查询当前目录信息
    @Query(value = "select * from tb_stdgl_category where id = ?1",nativeQuery = true)
    List<CategoryInfo> queryCategoryInfoById(String id);

    @Query(value = "select * from tb_stdgl_category where id = ?1",nativeQuery = true)
    CategoryInfo findCategoryInfoById(String id);

    //查询所有目录的目录名和id
    //@Query(value = "select new CategoryInfo(ca.id,ca.name) from CategoryInfo ca")
    @Query(value = "select * from tb_stdgl_category",nativeQuery = true)
    List<CategoryInfo> getCategoryInfo();

    @Query(value = "select * from tb_stdgl_category order by parent_id asc ,pxh asc",nativeQuery = true)
    List<CategoryInfo> getCategoryInfoOrderByPxh();

    //删除当前目录，如果有子目录或者标准则不能删除
    @Modifying
    @Transactional
    @Query(value = "DELETE  FROM tb_stdgl_category WHERE id = ?1",nativeQuery = true)
    void deleteCategoryInfoById(String id);

    //新建目录
    @Override
    <S extends CategoryInfo> S save(S s);

    @Modifying
    @Transactional
    @Query(value="UPDATE tb_stdgl_category SET name = ?1, parent_id = ?2 ,pxh = ?3 WHERE id = ?4", nativeQuery = true)
    void updataCategoryInfoById(String name, String parentId, Integer pxh, String id);

    @Query(value="select max(pxh) from tb_stdgl_category where parent_id=?1", nativeQuery = true)
    String getpxh(String id);

    @Query(value="select * from tb_stdgl_category where name=?1 and parent_id=?2", nativeQuery = true)
    List<CategoryInfo> getcategorybyname(String category_name,String parent_id);

    @Query(value="select * from tb_stdgl_category where name=?1 and id!=?2", nativeQuery = true)
    List<CategoryInfo> getcategorybynameandid(String category_name, String id);

}
