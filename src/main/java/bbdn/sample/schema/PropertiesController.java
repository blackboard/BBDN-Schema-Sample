package bbdn.sample.schema;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bbdn.sample.schema.Properties;
import bbdn.sample.schema.PropertiesDAO;
import blackboard.base.FormattedText;
import blackboard.platform.spring.beans.annotations.UserAuthorization;

@Controller
public class PropertiesController {

	@Autowired
	private PropertiesDAO _dao;
	
	@UserAuthorization("system.admin.VIEW")
	
	@RequestMapping("/properties")
	public ModelAndView create( HttpServletRequest request, HttpServletResponse response )
	{
		ModelAndView mv = new ModelAndView("properties");

		Properties props = _dao.load();
		
		boolean enabled = props.isEnabled();	
		
		FormattedText message = FormattedText.toFormattedText(props.getMessage());
		
		boolean[] status = {false,false,false};
		status[props.getStatus()] = true;				
		
		mv.addObject("enabled", enabled);
		mv.addObject("message", message);
		mv.addObject("status", status);
		mv.addObject("props", props);
    	
    	return mv;   
	}

	@RequestMapping("/saveProperties")
	private void saveProps(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("enabled") String enabled,
							@RequestParam("messagetext") String message,
							@RequestParam("status") String status,
							@RequestParam("user_id") String userId
							) {

		Properties props = _dao.load();
        
        //set all prefs to incoming request parameter values
		props.enable(Boolean.valueOf(enabled));
		props.setMessage(message);
        props.setStatus(Integer.parseInt(status));
        props.setUserId(Integer.parseInt(userId));
        
        //save the prefs
        _dao.save(props);
        
        try {
			response.sendRedirect("index");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
