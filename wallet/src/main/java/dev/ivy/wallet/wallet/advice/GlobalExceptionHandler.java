package dev.ivy.wallet.wallet.advice;

import dev.ivy.wallet.wallet.vo.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Global Exception Handler</h1>
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ErrorInfo<String> errorHandler(HttpServletRequest request, Exception ex) throws Exception{
        ErrorInfo<String> info = new ErrorInfo<String>();

        info.setCode(ErrorInfo.ERROR);
        info.setMessage(ex.getMessage());
        info.setData("Do Not Have Return Data");
        info.setUrl(request.getRequestURL().toString());
        return info;
    }
}
