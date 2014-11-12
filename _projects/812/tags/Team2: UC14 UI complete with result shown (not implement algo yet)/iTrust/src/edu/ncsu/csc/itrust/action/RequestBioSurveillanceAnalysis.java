package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.RequestBioSurveillanceValidator;
/*
 * Handle after request biosurveillance analysis(Top form) from requestBiosurveilance.jsp
 */

public class RequestBioSurveillanceAnalysis {
	


	public RequestBioSurveillanceAnalysis() {
	
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function is called after submit button is clicked and POST method.
	 * Check whether the input (DiagnosisCode,ZipCode,Date) format is the right format
	 * @param requestBio
	 * @return boolean
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	//return true if success otherwise false
	public boolean requestBioAnalysis(BioSurveillanceBean requestBio) throws FormValidationException, ITrustException
	{
		new RequestBioSurveillanceValidator().validate(requestBio);
		
		if(requestBio.getDiagnosisCode().contains("084"))
				{
					System.out.println("malaria");
					return true;
				}
		else if (requestBio.getDiagnosisCode().contains("487"))
		{
			System.out.println("influenza");
			return true;
		}
		else return false;
		/*
		System.out.println(requestBio.getDiagnosisCode());
		System.out.println(requestBio.getZipCode());
		System.out.println(requestBio.getDate());
		System.out.println(requestBio.getThreshold());
		*/

	}

}
