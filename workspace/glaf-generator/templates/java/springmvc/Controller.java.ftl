package ${packageName}.web.springmvc;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 

@Controller("/apps/${modelName}")
@RequestMapping("/apps/${modelName}.do")
public class ${entityName}Controller extends ${entityName}BaseController {
	private static final Log logger = LogFactory.getLog(${entityName}Controller.class);

	public ${entityName}Controller() {
	    
	}
 
}
