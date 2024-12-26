package com.pr.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_info")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private Long id;
    @Column(name= "email", nullable = false)
    private String email;
    @Column(name= "name", nullable = false)
    private String name;
    @Column(name= "provider", nullable = false)
    private String provider;
    @Column(name= "nickName")
    private String nickName;
    @Column(name= "picture")
    private String picture;
    @Column(name= "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name= "createDate", nullable = false)
    @CreatedDate
    private LocalDateTime createDate;
    @Column(name= "updateDate")
    @LastModifiedDate
    private LocalDateTime updateDate;

    public MemberInfo update(String nickName, LocalDateTime updateDate) {
        this.nickName = nickName;
        this.updateDate = LocalDateTime.now();;
        return this;
    }


    public String getRoleKey() {
        return this.role.getKey();
    }
}