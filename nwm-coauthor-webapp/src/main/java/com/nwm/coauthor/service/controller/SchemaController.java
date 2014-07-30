package com.nwm.coauthor.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.util.JsonSchemaUtil;

@Controller
@RequestMapping(value = "/schemas", produces = "application/json")
public class SchemaController extends BaseControllerImpl {
	@Autowired
	private JsonSchemaUtil jsonSchemaUtil;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/new-story")
    public ResponseEntity<Map<String, Object>> getNewStorySchema() throws Exception {
        Map<String, Object> schema = jsonSchemaUtil.outputSchema(NewStoryRequest.class);
        return new ResponseEntity<Map<String, Object>>(schema, HttpStatus.OK);
    }
}
