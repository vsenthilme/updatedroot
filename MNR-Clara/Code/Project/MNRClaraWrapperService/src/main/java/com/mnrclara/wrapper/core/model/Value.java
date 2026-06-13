package com.mnrclara.wrapper.core.model;

import lombok.Data;

@Data
public class Value {

	/*
	 *  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#Collection(microsoft.graph.driveItem)",
    	"value": [
        {
            "createdDateTime": "2024-04-15T10:56:45Z",
            "id": "01WPMNIO2BXKWUWPGF6JAIYD2NQOFJAFVV",
            "lastModifiedDateTime": "2024-04-15T10:56:45Z",
            "name": "3566",
        }
        ]
	 */
	private String id;
	private String name;
}
