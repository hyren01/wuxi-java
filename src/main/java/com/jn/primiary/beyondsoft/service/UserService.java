package com.jn.primiary.beyondsoft.service;


import com.jn.primiary.beyondsoft.dao.PermissionRepository;
import com.jn.primiary.beyondsoft.dao.SysRoleRepository;
import com.jn.primiary.beyondsoft.dao.SysUserRepository;
import com.jn.primiary.beyondsoft.entity.Permission;
import com.jn.primiary.beyondsoft.entity.SysRole;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public SysUser login(String username){
        SysUser user = sysUserRepository.findbyusername(username);
        return user;
    }

    public SysUser findbyid(String id){
        SysUser user = sysUserRepository.findbyid(id);
        List<SysRole> roleList = sysRoleRepository.findbyuserid(id);
        for (SysRole role:roleList) {
            List<Permission> permissonlist = permissionRepository.findbyroleid(role.getId());
            role.setPermissionlist(permissonlist);
        }
        user.setRolelist(roleList);
        return user;
    }

}
