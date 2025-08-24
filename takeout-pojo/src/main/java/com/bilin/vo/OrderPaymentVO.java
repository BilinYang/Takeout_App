package com.bilin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    private String nonceStr;   // Random string
    private String paySign;    // Payment signature
    private String timeStamp;  // Timestamp
    private String signType;   // Signature algorithm
    private String packageStr; // prepay_id value returned by the Unified Order API

}
