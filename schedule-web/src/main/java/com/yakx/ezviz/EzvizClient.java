package com.yakx.ezviz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yakx.common.core.domain.event.EzvizLogEvent;
import com.yakx.common.core.domain.event.LogininforEvent;
import com.yakx.common.exception.ServiceRuntimeException;
import com.yakx.common.utils.ServletUtils;
import com.yakx.common.utils.spring.SpringUtils;
import com.yakx.ezviz.exception.*;
import com.yakx.framework.util.AccessToken;
import com.yakx.framework.util.AccessTokenHolder;
import com.yakx.framework.util.DefaultAccessTokenHolder;
import lombok.Data;
import org.apache.http.*;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EzvizClient {

    private static Logger logger = LoggerFactory.getLogger(EzvizClient.class);

    public static final String URL_ADD_TEMPORARY_KEY    = "https://open.ys7.com/api/lapp/keylock/temporary/add";

    public static final String URL_GET_TEMPORARY_KEY    = "https://open.ys7.com/api/lapp/keylock/temporary/list";

    public static final String URL_REMOVE_TEMPORARY_KEY = "https://open.ys7.com/api/lapp/keylock/temporary/delete";

    protected CloseableHttpClient httpClient;

    private AccessTokenHolder accessTokenHolder;

    public static final String UNLIMIT_TIMES            = "-1";

    public static final String RESP_CODE_SUCCESS        = "200";

    public static final String CONNECTION_FAILURE_MSG   = "连接密码锁服务器失败";

    public static final String PAGE_SIZE                = "100";


    private volatile static EzvizClient client;

    public static EzvizClient getEzvizClient(){
        if(client == null){
            synchronized (EzvizClient.class){
               client = new EzvizClient( new DefaultAccessTokenHolder());
            }
        }
        return client;
    }



    public EzvizClient(AccessTokenHolder accessTokenHolder) {
        this.accessTokenHolder = accessTokenHolder;
        httpClient = HttpClients.createDefault();
    }

    @Retryable(value = {
        EzvizPwdAddFailureException.class,
        EzvizNetworkException.class,
        EzvizNetworkException.class,
    }, maxAttempts = 3)
    public  JSONObject addTemporaryKey(
            String deviceSerial,
            String userName,
            Long secondBegin,
            Long secondEnd
    ) throws EzvizException {
        JSONObject form = new JSONObject();
        form.put("accessToken", this.getAccessToken().getAccessToken());
        form.put("deviceSerial", deviceSerial);
        form.put("lockUserName", userName);
        form.put("beginTime", secondBegin.toString());
        form.put("endTime", secondEnd.toString());
        form.put("limitTime", UNLIMIT_TIMES);
        JSONObject result = post(URL_ADD_TEMPORARY_KEY, form);
        System.out.println(result.toJSONString());
        if (RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial, RESP_CODE_SUCCESS, result.getString("msg"));
            return result.getJSONObject("data");
        } else if ("20600".equals(result.get("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial, "20600", result.getString("msg"));
            throw new EzvizPwdReachLimitException();
        } else if ("20601".equals(result.get("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial,"20601", result.getString("msg"));
            throw new EzvizPwdAddFailureException();
        } else if ("20006".equals(result.get("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial,"20006", result.getString("msg"));
            throw new EzvizNetworkException();
        } else if ("20007".equals(result.get("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial,"20007", result.getString("msg"));
            throw new EzvizOfflineException();
        } else if (!RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            recordEzvizLog("新增","申请临时密码", deviceSerial, result.getString("msg"), result.getString("msg"));
            throw new EzvizException(result.getString("msg"), result.getInteger("code"));
        }
        return result.getJSONObject("data");
    }

    /**
      * 获取设备临时密码
     */
    public List<String> getTemporaryKey(String deviceSerial) throws EzvizException {
        JSONObject form = new JSONObject();
        form.put("accessToken", this.getAccessToken().getAccessToken());
        form.put("deviceSerial", deviceSerial);
        JSONObject result = post(URL_GET_TEMPORARY_KEY, form);
        List<String> list = new ArrayList<>();
        System.out.println(result.toJSONString());
        if (RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            JSONArray jsonArray = result.getJSONArray("data");
            if(CollUtil.isNotEmpty(jsonArray)){
                for(int i = 0; i < jsonArray.size(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //处理jsonObject
                    list.add(jsonObject.getString("tempIndex"));
                }
            }
        }else if ("20006".equals(result.get("code"))) {
            recordEzvizLog("查询","获取临时密码列表", deviceSerial,"20006", result.getString("msg"));
            throw new EzvizNetworkException();
        } else if ("20007".equals(result.get("code"))) {
            recordEzvizLog("查询","获取临时密码列表", deviceSerial,"20007", result.getString("msg"));
            throw new EzvizOfflineException();
        } else if (!RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            recordEzvizLog("查询","获取临时密码列表", deviceSerial, result.getString("code"), result.getString("msg"));
            throw new EzvizException(result.getString("msg"), result.getInteger("code"));
        }
        return list;
    }

    public void removeTemporaryKey(String deviceSerial, String tempIndex) throws EzvizException {
        JSONObject form = new JSONObject();
        form.put("accessToken", this.getAccessToken().getAccessToken());
        form.put("deviceSerial", deviceSerial);
        form.put("tempIndex", tempIndex);
        System.out.println("删除设备 " + deviceSerial);
        JSONObject result = post(URL_REMOVE_TEMPORARY_KEY, form);
        if (RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            recordEzvizLog("删除", "删除临时密码", deviceSerial, RESP_CODE_SUCCESS, result.getString("msg"));
        }else if ("20006".equals(result.get("code"))) {
            recordEzvizLog("删除","删除临时密码", deviceSerial,"20006", result.getString("msg"));
            throw new EzvizNetworkException();
        } else if ("20007".equals(result.get("code"))) {
            recordEzvizLog("删除","删除临时密码", deviceSerial,"20007", result.getString("msg"));
            throw new EzvizOfflineException();
        } else if (!RESP_CODE_SUCCESS.equals(result.getString("code"))) {
            recordEzvizLog("删除","删除临时密码", deviceSerial, result.getString("msg"), result.getString("msg"));
            throw new EzvizException(result.getString("msg"), result.getInteger("code"));
        }
    }

    public void batchRemoveTemporaryKey(List<String> deviceSerials){
        Map<String, List<String>> temporaryKeyMap = new HashMap<>();
        List<String> result;

        for(String deviceSerial: deviceSerials){
            try {
                System.out.println("查询 设备 : " + deviceSerial);
                     result = getTemporaryKey(deviceSerial);
            }catch(EzvizException ex){
                continue;
            }
            if(CollUtil.isNotEmpty(result)){
                temporaryKeyMap.put(deviceSerial, result);
            }
        }
        for (Map.Entry<String,List<String>> entry:temporaryKeyMap.entrySet()) {
            String deviceSerial = entry.getKey();
            List<String> tempIndexs = entry.getValue();
            for(String tmpIndex : tempIndexs){
                removeTemporaryKey(deviceSerial, tmpIndex);
            }
        }
    }


    public JSONObject post(String url, JSONObject form) {
        try {
            return httpPost(url, form);
        } catch (Exception e) {
            if(e instanceof ServiceRuntimeException) {
                ServiceRuntimeException serviceRuntimeException = (ServiceRuntimeException) e;
                if(invalidToken(serviceRuntimeException.getCode())) {
                    logger.warn("token invalid: {}, will refresh.", serviceRuntimeException.getCode());
                    refreshToken();
                    return httpPost(url, appendAccessToken(form));
                }
            }
            throw e;
        }
    }


    private JSONObject httpPost(String url, JSONObject form) {
        HttpPost httpPost = new HttpPost(url);
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8));

        List<NameValuePair> params = new ArrayList<>();
        for(String key : form.keySet()) {
            params.add(new BasicNameValuePair(key, form.getString(key)));
        }
        entityBuilder.setParameters(params);


        httpPost.setEntity(entityBuilder.build());
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new RuntimeException("error");
            }
            String responseContent = entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);
            return JSON.parseObject(responseContent);
        } catch (IOException ex) {
            logger.error("http post: {} failed", url, ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 附加AccessToken
     */
    private JSONObject appendAccessToken(JSONObject form) {
        String token = accessTokenHolder.getAccessToken().getAccessToken();
        logger.debug("access token: {}", token);
        form.put("accessToken",  token);
        return form;
    }

    public void refreshToken() {
        accessTokenHolder.refreshToken();
    }

    public AccessToken getAccessToken() {
        return accessTokenHolder.getAccessToken();
    }

    private boolean invalidToken(int code) {
        boolean result = code == 42001 || code == 40001 || code == 40014;
        if(result) {
            accessTokenHolder.expireToken();//强制设置为无效
        }
        return result;
    }

    private void recordEzvizLog(String businessType, String method, String deviceSerial, String code, String message) {
        EzvizLogEvent ezvizLogEvent = new EzvizLogEvent();
        ezvizLogEvent.setModule("萤石");
        ezvizLogEvent.setBusinessType(businessType);
        ezvizLogEvent.setMethod(method);
        ezvizLogEvent.setDeviceSerial(deviceSerial);
        ezvizLogEvent.setCode(code);
        ezvizLogEvent.setMessage(message);
        SpringUtils.context().publishEvent(ezvizLogEvent);
    }

    /*
    public static void main(String[] args) throws Exception{
        EzvizClient client = new EzvizClient(new DefaultAccessTokenHolder());

        Long       current = System.currentTimeMillis()/1000;
        Long       begin1   = current +  3600;
        Long       end1     = current +  7200;
        Long       begin2   = current + 10800;
        Long       end2     = current + 14400;
        List<String> list = client.getTemporaryKey("BB3511373");
        for(String index: list)
            System.out.println(index);
    }
*/
}
