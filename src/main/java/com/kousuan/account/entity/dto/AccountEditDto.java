package com.kousuan.account.entity.dto;

import lombok.Data;

@Data
public class AccountEditDto {
    private Integer id;
    private String name;
    private String email;
    private String classroom;
    private String phoneNumber;
}
