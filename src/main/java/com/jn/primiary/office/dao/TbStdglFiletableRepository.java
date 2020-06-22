package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbStdglFiletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TbStdglFiletableRepository extends JpaRepository<TbStdglFiletable,Integer> {

    @Query(value="select * from tb_stdgl_filetable where file_id=?1", nativeQuery = true)
    TbStdglFiletable getfilemessbyid(String file_id);

    @Query(value="select * from tb_stdgl_filetable where status =?1 and is_auth !=2 ORDER BY upload_time desc", nativeQuery = true)
    List<TbStdglFiletable> getfilemessbystatus(int code);

    @Modifying
    @Query(value = "update tb_stdgl_filetable set is_auth=?1 where file_id=?2",nativeQuery = true)
    void audit(String isAuth,String fileId);

    @Modifying
    @Query(value = "update tb_stdgl_filetable set is_auth=?1 where db_id=?2",nativeQuery = true)
    void audit_db(String isAuth,String db_id);

    @Modifying
    @Query(value = "insert into tb_stdgl_filetable(file_id,file_name,upload_person,upload_time,is_auth,status) values" +
            "(:#{#file.file_id},:#{#file.file_name},:#{#file.upload_person},:#{#file.upload_time},:#{#file.auth_status}," +
            ":#{#file.status})",nativeQuery = true)
    void savefilebysql(@Param("file") TbStdglFiletable file);
}
