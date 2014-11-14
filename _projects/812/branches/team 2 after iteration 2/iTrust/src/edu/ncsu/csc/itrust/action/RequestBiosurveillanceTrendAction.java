package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.RequestBioSurveillanceValidator;
/*
 * Handle after request biosurveillance trend(Bottom form) from requestBiosurveilance.jsp
 */
public class RequestBiosurveillanceTrendAction {

	public RequestBiosurveillanceTrendAction() {

		// TODO Auto-generated constructor stub
	}

	/**
	 *  This function is called after submit button is clicked and POST method.
	 *  Check whether the input (DiagnosisCode,ZipCode,Date) format is the right format
	 * @param requestBio
	 * @return
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	//return true if success otherwise false
	public boolean requestBioTrendVerify(BioSurveillanceBean requestBio) throws FormValidationException, ITrustException
	{
		new RequestBioSurveillanceValidator().validate(requestBio);
		/*
		System.out.println(requestBio.getDiagnosisCode());
		System.out.println(requestBio.getZipCode());
		System.out.println(requestBio.getDate());
	*/
		return true;
	}
	
	


}
