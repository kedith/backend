package de.msg.training.donationmanager.controller.auth;

public class RefreshTokenResponse {

    private String renewedAccesToken;

    public RefreshTokenResponse(String renewedAccesToken) {
        this.renewedAccesToken = renewedAccesToken;
    }

    public String getRenewedAccesToken() {
        return renewedAccesToken;
    }

    public void setRenewedAccesToken(String renewedAccesToken) {
        this.renewedAccesToken = renewedAccesToken;
    }
}
