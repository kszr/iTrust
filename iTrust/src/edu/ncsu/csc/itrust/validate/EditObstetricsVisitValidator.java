package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditObstetricsVisitAction;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricsVisitForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Used to validate updating an obstetrics visit, by {@link EditObstetricsVisitAction}
 * 
 *  
 * 
 */
public class EditObstetricsVisitValidator extends BeanValidator<EditObstetricsVisitForm> {
	//private boolean validatePrescription = false;

	/**
	 * The default constructor.
	 */
	public EditObstetricsVisitValidator() {
	}

	/*public EditOfficeVisitValidator(boolean validatePrescription) {
		this.validatePrescription = validatePrescription;
	}*/

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(EditObstetricsVisitForm form) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("HCP ID", form.getHcpID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Patient ID", form.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Visit Date", form.getVisitDate(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("Weeks Pregnant", form.getWeeksPregnant(), ValidationFormat.WEEKS_PREGNANT, false));
		errorList.addIfNotNull(checkFormat("Days Pregnant", form.getDaysPregnant(), ValidationFormat.DAYS_PREGNANT, false));
		errorList.addIfNotNull(checkFormat("Fetal Heart Rate", form.getWeeksPregnant(), ValidationFormat.FETAL_HEART_RATE, false));
		errorList.addIfNotNull(checkFormat("Fundal Height of Uterus", form.getFundalHeightOfUterus(), ValidationFormat.FUNDAL_HEIGHT_OF_UTERUS, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
