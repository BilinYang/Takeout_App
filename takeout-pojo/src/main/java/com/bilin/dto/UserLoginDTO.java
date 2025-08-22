package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

// Login DTO for users of the wechat program app
@Data
public class UserLoginDTO implements Serializable {

    private String code; // wechat authorization code (can only be used once)

}
