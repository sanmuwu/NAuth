/**
 * 
 */
package com.qf.qauth.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.qf.qauth.modules.sys.security.exception.RestFulException;

/**
 * rest请求异常处理器.
 * @author MengpingZeng
 *
 */
@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

	@Autowired
    private DefaultErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;
    
    @RequestMapping(value = "404", produces = "text/html")
    public ModelAndView notFoundHandler(HttpServletRequest req, Exception e) throws Exception {
    	/*req.getSession()..setStatus(getStatus(req).value());*/
    	ModelAndView model = new ModelAndView();
/*    	model.addObject("exception", e);
    	model.addObject("url", req.getRequestURL());*/
        model.setViewName("error/404");
    	/*Map<String, Object> model = getErrorAttributes(req,
    			isIncludeStackTrace(req, MediaType.TEXT_HTML));*/
    	return model;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/500");
        return mav;
    }
    
    @ExceptionHandler(value = RestFulException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> jsonErrorHandler(HttpServletRequest req, RestFulException e) throws Exception {
    	HttpServletRequest httpRequest = WebUtils.getHttpRequest(SecurityUtils.getSubject());
		Map<String, Object> errorAttributes = getErrorAttributes(httpRequest,
				isIncludeStackTrace(httpRequest, MediaType.TEXT_HTML));
		 HttpStatus status = getStatus(httpRequest);
	      return new ResponseEntity<Map<String, Object>>(errorAttributes, status);
    }

	// 处理500内部错误
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = WebUtils.getHttpRequest(SecurityUtils.getSubject());
		Map<String, Object> errorAttributes = getErrorAttributes(httpRequest,
				isIncludeStackTrace(httpRequest, MediaType.TEXT_HTML));
	      return new ResponseEntity<Object>(errorAttributes, status);
	}

	// 处理请求方法不支持异常
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = WebUtils.getHttpRequest(SecurityUtils.getSubject());
		Map<String, Object> errorAttributes = getErrorAttributes(httpRequest,
				isIncludeStackTrace(httpRequest, MediaType.TEXT_HTML));
	      return new ResponseEntity<Object>(errorAttributes, status);
	}

	// 处理请求参数不足异常
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = WebUtils.getHttpRequest(SecurityUtils.getSubject());
		Map<String, Object> errorAttributes = getErrorAttributes(httpRequest,
				isIncludeStackTrace(httpRequest, MediaType.TEXT_HTML));
	      return new ResponseEntity<Object>(errorAttributes, status);
	}

	// 处理参数类型不匹配错误
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = WebUtils.getHttpRequest(SecurityUtils.getSubject());
		Map<String, Object> errorAttributes = getErrorAttributes(httpRequest,
				isIncludeStackTrace(httpRequest, MediaType.TEXT_HTML));
	      return new ResponseEntity<Object>(errorAttributes, status);
	}

    /**
     * Determine if the stacktrace attribute should be included.
     * @param request the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }


    /**
     * 获取错误的信息
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes,
                includeStackTrace);
    }

    /**
     * 是否包含trace
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    /**
     * 获取错误编码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
