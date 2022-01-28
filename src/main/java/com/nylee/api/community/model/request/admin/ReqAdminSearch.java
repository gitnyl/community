package com.nylee.api.community.model.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReqAdminSearch {

    @ApiModelProperty(value = "검색조건 ( 0 or null 전체, 1 이름, 2 휴대폰, 3 이메일, 4 아이디 )")
    private Integer srchType;

    @ApiModelProperty(value = "검색 키워드")
    private String  srchKeyword;

    @ApiModelProperty(value = "페이지 번호")
    private Integer srchPage;

    @ApiModelProperty(value = "페이지 별 데이터 수")
    private Integer srchPageCnt;
}
