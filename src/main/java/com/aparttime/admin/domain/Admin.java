package com.aparttime.admin.domain;

import com.aparttime.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role;

    private Admin(
        String username,
        String password
    ) {
        this.username = username;
        this.password = password;
        this.role = "ADMIN"; // 나중에 상수로 바꿔주기
    }

    public static Admin of(
        String username,
        String password
    ) {
        return new Admin(
            username,
            password
        );
    }
}
