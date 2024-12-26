package com.pr.member.dto;

import com.pr.member.domain.MemberInfo;
import com.pr.member.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private Long id;
    private String email;
    private String name;
    private String provider;
    private String nickName;
    private String picture;
    private Role role;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    /*
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }
*/
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println("google....asd.ads.ads.ads.das.asd..sda");
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .provider("Google")
                .role(Role.USER)
                .createDate(LocalDateTime.now())
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    /*
        private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return OAuthAttributes.builder()
                    .name((String) response.get("name"))
                    .email((String) response.get("email"))
                    .provider("Naver")
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }

        private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> account = (Map<String, Object>) response.get("profile");

            return OAuthAttributes.builder()
                    .name((String) account.get("nickname"))
                    .email((String) response.get("email"))
                    .picture((String) account.get("profile_image_url"))
                    .provider("Kakao")
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }
    */
    public MemberInfo toEntity() {
        return MemberInfo.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .provider(provider)
                .role(Role.USER)
                .createDate(LocalDateTime.now())
                .build();
    }
}
