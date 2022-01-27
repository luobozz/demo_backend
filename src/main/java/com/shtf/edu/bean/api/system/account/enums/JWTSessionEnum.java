package com.shtf.edu.bean.api.system.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenlingyu
 */
@AllArgsConstructor
@Getter
public enum JWTSessionEnum {
    NORMAL("normal","正常"),
    BANNED("banned","禁止");
    private String content;
    private String description;
}
