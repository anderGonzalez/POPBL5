package action.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * The Class LogoutAction. Class that logs out the loged user gives the option
 * to return to index after logout or to the calling page
 */
public class LogoutAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The url. */
	private String url;

	/** The goto index. */
	private String gotoIndex = "false";

	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("user");
		session.remove("listenerUser");
		session.remove("listenerRole");
		if (!gotoIndex.equals("true")) {
			HttpServletRequest request = ServletActionContext.getRequest();
			url = request.getHeader("referer");
		}

		return SUCCESS;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the goto index.
	 *
	 * @return the goto index
	 */
	public String getGotoIndex() {
		return gotoIndex;
	}

	/**
	 * Sets the goto index.
	 *
	 * @param gotoIndex
	 *            the new goto index
	 */
	public void setGotoIndex(String gotoIndex) {
		this.gotoIndex = gotoIndex;
	}

}
