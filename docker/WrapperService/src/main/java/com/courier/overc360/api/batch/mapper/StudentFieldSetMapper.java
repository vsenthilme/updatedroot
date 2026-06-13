package com.courier.overc360.api.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.courier.overc360.api.batch.dto.Student;

public class StudentFieldSetMapper implements FieldSetMapper<Student> {

	@Override
	public Student mapFieldSet(FieldSet fieldSet) {
		return new Student(fieldSet.readLong("id"),
				fieldSet.readString("firstName"),
				fieldSet.readString("lastName"));
	}
}