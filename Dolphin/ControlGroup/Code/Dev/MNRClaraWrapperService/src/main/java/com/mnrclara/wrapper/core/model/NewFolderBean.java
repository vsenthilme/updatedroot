package com.mnrclara.wrapper.core.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class NewFolderBean implements Serializable{
	/*
	 * {
		  "name": "CG",
		  "folder": { },
		  "@microsoft.graph.conflictBehavior": "rename"
		}
	 */
	private String name;
	private Folder folder;
}
