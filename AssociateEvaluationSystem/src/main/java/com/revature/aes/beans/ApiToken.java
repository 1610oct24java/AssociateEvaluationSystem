package com.revature.aes.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mpski on 2/17/17.
 */

@Entity
@Table(name = "AES_API_TOKEN")
public class ApiToken implements Serializable {

    private static final long serialVersionUID = 1609968850692881769L;

    @Id
    @Column(name = "API_ID")
    private Integer apiId;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "VALID")
    private Integer valid;

//    @Column(name = "DATE_ISSUED")
//    @Temporal(TemporalType.DATE)
    @Transient
    private Date dateIssued;


    public ApiToken() { super(); }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    @Override
    public String toString() {
        return "ApiToken{" +
                "apiId=" + apiId +
                ", token='" + token + '\'' +
                ", valid=" + valid +
                '}';
    }
}
