package edu.ncsu.csc.itrust.validate;


import java.util.Date;

import edu.ncsu.csc.itrust.beans.OIRBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * The validator used by {@link OIRAction}. Only checks first name, last name, and email
 * 
 *  
 * 
 */
public class OIRValidator extends BeanValidator<OIRBean> {
	/**
	 * The default constructor.
	 */
	public OIRValidator() {
	}
	
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(OIRBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Patient ID", bean.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkDouble("Weeks Pregnant", ""+bean.getWeeksPregnant(), 0, 60));
	
		Date creationDate = bean.getCreationDate();
		Date LMP = bean.getLMP();
		if(LMP.after(creationDate)){
			errorList.addIfNotNull("Last menstural period must be before creation date");
		}
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
