package cn.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
@Component
public class ItemFallback implements FallbackProvider {
    /**
     * 返回service id
     * 争对某个服务，应用当前降级类
     * 如果返回“*”或者null，则对所有服务都执行降级类
     * @return
     */
    @Override
    public String getRoute() {
        return "item-service";
    }

    /**
     * 封装降级响应的对象
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {

                HttpHeaders h = new HttpHeaders();
                h.setContentType(MediaType.APPLICATION_JSON);
                return h;
            }

            @Override
            public InputStream getBody() throws IOException {
                String json = JsonResult.err("调用商品服务失败").toString();
                ByteArrayInputStream body = new ByteArrayInputStream(json.getBytes("utf-8"));
                return body;
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return  HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }
}
