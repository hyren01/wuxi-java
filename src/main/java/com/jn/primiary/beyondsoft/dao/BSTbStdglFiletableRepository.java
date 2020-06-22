package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.BSTbStdglFiletable;
import com.jn.primiary.office.entity.TbStdglFiletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BSTbStdglFiletableRepository extends JpaRepository<BSTbStdglFiletable,Integer> {

    @Query(value="select * from tb_stdgl_filetable where file_id=?1", nativeQuery = true)
    TbStdglFiletable getfilemessbyid(String file_id);

    @Query(value="select * from tb_stdgl_filetable where status !=?1", nativeQuery = true)
    List<TbStdglFiletable> getfilemessbystatus(int code);

    @Query(value="select count(*) from tb_stdgl_filetable where is_auth=?1 and status!=?2", nativeQuery = true)
    long getfilecount(int is_auth, int status);
}
