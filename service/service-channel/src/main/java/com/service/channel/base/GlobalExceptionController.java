/** */
package com.service.channel.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.enums.code.BizCodeMsgEnum;
import com.service.common.enums.code.SysCodeMsgEnum;
import com.service.common.exception.ApplicationException;
import com.service.common.tool.ConstraintViolationTool;
import com.service.common.tool.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局异常处理类
 *
 */
@RestControllerAdvice
@RequestMapping("${server.error.path:${error.path:/error}}")
@Slf4j
public class GlobalExceptionController extends AbstractErrorController {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath = "/error";

    /**
     * 构造函数
     *
     * @param errorAttributes
     */
    public GlobalExceptionController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 处理全局的/error请求
     *
     * @param request
     * @return
     */
    @RequestMapping
    public ResultModel<Object> error(HttpServletRequest request, Exception e) {
        // 参考org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter第79行
        Object exception = request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            return handleException(request, (Exception) exception);
        }
        Object errCode = request.getAttribute("javax.servlet.error.status_code");
        if ((errCode != null) && (errCode.toString().length() > 0)) {
            Object requestUri = request.getAttribute("javax.servlet.error.request_uri");
            if (requestUri != null) {
                return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(),
                        SysCodeMsgEnum.SYS_ERROR.getMsg());
            }
        }
        Object msg = request.getAttribute("javax.servlet.error.message");
        return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(), msg.toString());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleApplicationException(HttpServletRequest request, Exception e) {
        log.warn("请求服务应用错误，请求地址：{}，错误信息：{}。", request.getRequestURI(), e.toString());
        ApplicationException applicationException = (ApplicationException) e;
        return ResultModel.buildErrorResult(applicationException.getErrorCode(), applicationException.getErrorMsg());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleBindException(HttpServletRequest request, BindException e) {
        log.warn("参数绑定错误，请求地址：{}，错误信息：{}。", request.getRequestURI(), e.toString(), e);
        return ResultModel.buildErrorResult(SysCodeMsgEnum.PARAMETERS_BIND_ERROR.getCode(),
                getErrorMsgFromFieldError(e.getFieldErrors()));
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleConstraintViolationException(HttpServletRequest request,
            ConstraintViolationException e) {
        log.warn("参数验证错误，请求地址：{}，错误信息：{}", request.getRequestURI(), e.toString(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, String> errorMsgMap = new HashMap<>();
        constraintViolations.forEach(constraintViolation -> {
            String[] errorMsg = ConstraintViolationTool.getErrorMsgFromConstraintViolations(constraintViolation);
            errorMsgMap.put(errorMsg[0], errorMsg[1]);
        });
        return ResultModel.buildErrorResult(SysCodeMsgEnum.PARAMETERS_VALID_ERROR.getCode(), errorMsgMap.toString());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleMethodNotValidException(HttpServletRequest request,
            MethodArgumentNotValidException e) {
        log.warn("参数验证错误，请求地址：{}，错误信息：{} ", request.getRequestURI(), e.toString(), e);
        BindingResult bindingResult = e.getBindingResult();
        return ResultModel.buildErrorResult(SysCodeMsgEnum.PARAMETERS_BIND_ERROR.getCode(),
                getErrorMsgFromFieldError(bindingResult.getFieldErrors()));
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleRequestParameterException(HttpServletRequest request,
            MissingServletRequestParameterException e) {
        log.warn("请求参数不存在，请求地址：{}，错误信息：{} ", request.getRequestURI(), e.toString());
        return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(), e.getMessage());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleRequestParameterMismatchException(HttpServletRequest request,
            MethodArgumentTypeMismatchException e) {
        log.warn("参数转换错误，请求地址：{}，错误信息：{} ", request.getRequestURI(), e.toString(), e);
        String errMsg = StringTool.convertToSnakeCase(e.getName()) + " required type should be " + e.getRequiredType();
        return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(), errMsg);
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel<Object> handleException(HttpServletRequest request, Exception e) {
        log.warn("未知异常错误，请求地址：{}，错误信息：{} ", request.getRequestURI(), e.toString(), e);
        if (e instanceof NullPointerException) {
            log.warn(String.format("npe exception. request uri: %s", request.getRequestURI()));
            return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(), SysCodeMsgEnum.SYS_ERROR.getMsg());
        } else if (e instanceof NumberFormatException) {
            return ResultModel.buildErrorResult(SysCodeMsgEnum.NUMBER_FORMAT_ERROR.getCode(),
                    SysCodeMsgEnum.NUMBER_FORMAT_ERROR.getMsg());
        } else if (e instanceof IllegalArgumentException) {
            // 参数异常
            return ResultModel.buildErrorResult(BizCodeMsgEnum.PARAMETER_ERROR.getCode(),
                    e.getMessage());
        } else if (e instanceof ClassCastException) {
            // 类型转换异常
            return ResultModel.buildErrorResult(SysCodeMsgEnum.CLASS_CAST_ERROR.getCode(),
                    SysCodeMsgEnum.CLASS_CAST_ERROR.getMsg());
        } else if (e instanceof AccessDeniedException) {
            return ResultModel.buildErrorResult(BizCodeMsgEnum.USER_NOT_LOGIN.getCode(), e.getMessage());
        }
        log.warn(String.format("unchecked exception. request uri: %s", request.getRequestURI()), e);
        return ResultModel.buildErrorResult(SysCodeMsgEnum.SYS_ERROR.getCode(), SysCodeMsgEnum.SYS_ERROR.getMsg());
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    /**
     * 根据字段错误返回具体错误信息
     *
     * @param fieldErrors 字段错误列表信息
     * @return
     */
    private String getErrorMsgFromFieldError(List<FieldError> fieldErrors) {
        Map<String, String> errorMsgMap = new HashMap<>();
        fieldErrors.forEach(objectError -> errorMsgMap.put(StringTool.convertToSnakeCase(objectError.getField()),
                objectError.getDefaultMessage()));
        return errorMsgMap.toString();
    }

}
