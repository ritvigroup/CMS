package com.ritvi.cms.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 31-01-2018.
 */

public class UserProfilePOJO {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_profile_id")
    private String userProfileId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_full_name")
    private String userFullName;
    @SerializedName("user_image")
    private String userImage;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_phonecountry")
    private String userPhonecountry;
    @SerializedName("user_mobile")
    private String userMobile;
    @SerializedName("user_createdon")
    private String userCreatedon;
    @SerializedName("user_address")
    private String userAddress;
    @SerializedName("user_city")
    private String userCity;
    @SerializedName("user_state")
    private String userState;
    @SerializedName("user_device_token")
    private String userDeviceToken;
    @SerializedName("user_date_of_birth")
    private String userDateOfBirth;
    @SerializedName("user_gender")
    private String userGender;
    @SerializedName("user_login_status")
    private String userLoginStatus;

    public UserProfilePOJO(String userId, String userProfileId, String userName, String userFullName, String userImage, String userEmail, String userPhonecountry, String userMobile, String userCreatedon, String userAddress, String userCity, String userState, String userDeviceToken, String userDateOfBirth, String userGender, String userLoginStatus) {
        this.userId = userId;
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.userFullName = userFullName;
        this.userImage = userImage;
        this.userEmail = userEmail;
        this.userPhonecountry = userPhonecountry;
        this.userMobile = userMobile;
        this.userCreatedon = userCreatedon;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userState = userState;
        this.userDeviceToken = userDeviceToken;
        this.userDateOfBirth = userDateOfBirth;
        this.userGender = userGender;
        this.userLoginStatus = userLoginStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhonecountry() {
        return userPhonecountry;
    }

    public void setUserPhonecountry(String userPhonecountry) {
        this.userPhonecountry = userPhonecountry;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserCreatedon() {
        return userCreatedon;
    }

    public void setUserCreatedon(String userCreatedon) {
        this.userCreatedon = userCreatedon;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserDeviceToken() {
        return userDeviceToken;
    }

    public void setUserDeviceToken(String userDeviceToken) {
        this.userDeviceToken = userDeviceToken;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(String userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }
}
