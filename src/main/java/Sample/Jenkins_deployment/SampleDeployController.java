package Sample.Jenkins_deployment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
public class SampleDeployController {

	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
	    return new ResponseEntity<>("Testing Jenkins Deployment REST api.", HttpStatus.OK);
	}
}
