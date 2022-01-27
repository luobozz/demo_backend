package com.shtf.edu.bean.api.system.account.vo;

import lombok.Data;

import java.util.List;

/**
 * @author chenlingyu
 */
@Data
public class JWTSessionAccount {
    private String uuid;
    private String account;
    private String status;
    private List<String> roleUuIds;
    private String token;
}
