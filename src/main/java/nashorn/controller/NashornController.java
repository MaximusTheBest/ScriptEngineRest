package nashorn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nashorn.model.ApiResponse;
import nashorn.model.ScriptRequest;
import nashorn.model.StatusScriptResponse;
import nashorn.service.NashornService;

@RestController
@RequestMapping(value = "/script")
public class NashornController {

	@Autowired
	private NashornService nashornService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> addScript(@RequestBody ScriptRequest input) {
		ApiResponse id = nashornService.addScript(input.getScript());
		return new ResponseEntity<ApiResponse>(id, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse getResultScriptById(@PathVariable("id") String id) throws NumberFormatException, Exception {
		return nashornService.getResultScriptById(Long.valueOf(id));
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public StatusScriptResponse getListScripts() {
		return nashornService.getListScripts();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse removeScriptById(@PathVariable("id") String id) throws NumberFormatException, Exception {
		return nashornService.removeScriptById(id);
	}

}
