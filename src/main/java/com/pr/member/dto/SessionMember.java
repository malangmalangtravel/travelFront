package com.pr.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pr.member.domain.MemberInfo;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;
    @JsonProperty("nickName")
    private String nickName;
    // 닉네임 유무 체크
    private Boolean checkNickName;

    public SessionMember(MemberInfo memberInfo){
        this.name = memberInfo.getName();
        this.email = memberInfo.getEmail();
        this.picture = memberInfo.getPicture();
        this.nickName = memberInfo.getNickName();
        this.checkNickName = false; // 기본값 false
    }

    public void setCheckNickName(Boolean checkNickName) {
        this.checkNickName = checkNickName;
    }

}