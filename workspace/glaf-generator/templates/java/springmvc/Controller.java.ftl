package ${packageName}.web.springmvc;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 

@Controller("/apps/${modelName}")
@RequestMapping("/apps/${modelName}.do")
public class ${entityName}Controller extends ${entityName}BaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public ${entityName}Controller() {
	    
	}
 
}
