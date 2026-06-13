package com.courier.overc360.api.idmaster.replica.model.storagetypemaster;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReplicaStorageTypeMasterCompositeKey  implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `STORAGE_TYPE_MASTER`
     */

    private String companyId;
    private String languageId;
    private String storageTypeId;
}
