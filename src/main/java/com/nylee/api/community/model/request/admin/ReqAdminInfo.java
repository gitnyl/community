package com.nylee.api.community.model.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReqAdminInfo {

    @ApiModelProperty(value = "IDX")
    private Integer idx;

    @ApiModelProperty(value = "아이디")
    private String  id;

    @ApiModelProperty(value = "패스워드 ( 암호화된 )")
    private String  pwd;

    @ApiModelProperty(value = "관리자 권한 ( 1 슈퍼관리자, 2 일반관리자 )")
    private Integer level;

    @ApiModelProperty(value = "관리자 명")
    private String  userName;

    @ApiModelProperty(value = "관리자 휴대폰")
    private String  userPhone;

    @ApiModelProperty(value = "관리자 이메일")
    private String  userEmail;
}
