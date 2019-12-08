package org.erp.model.common;

import lombok.Getter;
import lombok.Setter;

/*
 * 결과가 단일건인 API를 담는 모델
 * Generic Interface <T> 로 지정하여 모든 형태의 값을 넣을 수 있도록 구현
 * */
@Getter
@Setter
public class SingleResult<T> extends CommonResult {

    private T data;
}