package com.nylee.api.community.model.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RespAdminDetailData {

    @ApiModelProperty(value = "IDX")
    private Integer idx;

    @ApiModelProperty(value = "이름")
    private String  userName;

    @ApiModelProperty(value = "휴대폰")
    private String  userPhone;

    @ApiModelProperty(value = "이메일")
    private String userEmail;

    @ApiModelProperty(value = "아이디")
    private String userId;

    @ApiModelProperty(value = "관리자 레벨[0(super) ,1(관리자)]")
    private Integer level;

    @ApiModelProperty(value = "등록일")
    private String  rgdt;

    @ApiModelProperty(value = "최종 수정일")
    private String updt;

    @ApiModelProperty(value = "최종 수정자")
    private String upid;

    @ApiModelProperty(value = "등록자")
    private String  rgid;
}
