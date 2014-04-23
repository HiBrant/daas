package data.as.a.service.controller.ui;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import data.as.a.service.access.entity.jpa.sys.AppEntity;
import data.as.a.service.management.ApplicationService;

@Controller
@RequestMapping("/ui")
public class ApplicationController {

	@RequestMapping(value = "/create_app", method = RequestMethod.POST)
	@ResponseBody
	public Object create(@RequestParam("app_name") String appName, HttpSession session) {
		
		String userId = (String) session.getAttribute("userId");
		ApplicationService service = new ApplicationService();
		AppEntity app = service.createOne(appName, userId);
		JSONObject json = new JSONObject();
		if (app == null) {
			json.put("ok", 0);
			json.put("msg", "You have already created an app named \"" + appName + "\"");
		} else {
			json.put("ok", 1);
//			json.put("appName", app.getAppName());
//			json.put("appId", app.get_id());
//			json.put("apiKey", app.getApiKey());
		}
		return json;
	}
	
	@RequestMapping(value = "/apps", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllApps(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		ApplicationService service = new ApplicationService();
		List<AppEntity> list = service.getAllApps(userId);
		JSONArray array = new JSONArray();
		for (AppEntity app: list) {
			JSONObject json = new JSONObject();
			json.put("appId", app.get_id());
			json.put("appName", app.getAppName());
			array.add(json);
		}
		
		if (list.size() > 0) {
			session.setAttribute("app", list.get(0));
		}
		
		return array;
	}
}
