package com.jn.primiary.beyondsoft.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysRole;
import com.jn.primiary.beyondsoft.entity.SysUser;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy
public class UkeyTokenUtil {
    private Logger logger = Logger.getLogger(UkeyTokenUtil.class);
    @Value("${App_id}")
    private String AppId;
    @Value("${cas_url}")
    private String cas_url; //ukey单点登录服务器地址
    @Value("${trx_door_url}")
    private String trx_door_url;    //门户地址
    @Value("${trx_getusermess_url}")
    private String trx_getusermess_url; //获取用户信息
    @Value("${trx_getuserrole_url}")
    private String trx_getuserrole_url; //获取用户角色
    @Value("${trx_token_on_line_url}")
    private String trx_token_on_line_url; //token保活
    @Autowired
    private HttpClientUtil httpClientUtil;

    public SysUser validateLoginByCas(String AppId, String token, HttpServletRequest request) throws Exception {
        SysUser user = new SysUser();
        try {
            String local_ip = WebUtil.getIP(request);
            JSONObject responseJson = verifyToken(token, local_ip);
            if(responseJson != null){
                tokenOnline(token); //{“result”:0,“userId”:”123”}
            }

            String result = responseJson.getString("result");
            String userId = responseJson.getString("userId");
            logger.info("------------------------------------------------完成verifyToken");
            if (!"0".equals(result)) {
                throw  new CommonException("ukey单点登录验证失败");
            } else {
                logger.info("--------------------------------------开始跳转页面" + local_ip);
                JSONObject userRoleMessObj = new JSONObject();
                try {
                    userRoleMessObj = getUserMess(userId);
                    logger.info("--------------------------------------返回的用户信息" + userRoleMessObj);
                } catch (Exception e1) {
                    throw new Exception(e1.getMessage());
                }

                UkeyUser userMess = userRoleMessObj.getObject("userMess", UkeyUser.class);
                JSONArray roleMessArray = userRoleMessObj.getJSONArray("roleMess");
                String UserName = userMess.userName;
                String user_id = "";
                String user_password = "";

                if (UserName.startsWith("wx_")) {
                    //操作员
                    user_id = "2001";
                    user_password = "111111";
                } else {
                    //管理员
                    user_id = "2001";
                    user_password = "111111";
                }
                user.setUsername(user_id);
                user.setPassword(user_password);

                List<SysRole> roleList = new ArrayList<>();
                for (int i = 0; i <roleMessArray.size() ; i++) {
                    SysRole role = new SysRole();
                    role.setRolename(roleMessArray.getJSONObject(i).getString("roleName"));
                    role.setType(roleMessArray.getJSONObject(i).getString("roleType"));
                    roleList.add(role);
                }
                user.setRolelist(roleList);

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return user;
    }

    /**
     * 验证token
     * @param token
     * @param client_ip
     * @return
     * @throws Exception
     */
    private JSONObject verifyToken(String token, String client_ip) throws Exception {
        logger.info("-------------------------------------进入verifyToken");
        // 传参数
        JSONObject param = new JSONObject();
        param.put("token", token);
        param.put("client_ip", client_ip);

        String responseStr = "";
        try(CloseableHttpClient hp = HttpClientUtil.getTrustHttpsCertificatesHttpClient()){
            responseStr = httpClientUtil.postJsonCommit(trx_getusermess_url, param.toJSONString(),hp);
            logger.info("-------------------------------------result="+responseStr);



            /*String responseResult = "{\n" +
				"    \"result\": 0,\n" +
				"    \"userId\": \"1\"\n" +
				"}";*/

        }catch (Exception e){
            throw new CommonException("验证token失败");
        }
        JSONObject responseJson = JSONObject.parseObject(responseStr);
        return responseJson;
    }

    /**
     * token保活
     */
    public String tokenOnline(String token) throws CommonException {
        logger.info("-------------------------------------进入token保活");
        String responseStr = "";
        try(CloseableHttpClient hp = HttpClientUtil.getTrustHttpsCertificatesHttpClient()){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put(token,"");
            responseStr = httpClientUtil.post_with_form(trx_token_on_line_url, paramMap);
            logger.info("---------------------------------token保活result="+responseStr);
        }catch (Exception e){
            throw new CommonException("token保活失败");
        }
        return responseStr;
    }

    /**
     * 获取用户信息
     * @param reqUserId
     * @return
     * @throws Exception
     */
    private JSONObject getUserMess(String reqUserId) throws Exception {
        logger.info("-------------------------------------进入获取用户信息");
        JSONObject resultObj = new JSONObject();
        String responseStr = "";
        try(CloseableHttpClient hp = HttpClientUtil.getTrustHttpsCertificatesHttpClient()){
            JSONObject param = new JSONObject();
            param.put("reqUserId", reqUserId);
            responseStr = httpClientUtil.postJsonCommit(trx_getusermess_url, param.toJSONString(),hp);
            logger.info( "-------------------------------------用户信息查询result="+responseStr);

            JSONObject responseObj = JSONObject.parseObject(responseStr);
            JSONArray ukeyUserArray = responseObj.getObject("data", JSONArray.class);
            for (int i = 0; i < ukeyUserArray.size(); i++) {
                UkeyUser ukeyUser = ukeyUserArray.getObject(i, UkeyUser.class);
                String UserID = ukeyUser.userId;
                JSONArray userRoleMessArray = getUserRoleMess(reqUserId, UserID);
                resultObj.put("userMess",ukeyUser);
                resultObj.put("roleMess",userRoleMessArray);
            }

        }catch (Exception e){
            throw new CommonException("获取用户信息失败");
        }
        return resultObj;
    }

    /**
     * 获取角色信息
     * @return
     * @throws Exception
     */
    private JSONArray getUserRoleMess(String reqUserId,String UserID) throws CommonException {
        logger.info("-------------------------------------进入获取角色信息");
        String responseStr = "";
        try(CloseableHttpClient hp = HttpClientUtil.getTrustHttpsCertificatesHttpClient()){
            JSONObject param = new JSONObject();
            param.put("reqUserId", reqUserId);
            param.put("UserID", UserID);
            responseStr = httpClientUtil.postJsonCommit(trx_getusermess_url, param.toJSONString(),hp);
            logger.info( "-------------------------------------角色信息查询result="+responseStr);
        }catch (Exception e){
            throw new CommonException("获取角色信息失败");
        }
        JSONArray responseJson = JSONArray.parseArray(responseStr);
        return responseJson;
    }






    /*private JSONArray getUserMess(String appid, String userid) throws Exception {

        logger.info("-------------------------------------进入获取用户信息");
        CloseableHttpClient hp = HttpClientUtil.getTrustHttpsCertificatesHttpClient();
        String url = trx_door_url+"/"+appid+"/"+userid;
        logger.info("-------------------------------------url="+url);

		*//*List<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
		pairs.add(new BasicNameValuePair(key, params.get(key).toString()));*//*
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = hp.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseStr = EntityUtils.toString(entity,"utf-8");
        logger.info( "-------------------------------------用户信息查询result="+responseStr);
        hp.close();
        JSONArray responseJson = JSONArray.parseArray(responseStr);
        return responseJson;
    }*/

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getCas_url() {
        return cas_url;
    }

    public void setCas_url(String cas_url) {
        this.cas_url = cas_url;
    }

    public String getTrx_door_url() {
        return trx_door_url;
    }

    public void setTrx_door_url(String trx_door_url) {
        this.trx_door_url = trx_door_url;
    }

    public String getTrx_getusermess_url() {
        return trx_getusermess_url;
    }

    public void setTrx_getusermess_url(String trx_getusermess_url) {
        this.trx_getusermess_url = trx_getusermess_url;
    }

    public String getTrx_getuserrole_url() {
        return trx_getuserrole_url;
    }

    public void setTrx_getuserrole_url(String trx_getuserrole_url) {
        this.trx_getuserrole_url = trx_getuserrole_url;
    }
}

class UkeyUser{
    public String userName;
    public String userId;
    public String userUnit;
    public String loginName;
    public String orgId;
    public String status;

    public UkeyUser(String userName, String userId, String userUnit, String loginName, String orgId, String status) {
        this.userName = userName;
        this.userId = userId;
        this.userUnit = userUnit;
        this.loginName = loginName;
        this.orgId = orgId;
        this.status = status;
    }
}
