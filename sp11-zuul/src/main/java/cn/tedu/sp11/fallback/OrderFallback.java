package cn.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
@Component
public class OrderFallback implements FallbackProvider {
    @Override
    public String getRoute() {
        return "order-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
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

            @Override
            public InputStream getBody() throws IOException {
                String str = JsonResult.ok("调用订单服务失败").toString();
                ByteArrayInputStream body = new ByteArrayInputStream(str.getBytes("utf-8"));
                return body;
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders http = new HttpHeaders();
                http.setContentType(MediaType.APPLICATION_JSON);
                return http;
            }
        };
    }
}
