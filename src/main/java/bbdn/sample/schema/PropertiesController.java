package bbdn.sample.schema;

import java.io.IOException;
import java.util.StringTokenizer;

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
import blackboard.data.ReceiptOptions;
import blackboard.data.user.User;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.platform.servlet.InlineReceiptUtil;
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
		
		int userIdInt = 0;
        
        //set all prefs to incoming request parameter values
		props.enable(Boolean.valueOf(enabled));
		props.setMessage(message);
        props.setStatus(Integer.parseInt(status));
        try {
			Id userIdParam = Id.generateId(User.DATA_TYPE, userId);
			StringTokenizer sToken = new StringTokenizer(userIdParam.toExternalString(), "_");
			
			userIdInt = Integer.parseInt(sToken.nextToken());
		} catch (PersistenceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        props.setUserId(userIdInt);
        
        //save the prefs
        _dao.save(props);
        
        try {
        	ReceiptOptions ro = new ReceiptOptions();
        	ro.addSuccessMessage("Properties Successfully Updated."); // Escape since variable is not intended to contain HTML
        	InlineReceiptUtil.addReceiptToRequest(ro); 
        	response.sendRedirect("properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
