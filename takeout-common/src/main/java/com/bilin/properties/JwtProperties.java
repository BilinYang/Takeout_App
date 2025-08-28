package com.bilin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bilin.jwt")
@Data
public class JwtProperties {

    // Employee JWT token generation related configuration in the management system
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    // User JWT token generation related configuration in the management system
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
