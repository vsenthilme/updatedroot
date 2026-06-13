package com.courier.overc360.api.idmaster.replica.model.roleaccess;

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
 * `LANG_ID`, `C_ID`, `USR_ROLE_ID`, `MENU_ID`, `SUB_MENU_ID`
 */
@Table(name = "tblroleaccess",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_roleaccess",
                        columnNames = {"LANG_ID", "C_ID", "USR_ROLE_ID", "MENU_ID", "SUB_MENU_ID"}
                )
        }
)
@IdClass(ReplicaRoleAccessCompositeKey.class)
public class ReplicaRoleAccess {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USR_ROLE_ID")
    private Long userRoleId = 0L;

    @Id
    @Column(name = "MENU_ID")
    private Long menuId;

    @Id
    @Column(name = "SUB_MENU_ID")
    private Long subMenuId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "MOD_ID", columnDefinition = "nvarchar(50)")
    private String moduleId;

    @Column(name = "AUT_OBJ_ID")
    private Long authorizationObjectId;

    @Column(name = "AUT_OBJ_VALUE", columnDefinition = "nvarchar(100)")
    private String authorizationObjectValue;

    @Column(name = "USR_ROLE_NM", columnDefinition = "nvarchar(100)")
    private String userRoleName;

    @Column(name = "MENU_NAME", columnDefinition = "nvarchar(100)")
    private String menuName;

    @Column(name = "SUB_MENU_NAME", columnDefinition = "nvarchar(100)")
    private String subMenuName;

    @Column(name = "USR_ROLE_TEXT", columnDefinition = "nvarchar(100)")
    private String description;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "LANG_ID_DESC", columnDefinition = "nvarchar(200)")
    private String languageIdAndDescription;

    @Column(name = "COMP_ID_DESC", columnDefinition = "nvarchar(200)")
    private String companyIdAndDescription;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
    private String referenceField10;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator;

    @Column(name = "CREATE_ACCESS")
    private Boolean createUpdate;

    @Column(name = "EDIT_ACCESS")
    private Boolean edit;

    @Column(name = "VIEW_ACCESS")
    private Boolean view;

    @Column(name = "DELETE_ACCESS")
    private Boolean delete;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

}
