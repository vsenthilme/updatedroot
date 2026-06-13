package com.courier.overc360.api.batch.mapper;

import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.courier.overc360.api.util.DateUtils;
import com.courier.overc360.api.util.User1;

public class UserFieldSetMapper implements FieldSetMapper<User1> {

	@Override
	public User1 mapFieldSet(FieldSet fieldSet) {
		String sdate = fieldSet.readString("dob");
		Date date = DateUtils.convertStringToDate2(sdate);
		
		return new User1(
				fieldSet.readString("id"),
				fieldSet.readString("name"),
				date
			);
	}
}