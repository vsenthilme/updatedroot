package com.mnrclara.api.management.model.mattergeneral;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbldocketwiseid")
public class DocketWiseId {

    @Id
    @Column(name = "DOC_WISE_ID")
    private String docketWiseId;                                      // DocketWiseId

    @Column(name = "TEXT")
    private String description;                                     // Description for DockerWise Id

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

}
