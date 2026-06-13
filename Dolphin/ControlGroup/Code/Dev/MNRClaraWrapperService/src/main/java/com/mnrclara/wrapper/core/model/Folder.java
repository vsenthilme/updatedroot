package com.mnrclara.wrapper.core.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Folder implements Serializable {

}
