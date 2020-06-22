package com.jn.primiary.standard.dao;

import java.util.List;

//import com.baomidou.mybatisplus.mapper.BaseMapper;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.standard.entity.StandardRequest;



/**
 *
 * @author wld
 * @since 2019-07-18 11:00
 */



public interface StandardRequestRepository extends JpaRepository <StandardRequest,Integer> {
	
	StandardRequest findById(String id);
	
	@Query(value = "SELECT 1 AS fun_type, id, resource_code, request_person, resource_id, resource_content, request_time, type, audit_time, audit_person, audit_result, audit_message"
			+ " FROM tb_stdgl_request UNION ALL "
			+ "SELECT 2 AS fun_type, id, '', create_user, '', '{}', DATE_FORMAT(CONCAT_WS('', create_date, create_time), '%Y-%m-%d %H:%i:%s'), auth_type, '', '', is_auth, auth_remark"
			+ " FROM tb_stdgl_code_info ORDER BY request_time DESC", nativeQuery = true)
	List<StandardRequest> findStandardAndCodeInfo();
	  
}