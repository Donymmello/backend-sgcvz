package com.supportportal.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("C")
public class Credit extends Operation {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Credit() {
        super();
    }

    public Credit(Date operationDate, double amount, String typeOperation, Account account) {
        super(operationDate, amount, typeOperation, account);
    }

}

