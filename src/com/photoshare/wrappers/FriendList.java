package com.photoshare.wrappers;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FriendList {

	String[] friendEmailIds = {};

	public String[] getFriendEmailIds() {
		return friendEmailIds;
	}

	public void setFriendEmailIds(String[] friendEmailIds) {
		this.friendEmailIds = friendEmailIds;
	}

	
	
	
	
}
