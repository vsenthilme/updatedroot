package com.courier.overc360.api.idmaster.replica.model.menu;

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
 * `LANG_ID`, `C_ID`, `MENU_ID`, `SUB_MENU_ID`, `AUT_OBJ_ID`
 */
@Table(name = "tblmenu",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_menu",
                        columnNames = {"LANG_ID", "C_ID", "MENU_ID", "SUB_MENU_ID", "AUT_OBJ_ID"})
        }
)
@IdClass(ReplicaMenuCompositeKey.class)
public class ReplicaMenu {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "MENU_ID")
    private Long menuId;

    @Id
    @Column(name = "SUB_MENU_ID")
    private Long subMenuId;

    @Id
    @Column(name = "AUT_OBJ_ID")
    private Long authorizationObjectId;

    @Column(name = "AUT_OBJ_VALUE", columnDefinition = "nvarchar(100)")
    private String authorizationObjectValue;

    @Column(name = "MENU_TEXT", columnDefinition = "nvarchar(100)")
    private String menuName;

    @Column(name = "SUB_MENU_TEXT", columnDefinition = "nvarchar(100)")
    private String subMenuName;

    @Column(name = "AUT_OBJ", columnDefinition = "nvarchar(100)")
    private String authorizationObject;

    @Column(name = "LANG_ID_DESC", columnDefinition = "nvarchar(500)")
    private String languageIdAndDescription;

    @Column(name = "COMP_ID_DESC", columnDefinition = "nvarchar(500)")
    private String companyIdAndDescription;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator;

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

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

    // For Role Access
    @Column(name = "CREATE_UPDATE")
    private Boolean createUpdate;

    @Column(name = "DELETE_MODULE")
    private Boolean delete;

    @Column(name = "VIEW_MODULE")
    private Boolean view;

    // For ModuleId
    @Column(name = "ADD_MODULE")
    private Boolean addModule;

}
