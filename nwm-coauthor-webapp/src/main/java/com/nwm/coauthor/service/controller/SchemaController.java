package com.nwm.coauthor.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.service.resource.request.NewStory;
import com.nwm.coauthor.service.util.JsonSchemaUtil;

@Controller
@RequestMapping(value = "/schema", produces = "application/json", consumes = "application/json")
public class SchemaController extends BaseControllerImpl {
	@Autowired
	private JsonSchemaUtil jsonSchemaUtil;
	
	@RequestMapping(method = RequestMethod.GET, value = "/stories/schema/new")
    public ResponseEntity<Map<String, Object>> getOrganizationSchema() throws Exception {
        Map<String, Object> schema = jsonSchemaUtil.outputSchema(NewStory.class);
        return new ResponseEntity<Map<String, Object>>(schema, HttpStatus.OK);
    }
}
