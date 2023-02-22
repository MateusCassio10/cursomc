package com.nelioalves.cursomc.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO  {

    @NotEmpty(message = "preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private EmailDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
