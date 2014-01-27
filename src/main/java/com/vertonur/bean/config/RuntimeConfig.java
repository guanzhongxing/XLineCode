package com.vertonur.bean.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.pojo.config.Config;

@Entity(name = "INFO_FRM_RT_CFG")
public class RuntimeConfig implements Config {

	private int id;
	private String uploadRootFolder;
	private String avatarRootFolder;
	private AttachmentType uploadFileSystem;

	@Id
	@GeneratedValue
	@Column(name = "RT_CFG_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "RT_CFG_FILE_UPLOAD_FOLDER")
	public String getUploadRootFolder() {
		return uploadRootFolder;
	}

	public void setUploadRootFolder(String uploadRootFolder) {
		this.uploadRootFolder = uploadRootFolder;
	}

	@Column(name = "RT_CFG_AVATAR_UPLOAD_FOLDER")
	public String getAvatarRootFolder() {
		return avatarRootFolder;
	}

	public void setAvatarRootFolder(String avatarRootFolder) {
		this.avatarRootFolder = avatarRootFolder;
	}

	public AttachmentType getUploadFileSystem() {
		return uploadFileSystem;
	}

	public void setUploadFileSystem(AttachmentType uploadFileSystem) {
		this.uploadFileSystem = uploadFileSystem;
	}
}
