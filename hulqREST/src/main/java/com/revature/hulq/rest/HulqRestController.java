package com.revature.hulq.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
public class HulqRestController {
    
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	public boolean submit(@RequestBody SourceCodePair scp){
		HulqDriver ct = new HulqDriver();
		if (ct.executeCodeTest(scp.getKeyFileName(), scp.getTestFileName()) < 100.0){
			return false;
		}
			return true;
	}
	
	
	@RequestMapping(value="/submission", method=RequestMethod.POST)
	public double grade(@RequestBody SourceCodePair scp){
		HulqDriver ct = new HulqDriver();
		return ct.executeCodeTest(scp.getKeyFileName(), scp.getTestFileName());
	}
}
