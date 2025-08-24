package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    private Long empId;
    private String oldPassword;
    private String newPassword;

}
