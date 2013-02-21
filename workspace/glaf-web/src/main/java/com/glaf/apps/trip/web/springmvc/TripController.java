package com.glaf.apps.trip.web.springmvc;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 

@Controller
@RequestMapping("/apps/trip.do")
public class TripController extends TripBaseController {
	protected final static Log logger = LogFactory
			.getLog(TripController.class);

	public TripController() {
	    
	}
 
}
