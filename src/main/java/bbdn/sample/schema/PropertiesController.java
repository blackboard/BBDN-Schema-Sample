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

// Denote this class as a Spring Controller
@Controller
public class PropertiesController {

	// Autowire -- a.k.a. auto instantiate -- the PropertiesDAO object for use in the controller
	@Autowired
	private PropertiesDAO _dao;
	
	// This is a system too, so ensure the user running the code has permission to view the System Admin panel
	@UserAuthorization("system.admin.VIEW")
	
	// Any time Spring receives a request for the URL properties, execute this method. The method itself returns a ModelAndView. Spring uses this object to locate the appropriate jsp file and 
	// pass any variables you have set.
	@RequestMapping("/properties")
	public ModelAndView create( HttpServletRequest request, HttpServletResponse response )
	{
		// Create a new ModelAndView Object. The constructor takes a string that corresponds to the name of the jsp file the object should be routed to
		ModelAndView mv = new ModelAndView("properties");

		// Since we autowired the DAO, we can simply call the load method.
		Properties props = _dao.load();
		
		// Set enabled equal to the state specified in the database
		boolean enabled = props.isEnabled();	
		
		// get the string message from the database, convert it to FormattedText and store it in the message variable.
		FormattedText message = FormattedText.toFormattedText(props.getMessage());
		
		//Create our boolean array and instantiate to false, then set the appropriate status code equal to true.
		boolean[] status = {false,false,false};
		status[props.getStatus()] = true;				
		
		// ModelAndView.addObject adds the objects you wish to pass to the jsp
		mv.addObject("enabled", enabled);
		mv.addObject("message", message);
		mv.addObject("status", status);
		mv.addObject("props", props);
    	
		// Returning the ModelAndView tells spring to find the JSP and set these objects in the context for the request.
    	return mv;   
	}

	// Any time Spring receives a request for the URL properties, execute this method. The method itself returns a ModelAndView. Spring uses this object to locate the appropriate jsp file and 
	// pass any variables you have set. RequestParam pulls the query parameter from the request (ala request.getParameter("enabled")) amd sets it equal to the variable you declare
	@RequestMapping("/saveProperties")
	private void saveProps(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("enabled") String enabled,
							@RequestParam("messagetext") String message,
							@RequestParam("status") String status,
							@RequestParam("user_id") String userId
							) {
		
		// dao is already instantiated, so load the current instance, since properties only has one record that we maintain. If using a multiple record database, you would
		// want to create a new Properties object and set all the parameters associated with that object.
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
