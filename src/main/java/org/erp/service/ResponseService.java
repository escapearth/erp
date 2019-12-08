package org.erp.service;

import org.erp.model.common.CommonResult;
import org.erp.model.common.ListResult;
import org.erp.model.common.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

// 해당 Class가 Service임을 명시해주기 위해 @Service를 걸어준다.
@Service
public class ResponseService {
    public enum CommonResponse {
        SUCCESS(0, "성공하였습니다."),
        FAILED(-1, "실패하였습니다.");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
    // 단일 결과를 처리해주는 method
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    // 다중 결과를 처리해주는 method
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // 성공 결과만 처리해주는 method
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    // 실패 결과만 처리해주는 method
    public CommonResult getFailedResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    // 결과 모델에 API 요청 성공 data를 setting 해주는 method
    public void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}