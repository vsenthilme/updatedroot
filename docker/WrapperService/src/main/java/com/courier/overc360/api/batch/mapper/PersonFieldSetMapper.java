package com.courier.overc360.api.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.courier.overc360.api.batch.dto.Person;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {

	@Override
	public Person mapFieldSet(FieldSet fieldSet) {
		return new Person(fieldSet.readLong("id"),
				fieldSet.readString("firstName"),
				fieldSet.readString("lastName"));
	}
}