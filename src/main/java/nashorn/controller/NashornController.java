package nashorn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nashorn.model.ScriptRequest;
import nashorn.model.StatusRequest;
import nashorn.service.NashornService;

@RestController
@RequestMapping(value = "/script")
public class NashornController {

	@Autowired
	private NashornService nashornService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addScript(@RequestBody ScriptRequest input) {
		Long id = nashornService.addScript(input.getScript());
		return new ResponseEntity<Long>(id, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getResultScriptById(@PathVariable("id") String id) throws NumberFormatException, Exception {
		return nashornService.getResultScriptById(Long.valueOf(id));
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StatusRequest> getListScripts() {
		return nashornService.getListScripts();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String removeScriptById(@PathVariable("id") String id) throws NumberFormatException, Exception {
		nashornService.removeScriptById(id);
		return "";
	}

}
