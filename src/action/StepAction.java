package action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

// TODO: Auto-generated Javadoc
/**
 * The Class StepAction.
 */
public class StepAction extends ActionSupport {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The repeat password. */
	public String repeatPassword;
	
	/** The name. */
	public String name = this.getName();
	
	
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getHeader("referer");
		
		ActionContext.getContext().getSession().put("lastPage", url);
		
		return SUCCESS;
	}
	

	/**
	 * Gets the repeat password.
	 *
	 * @return the repeat password
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/**
	 * Sets the repeat password.
	 *
	 * @param repeatPassword the new repeat password
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
