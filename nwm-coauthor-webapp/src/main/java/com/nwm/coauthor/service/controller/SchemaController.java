package com.nwm.coauthor.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nwm.coauthor.Praises;
import com.nwm.coauthor.service.resource.request.EntryRequest;
import com.nwm.coauthor.service.resource.request.NativeAuthRequest;
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
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/request-entry")
    public ResponseEntity<Map<String, Object>> getEntryRequestSchema() throws Exception {
        Map<String, Object> schema = jsonSchemaUtil.outputSchema(EntryRequest.class);
        return new ResponseEntity<Map<String, Object>>(schema, HttpStatus.OK);
    }
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/praises")
    public ResponseEntity<Map<String, Object>> getGenresSchema() throws Exception {
        Map<String, Object> schema = jsonSchemaUtil.outputSchema(Praises.class);
        return new ResponseEntity<Map<String, Object>>(schema, HttpStatus.OK);
    }
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/native-auth")
    public ResponseEntity<Map<String, Object>> getAccountCreation() throws Exception {
        Map<String, Object> schema = jsonSchemaUtil.outputSchema(NativeAuthRequest.class);
        return new ResponseEntity<Map<String, Object>>(schema, HttpStatus.OK);
    }	
}
