package com.bilin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    // Unique identification number given to each WeChat user
    private String openid;
    private String name;
    private String phone;
    // 0.Female 1.Male
    private String sex;
    // ID card number
    private String idNumber;
    private String avatar;
    private LocalDateTime createTime;
}
