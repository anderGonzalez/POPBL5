package action.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Passenger;
import domain.model.User;

public class RegisterAction extends ActionSupport {

	private static final int MIN_YEARS = 18;
	private static final String DATE_FORMAT = "dd-MM-yyyy";
	private static final String PASS_NOT_MATCH = "Passwords do not match!";
	private static final String REPEAT_PASSWORD = "repeatPassword";
	private static final String INCORRECT_FORMAT = "Incorrect format!";
	private static final String TOO_YOUNG = "You are too young m8!";
	private static final String BIRTH_DATE = "birthdate";
	private static final String BIRTHDATE_BLANK = "Birthdate cannot be blank";
	private static final String PASSWORD_BLANK = "Password cannot be blank";
	private static final String PASSWORD = "user.password";
	private static final String USERNAME_BLANK = "Username cannot be blank";
	private static final String USERNAME = "user.username";
	private static final long serialVersionUID = 1L;

	User user = new User();
	String type;
	String birthdate;
	String repeatPassword;

	@Override
	public void validate() {
		if (user.getPassword() == null || user.getPassword().isEmpty())
			addFieldError(PASSWORD, PASSWORD_BLANK);
		else {
			if (!user.getPassword().equals(repeatPassword))
				addFieldError(REPEAT_PASSWORD, PASS_NOT_MATCH);
		}
		if (user.getUsername() == null || user.getUsername().isEmpty())
			addFieldError(USERNAME, USERNAME_BLANK);

		if (birthdate == null || birthdate.isEmpty())
			addFieldError(BIRTH_DATE, BIRTHDATE_BLANK);
		else {
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
			try {
				user.setBirthDate(df.parse(birthdate));
				LocalDate birthdate = user.getBirthDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				if (Period.between(birthdate, LocalDate.now()).getYears() < MIN_YEARS)
					addFieldError(BIRTH_DATE, TOO_YOUNG);
			} catch (ParseException e) {
				addFieldError(BIRTH_DATE, INCORRECT_FORMAT);
			}
		}
		if (!getFieldErrors().isEmpty()) {
			repeatPassword = "";
			user.setPassword("");
		}
	}

	@Override
	public String execute() throws Exception {
		type = User.PASSENGER;
		switch (type) {
		// TODO mas tipos
		case User.PASSENGER:
			user = new Passenger(user);
			break;
		case "default":
			break;

		}
		String ret = HibernateGeneric.insertObject(user) ? SUCCESS : ERROR;
		user = new User();
		repeatPassword = "";
		return ret;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

}
