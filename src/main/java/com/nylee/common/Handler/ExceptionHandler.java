package com.nylee.common.Handler;

import com.nylee.common.exception.BaseException;
import com.nylee.common.restVO.CusResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;
import java.rmi.ServerException;

@RestControllerAdvice
public class ExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    CusResult result = CusResult.getDefResult();

    //잘못된 요청에 의한
    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public ResponseEntity<?> basicHandel(HttpServletRequest req, BaseException e){
        //logger.error("BadReqeust : ",e.getException() == null ? null :e.getException());
        result.addObject("result" , false);
        result.addObject("errMsg", e.getErrMsg());
        result.setHttpStatus(HttpStatus.OK);

        return result.getResponse();
    }

//    //checked 서버에러
//    @org.springframework.web.bind.annotation.ExceptionHandler(ServerException.class)
//    public ResponseEntity<?> serverHandel(HttpServletRequest req, ServerException e){
//        logger.error("ServerError : " , e.getException());
//        result.addObject("result",false);
//        result.addObject("errMsg", e.getErrMsg());
//        result.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        return result.getResponse();
//    }
    
    //unchecked 서버에러
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverBasicHandel(HttpServletRequest req, Exception e){
        logger.error("ExcptionError : ", e);
        result.addObject("result",false);
        result.addObject("errMsg", "서버 에러 발생");
        result.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return result.getResponse();

    }

    //Request Binding Error
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> bindHandel(MethodArgumentNotValidException e){
        String errMsg = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        logger.error("BindError : " ,e);
        result.addObject("result",false);
        result.addObject("errMsg", errMsg);
        result.setHttpStatus(HttpStatus.OK);
        return result.getResponse();
    }

    //Request Binding Error
    @org.springframework.web.bind.annotation.ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<?> bindHandel(UnexpectedTypeException e){
        logger.error("BindError  " ,e);
        logger.error("BindError  " +e.getMessage());
        result.addObject("result",false);
        result.addObject("errMsg", "패턴에 맞지 않습니다.");
        result.setHttpStatus(HttpStatus.OK);
        return result.getResponse();
    }

    //duplication
    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyHandel(HttpServletRequest req, Exception e){
        logger.error("ExcptionError : ", e);
        result.addObject("result",false);
        result.addObject("errMsg", "키 중복 에러");
        result.setHttpStatus(HttpStatus.CONFLICT);

        return result.getResponse();
    }

}
