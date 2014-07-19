package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;
import com.nwm.coauthor.service.util.HintText;
import com.nwm.coauthor.service.util.MaxLength;
import com.nwm.coauthor.service.util.MinLength;
import com.nwm.coauthor.service.util.RequiredField;

public class NewStory extends BaseResource{
	
	@HintText("Enter a title:")
	@MinLength(1)
	@MaxLength(100)
	private String title;
	
	@RequiredField
	@MinLength(1)
	@MaxLength(1000)
	@HintText("Start the story here:")
	private String entry;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
}
