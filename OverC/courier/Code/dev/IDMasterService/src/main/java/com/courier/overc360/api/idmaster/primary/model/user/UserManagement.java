package com.courier.overc360.api.idmaster.primary.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

/*
 *`LANG_ID` , `C_ID` , `USER_ID`
 */
@Table(
        name = "tblusermanagement",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_usermanagement",
                        columnNames = {"LANG_ID", "C_ID", "USER_ID"}
                )
        }
)
@IdClass(UserManagementCompositeKey.class)
public class UserManagement {

    public enum Role {USER, ADMIN, USER_MANAGER}

    @Id
    @Column(name = "USER_ID", columnDefinition = "nvarchar(50)")
    private String userId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "USER_ROLE_ID")
    private Long userRoleId;

    @Column(name = "LANG_ID_DESC", columnDefinition = "nvarchar(200)")
    private String languageIdAndDescription;

    @Column(name = "COMP_ID_DESC", columnDefinition = "nvarchar(200)")
    private String companyIdAndDescription;

    @Column(name = "USR_ROLE_ID_DESC", columnDefinition = "nvarchar(200)")
    private String userRoleIdAndDescription;

    @Column(name = "USR_TYP_ID_DESC", columnDefinition = "nvarchar(200)")
    private String userTypeIdAndDescription;

    @Column(name = "USR_TYP_ID")
    private Long userTypeId;

    // @JsonIgnore
    @Column(name = "PASSWORD", columnDefinition = "nvarchar(300)")
    private String password;

    @Column(name = "USER_NM", columnDefinition = "nvarchar(100)")
    private String userName;

    @Column(name = "FST_NM", columnDefinition = "nvarchar(100)")
    private String firstName;

    @Column(name = "LST_NM", columnDefinition = "nvarchar(100)")
    private String lastName;

    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "DATE_FOR_ID")
    private Long dateFormatId;

    @Column(name = "CUR_DECIMAL")
    private Long currencyDecimal;

    @Column(name = "CREATE_HHT_USR")
    private Boolean createHhtUser;

    @Column(name = "TIME_ZONE")
    private String timeZone;

    @Column(name = "IS_LOGGED_IN")
    private Boolean portalLoggedIn;

    @Column(name = "HHT_LOGGED_IN")
    private Boolean hhtLoggedIn;

    @Column(name = "RESET_PASSWORD")
    private Boolean resetPassword;

    @Column(name = "MAIL_ID", columnDefinition = "nvarchar(200)")
    private String emailId;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

}
