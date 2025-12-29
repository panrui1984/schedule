package com.yakx.framework.util;

import com.yakx.common.exception.ServiceRuntimeException;
import com.yakx.common.exception.Error;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AccessTokenHolder {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenHolder.class);

    private final String appKey = "";
    private final String appSecret = "";
    private final String tokenUrl = "https://open.ys7.com/api/lapp/token/get";
    private CloseableHttpClient httpClient;

    public AccessTokenHolder() {
        httpClient = HttpClients.createDefault();
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {

        this.httpClient = httpClient;
    }

    protected String fetchAccessToken() {
        logger.debug("[{}]:fetching a new access token.", appKey);

        String url = String.format(this.tokenUrl);
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("appKey", getAppKey()));
        params.add(new BasicNameValuePair("appSecret", getAppSecret()));
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setParameters(params);
        httpPost.setEntity(entityBuilder.build());
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new ServiceRuntimeException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            String responseContent = entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);

            Error error = Error.fromJson(responseContent);

            if (error.getCode() != 200) {
                throw new ServiceRuntimeException(error);
            }
            return responseContent;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            logger.error("fetching a new token failed: {} failed.", url, ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 获取access token
     * @return
     */
    public abstract AccessToken getAccessToken();

    /**
     * 强制刷新
     */
    public abstract void refreshToken();

    /**
     * 强制设置token过期
     */
    public abstract void expireToken();
}
