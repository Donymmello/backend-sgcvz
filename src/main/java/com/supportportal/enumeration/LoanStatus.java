package com.supportportal.enumeration;


import static com.supportportal.constant.Authority.SUPER_ADMIN_AUTHORITIES;
public enum LoanStatus {
    PENDENTE(SUPER_ADMIN_AUTHORITIES),
    APOVADO(SUPER_ADMIN_AUTHORITIES),
    REJEITADO(SUPER_ADMIN_AUTHORITIES);

    private final String[] authorities;

    LoanStatus(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
