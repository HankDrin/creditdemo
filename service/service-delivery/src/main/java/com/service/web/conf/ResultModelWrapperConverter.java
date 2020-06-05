package com.service.web.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.channel.base.ResultModel;
import com.service.common.enums.code.SysCodeMsgEnum;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 接口接入下划线转驼峰
 * 接口返回驼峰转下划线
 *
 */
public class ResultModelWrapperConverter extends AbstractHttpMessageConverter<Object> {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    public ResultModelWrapperConverter() {
        super(new MediaType("application", "json", StandardCharsets.UTF_8),
                new MediaType("application", "*+json", StandardCharsets.UTF_8));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        String bodyText = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        return jacksonObjectMapper.readValue(bodyText, clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        ResultModel result;
        if (o instanceof ResultModel) {
            result = (ResultModel)o;
        } else {
            result = new ResultModel();
            result.setBizCode(SysCodeMsgEnum.SUCCESS.getCode());
            result.setBizMsg(SysCodeMsgEnum.SUCCESS.getMsg());
            result.setData(o);
        }

        OutputStream out = outputMessage.getBody();
        out.write(jacksonObjectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
