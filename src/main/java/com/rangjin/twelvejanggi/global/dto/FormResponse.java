package com.rangjin.twelvejanggi.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormResponse<T> {

    private boolean validated;
    private T data;

}
