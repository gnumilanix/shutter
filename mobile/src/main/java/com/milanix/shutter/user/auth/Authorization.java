package com.milanix.shutter.user.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Authorization response
 *
 * @author milan
 */
public class Authorization {
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("issued_at")
    long issuedAt;
    @SerializedName("signature")
    String signature;
    @SerializedName("refresh_token")
    String refreshToken;
    @SerializedName("token_type")
    String tokenType;
    @Expose(serialize = false, deserialize = false)
    String username;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
