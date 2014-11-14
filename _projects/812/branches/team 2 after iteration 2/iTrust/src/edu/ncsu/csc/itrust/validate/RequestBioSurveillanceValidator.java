package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validate the right format for zipcode,diagnosis code and date for RequestBiosurveillance.jsp
 * 
 */
public class RequestBioSurveillanceValidator extends BeanValidator<BioSurveillanceBean> {

	public RequestBioSurveillanceValidator() {
	
	
	}
/**
 * Perform the act of valiation of the BioSurveillance Ban
 *@param BioSurveillanceBean for validation
 */
	@Override
	public void validate(BioSurveillanceBean bb) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Date", bb.getDate(),ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("Zip Code", bb.getZipCode(),ValidationFormat.ZIPCODE, false));
		errorList.addIfNotNull(checkFormat("Diagnosis Code",bb.getDiagnosisCode(),ValidationFormat.ICD9CM,false));
		if(bb.getDiagnosisCode().contains("084.5")) {
			errorList.addIfNotNull(checkFormat("Threshold for Malaria", bb.getThreshold(),ValidationFormat.THRESHOLDFORMALARIA,false));
		}
		if(errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	

}
