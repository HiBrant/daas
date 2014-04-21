package data.as.a.service.controller.ui;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	@ResponseBody
	public Object hello(@RequestParam String username, @RequestParam String password) {
		System.out.println(username);
		System.out.println(password);
		JSONObject json = new JSONObject();
		json.put("hello", "world");
		json.put("username", username);
		json.put("password", password);
		return json;
	}
}
