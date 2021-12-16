package com.supportportal.enumeration;


import static com.supportportal.constant.Authority.SUPER_ADMIN_AUTHORITIES;
public enum LoanStatus {
    PENDING(SUPER_ADMIN_AUTHORITIES),
    APPROVED(SUPER_ADMIN_AUTHORITIES),
    REJECTED(SUPER_ADMIN_AUTHORITIES);

    private final String[] authorities;

    LoanStatus(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
