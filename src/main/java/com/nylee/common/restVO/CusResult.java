package com.nylee.common.restVO;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CusResult {

    @JsonIgnore
    HttpStatus httpStatus;
    @JsonIgnore
    ResultCode resultCode;
    Map<String, Object> result = null;

    public CusResult(ResultCode result_code) {
        this.resultCode = result_code;
    }

    public CusResult(HttpStatus httpStatus, ResultCode resultCode) {
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public static CusResult getDefResult() {
        return new CusResult(HttpStatus.OK, ResultCode.SUCCESS);
    }

    public static CusResult getResult(ResultCode result_code) {
        return new CusResult(result_code);
    }

    public void addObject(String key, Object value){
        if(result == null) result = new HashMap<String, Object>();
        result.put(key, value);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @JsonAnyGetter // json tree를 한단계 상위로 끌어 올리기 위해 사용. 원래는 JsonUnWrapper 쓰면 될 것 같았지만..Map는 영향을 미치지 않는 다고 함.
    public Map<String, Object> getResult() {
        return result;
    }

    public ResponseEntity<?> getResponse(){
        if (result == null){
            return new ResponseEntity<Object>(httpStatus);
        }else{
            return new ResponseEntity<Object>(result, httpStatus);
        }
    }
}
