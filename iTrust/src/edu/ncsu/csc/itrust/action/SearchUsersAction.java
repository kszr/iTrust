package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * SearchUsersAction
 */
@SuppressWarnings("unused")
public class SearchUsersAction {
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;


	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user who is performing the search.
	 */
	public SearchUsersAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
	}
	

	/**
	 * Searches for all personnel with the first name and last name specified in the parameter list.
	 * @param firstName The first name to be searched.
	 * @param lastName The last name to be searched.
	 * @return A java.util.List of PersonnelBeans for the users who matched.
	 */
	public List<PersonnelBean> searchForPersonnelWithName(String firstName, String lastName) {
		
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return personnelDAO.searchForPersonnelWithName(firstName, lastName);
		}
		catch (DBException e) {
			
			return null;
		}
	}
	
	/**
	 * Search for all experts with first name and last name given in parameters.
	 * @param query query
	 * @return A java.util.List of PersonnelBeans
	 */
	@SuppressWarnings("unchecked")
	public List<PersonnelBean> fuzzySearchForExperts(String query) {
		String[] subqueries=null;
		
		//Set<PersonnelBean> patientsSet = new TreeSet<PersonnelBean>();
		List<PersonnelBean> result = new ArrayList<PersonnelBean>();
		if(query!=null && query.length()>0 && !query.startsWith("_")){
			subqueries = query.split(" ");
			int i=0;
			for(String q : subqueries){
				try {
					List<PersonnelBean> first = personnelDAO.fuzzySearchForExpertsWithName(q, "");				
					List<PersonnelBean> last = personnelDAO.fuzzySearchForExpertsWithName("", q);
					
					for(int j=0; j < last.size(); j++){
					  if(!result.contains(last.get(j))){
						  result.add(0, last.get(j));
					  }
					}
					for(int j=0; j < first.size(); j++){
					  if(!result.contains(first.get(j))){
						  result.add(0, first.get(j));
					  }
					}
					i++;
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param firstName The first name of the patient being searched.
	 * @param lastName The last name of the patient being searched.
	 * @return A java.util.List of PatientBeans
	 */
	public List<PatientBean> searchForPatientsWithName(String firstName, String lastName) {
	
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return patientDAO.searchForPatientsWithName(firstName, lastName);
		}
		catch (DBException e) {
			
			return null;
		}
	}

	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param query query
	 * @return A java.util.List of PatientBeans
	 */
	public List<PatientBean> fuzzySearchForPatients(String query) {
		return fuzzySearchForPatients(query, false);
	}
	
	/**
	 * Search for all *female* patients with first name and last name given in parameters.
	 * @param query query
	 * @param allowDeactivated allowDeactivated
	 * @return A java.util.List of PatientBeans
	 */
	public List<PatientBean> fuzzySearchForFemalePatients(String query, boolean allowDeactivated){
		List<PatientBean> patientList= this.fuzzySearchForPatients(query, allowDeactivated);
		if(patientList != null){
			List<PatientBean> femalePatientList = new ArrayList<PatientBean>();
			for(PatientBean p : patientList){
				if("Female".equals(p.getGender().toString())){
					femalePatientList.add(p);
				}
			}
			return femalePatientList;
		}
		
		return null;
	}
	
	/**
	 * Search for all patients with first name and last name given in parameters.
	 * @param query query
	 * @param allowDeactivated allowDeactivated
	 * @return A java.util.List of PatientBeans
	 */
	@SuppressWarnings("unchecked")
	public List<PatientBean> fuzzySearchForPatients(String query, boolean allowDeactivated) {
		String[] subqueries=null;
		
		Set<PatientBean> patientsSet = new TreeSet<PatientBean>();
		if(query!=null && query.length()>0 && !query.startsWith("_")){
			subqueries = query.split(" ");
			Set<PatientBean>[] patients = new Set[subqueries.length];
			int i=0;
			for(String q : subqueries){
				try {
					patients[i] = new TreeSet<PatientBean>();
					List<PatientBean> first = patientDAO.fuzzySearchForPatientsWithName(q, "");				
					List<PatientBean> last = patientDAO.fuzzySearchForPatientsWithName("", q);
					patients[i].addAll(first);
					patients[i].addAll(last);
					
					try{
						long mid = Long.valueOf(q);
						//If the patient exists with the mid, then add the patient to the patient list
						if (patientDAO.checkPatientExists(mid)) {
							patients[i].add(patientDAO.getPatient(mid));
						}
					}catch(NumberFormatException e) {
						//TODO
					}
					i++;
				} catch (DBException e1) {
					e1.printStackTrace();
				}
			}
			
			if (i > 0) {
				patientsSet.addAll(patients[0]);
			}
			for(Set<PatientBean> results : patients){
				try{
					patientsSet.retainAll(results);
				}catch(NullPointerException e) {
					//TODO
				}
			}
		}
		ArrayList<PatientBean> results=new ArrayList<PatientBean>(patientsSet);
		
		if(allowDeactivated == false) {
			for(int i=results.size()-1; i>=0; i--){
				if(!results.get(i).getDateOfDeactivationStr().equals("")){
					results.remove(i);
				}
			}
		}
		
		return results;
	}
	
	/**
	 * getDeactivated is a special case used for when we want to see all deactivated patients.
	 * @return The List of deactivated patients.
	 */
	public List<PatientBean> getDeactivated(){
		List<PatientBean> result = new ArrayList<PatientBean>();
		try {
			result = patientDAO.getAllPatients();
			for(int i = result.size() - 1; i >= 0; i--){
				if(result.get(i).getDateOfDeactivationStr().equals("")){
					result.remove(i);
				}
			}
		} catch (DBException e) {
			//TODO
		}
		return result;
	}
}
