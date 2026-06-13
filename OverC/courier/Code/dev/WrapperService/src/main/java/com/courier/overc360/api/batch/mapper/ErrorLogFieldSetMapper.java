package com.courier.overc360.api.batch.mapper;

import com.courier.overc360.api.model.dto.ErrorLogdto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class ErrorLogFieldSetMapper implements FieldSetMapper<ErrorLogdto> {

    @Override
    public ErrorLogdto mapFieldSet(FieldSet fieldSet) {
        return new ErrorLogdto(
                fieldSet.readString("logId"),
                fieldSet.readDate("logDate"),
                fieldSet.readString("errorMessage"),
                fieldSet.readString("languageId"),
                fieldSet.readString("companyId"),
                fieldSet.readString("refDocNumber"),
                fieldSet.readString("method"),
                fieldSet.readString("referenceField1"),
                fieldSet.readString("referenceField2"),
                fieldSet.readString("referenceField3"),
                fieldSet.readString("referenceField4"),
                fieldSet.readString("referenceField5"),
                fieldSet.readString("referenceField6"),
                fieldSet.readString("referenceField7"),
                fieldSet.readString("referenceField8"),
                fieldSet.readString("referenceField9"),
                fieldSet.readString("referenceField10"),
                fieldSet.readString("createdBy")
        );
    }

}