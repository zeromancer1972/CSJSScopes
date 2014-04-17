package org.openntf.scopes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.xsp.extlib.util.ExtLibUtil;

/**
 * 
 * @author Oliver Busse, http://www.oliverbusse.com
 * 
 */

public class ScopeManager extends HttpServlet {

	private static final long serialVersionUID = 2094548026728443273L;

	/**
	 * called from the service page
	 * 
	 * @param facesContext
	 */
	public void init(FacesContext facesContext) {
		ExternalContext context = facesContext.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) context.getRequest();
		HttpServletResponse resp = (HttpServletResponse) context.getResponse();

		// method?
		try {
			if (req.getMethod().equalsIgnoreCase("get")) {
				doGet(req, resp);
			} else {
				doPost(req, resp);
			}
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		// fetch the scope and the key
		String scope = req.getParameter("scope");
		// get the key
		String key = req.getParameter("key");
		Object value = null;
		if (scope.equals("request")) {
			value = ExtLibUtil.getRequestScope().get(key);
		}
		if (scope.equals("view")) {
			value = ExtLibUtil.getViewScope().get(key);
		}
		if (scope.equals("application")) {
			value = ExtLibUtil.getApplicationScope().get(key);
		}
		if (scope.equals("session")) {
			value = ExtLibUtil.getSessionScope().get(key);
		}

		// transport it to JSON output
		JsonJavaObject json = new JsonJavaObject();
		json.put("key", key);
		json.put("value", value);

		// and print it out
		PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");
		resp.setHeader("Cache-Control", "no-cache");

		pw.write(json.toString());
		pw.flush();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String scope = req.getParameter("scope");
		String key = req.getParameter("key");
		String value = req.getParameter("value");
		if (scope.equalsIgnoreCase("request")) {
			ExtLibUtil.getRequestScope().put(key, value);
		}
		if (scope.equalsIgnoreCase("view")) {
			ExtLibUtil.getViewScope().put(key, value);
		}
		if (scope.equalsIgnoreCase("application")) {
			ExtLibUtil.getApplicationScope().put(key, value);
		}
		if (scope.equalsIgnoreCase("session")) {
			ExtLibUtil.getSessionScope().put(key, value);
		}

		JsonJavaObject json = new JsonJavaObject();
		json.put("status", "ok");

		PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");
		resp.setHeader("Cache-Control", "no-cache");
		pw.write(json.toString());
		pw.flush();

	}

}
