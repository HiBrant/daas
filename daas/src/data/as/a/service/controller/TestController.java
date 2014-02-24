package data.as.a.service.controller;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import data.as.a.service.access.entity.jpa.Person;
import data.as.a.service.adaptor.TestAdaptor;

@Controller
public class TestController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello() {
		return "Hello DaaS!";
	}
	
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Object sayHelloTo(
			@PathVariable("name") String name) {
		JSONObject json = new JSONObject();
		json.put("msg", "Hello " + name);
		return json;
	}
	
	@RequestMapping(value = "/person/{name}", method = RequestMethod.POST)
	@ResponseBody
	public boolean newPerson(@PathVariable("name") String name) {
		TestAdaptor.savePerson(name);
		return true;
	}
	
	@RequestMapping(value = "/person/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Object findPerson(@PathVariable("name") String name) {
		Person p = TestAdaptor.findByName(name);
		JSONObject json = JSONObject.fromObject(p.toString());
		return json;
	}
	
	@RequestMapping(value = "/m/person/{name}", method = RequestMethod.POST)
	@ResponseBody
	public boolean savePersonToMongoDB(@PathVariable String name) {
		TestAdaptor.savePersonInMongo(name);
		return true;
	}
}
