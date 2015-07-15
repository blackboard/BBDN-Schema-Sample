<%@ page language="java" contentType="text/html; charset=US-ASCII"
        pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.HashMap,
				 javax.servlet.http.HttpServletRequest, 
				 blackboard.base.FormattedText,
				 blackboard.platform.plugin.PlugInUtil,
				 bbdn.sample.schema.Properties,
				 bbdn.sample.schema.PropertiesDAO" 
%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>

<bbNG:genericPage
        title="Schema Sample Settings"
        ctxId="ctx"
        entitlement='system.admin.VIEW'
        navItem="bbdn-sample-schema-app-nav-1">

        <bbNG:pageHeader 
        	instructions="Manage the Properties for the sample schema building block">
        	
        	<bbNG:breadcrumbBar environment="SYS_ADMIN" />
            
            <bbNG:pageTitleBar>
                    Schema Sample Settings
            </bbNG:pageTitleBar>
            
        </bbNG:pageHeader>
        
        <bbNG:form action="saveProperties" method="POST" isSecure="${ true }" nonceId="/saveProperties">
        	<input type="hidden" name="user_id" value="<%=ctx.getUserId%>" />
        
        	<bbNG:dataCollection>
        		
        		<bbNG:step title="Schema Sample Settings" instructions="Set the settings below. Upon submit, the form data will be posted to a Spring controller, where the Blackboard dao annotations will be used to update the database.">
        	
        			<bbNG:dataElement label="Enable" renderLegendAndFieldset="true">
        				<bbNG:radioElement name="enabled" id="enabledYes" optionLabel="Yes" value="true" isSelected="${enabled}" />
        				<bbNG:radioElement name="enabled" id="enabledNo" optionLabel="No" value="false" isSelected="${!enabled}" />
        			</bbNG:dataElement>
        			
        			<bbNG:dataElement label="Message Text" renderLegendAndFieldset="true">
        				<bbNG:textbox name="message" ftext="${message}" />
        			</bbNG:dataElement>
        			
        			<bbNG:dataElement label="Status" renderLegendAndFieldset="true">
        				<p><bbNG:radioElement name="status" id="statusPending" optionLabel="Set status to needs approval" value="0" isSelected="${status[0]}" /></p>
        				<p><bbNG:radioElement name="status" id="statusApproved" optionLabel="Set status to approved" value="1" isSelected="${status[1]}" /></p>
        				<p><bbNG:radioElement name="status" id="statusDenied" optionLabel="Set status to denied" value="2" isSelected="${status[2]}" /></p>
        			</bbNG:dataElement>
        			
        		</bbNG:step>
        		
        		<bbNG:stepSubmit />
        	
        	</bbNG:dataCollection>
        </bbNG:form>
        
</bbNG:genericPage>