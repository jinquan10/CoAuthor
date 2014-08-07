package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;
import com.nwm.coauthor.service.util.HintText;
import com.nwm.coauthor.service.util.MaxLength;
import com.nwm.coauthor.service.util.MinLength;
import com.nwm.coauthor.service.util.RequiredField;

public class EntryRequest extends BaseResource {
	@RequiredField
	@MinLength(10)
	@MaxLength(1000)
	@HintText("Continue the story here...")
	private String entry;
	
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
}
