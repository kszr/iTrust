package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.PregnancyBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * The validator used by {@link OIRAction}. Only checks first name, last name, and email
 * 
 *  
 * 
 */
public class PregnancyValidator extends BeanValidator<PregnancyBean>{
	/**
	 * The default constructor.
	 */
	public PregnancyValidator() {
	}
	
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(PregnancyBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Patient ID", bean.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkInt("Weeks Pregnant", ""+bean.getNumberOfWeeksPregnant(), 0, 60, false));
		errorList.addIfNotNull(checkInt("Days Pregnant", ""+bean.getNumberOfDaysPregnant(), 0, 6, false));
		errorList.addIfNotNull(checkDouble("Hours In Labor", ""+bean.getHoursInLabor(), 0.0, 48.0));
		errorList.addIfNotNull(checkFormat("Year of Conception", ""+bean.getYearOfContraception(), ValidationFormat.YEAR, false));
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
