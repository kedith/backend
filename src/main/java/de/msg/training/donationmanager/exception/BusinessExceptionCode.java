package de.msg.training.donationmanager.exception;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BusinessExceptionCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "User was not found!"),
    INVALID_PASSWORD("INVALID_PASSWORD","Invalid password"),
    INVALID_CREDENTIALS("INVALID CREDENTIALS","Invalid username or password"),
    INVALID_USER("INVALID_USER", "The user has invalid fields!"),
    UNAUTHORIZED_USER("UNAUTHORIZED_USER", "The user is unauthorized to perform this operation!"),
    MISSING_COOKIE("MISSING_COOKIE", "Oh no! The cookie is missing. Maybe the cookie monster ate it :("),
    NOT_VALID_REFRESHTOKEN("NOT_VALID_REFRESHTOKEN", "Refresh token is not valid"),
    EXPIRED_REFRESHTOKEN("EXPIRED_REFRESHTOKEN", "Refresh token is expired"),
    DONOR_NOT_FOUND("DONOR_NOT_FOUND", "The donor was not found!"),
    INVALID_DONOR("INVALID_DONOR", "The donor has invalid fields!"),
    INVALID_CAMPAIGN("INVALID_CAMPAIGN","The campaign has invalid fields!"),
    CAMPAIGN_NOT_FOUND("CAMPAIGN_NOT_FOUND", "The requested campaign was not found!"),
    DONATION_NOT_FOUND("DONATION_NOT_FOUND", "The donation was not found!"),
    INVALID_DONATION("INVALID_DONATION", "The donation has invalid fields!"),
    CONCURRENCY_ERROR("CONCURRENCY_ERROR", "Another operation on this entity has been made, please try again!"),
    USER_NOT_AUTHORIZED("USER_NOT_AUTHORIZED", "User is not authorized"),
    PAYED_CAMPAIGN("PAYED_CAMPAIGN", "This campaign cannot be deleted. It has payed donations!"),
    USER_CANNOT_APPROVE_DONATION("USER_CANNOT_APPROVE_DONATION", "Current user cannot approve this donation!"),
    USER_DEACTIVATED("USER_DEACTIVATED","User has been deavtivated"),
    NOTIFICATION_INVALID("NOTIFICATION_INVALID","Invalid notificaiton"),
    NOTIFICATION_ERROR("NOTIFICATION_ERROR","The notification could not be send"),
    DEACTIVATED_USER("DEACTIVATED_USER","User is deactivated");

    private String errorId;

    private String message;
}
