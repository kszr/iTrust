package edu.ncsu.csc.itrust.action;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ViewTransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;



public class ViewTransactionAction {
	private ViewTransactionDAO viewTransactionDao;
	
	public ViewTransactionAction(DAOFactory factory)
	{
		this.viewTransactionDao = factory.getViewTransactionDAO();
	}
	
	public List<ViewTransactionBean>  getTransactionView(ViewTransactionBean viewTransactionBean)
	{
		List<ViewTransactionBean> ret = new ArrayList<ViewTransactionBean>();
		
		try {
//			System.out.println("logguser: " + viewTransactionBean.getLoggedInRole());
//			System.out.println("second: " + viewTransactionBean.getSecondaryRole());
//			System.out.println("startdate: " + viewTransactionBean.getStartDate());
//			System.out.println("enddate: " + viewTransactionBean.getEndDate());
//			System.out.println("Transactiontype: " + viewTransactionBean.getTransactionType());
			ret = viewTransactionDao.getSelectedTransactions(viewTransactionBean);
			System.out.println("hey");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public HashMap getDistinctLoggedUser(List<ViewTransactionBean> beanList)
	{
		HashMap distinct_logged_in_map = new HashMap();
		for (ViewTransactionBean element : beanList) {
			if(distinct_logged_in_map.containsKey(element.getLoggedInRole()))
			{
				int prevValue = (int) distinct_logged_in_map.get(element.getLoggedInRole());
				distinct_logged_in_map.replace(element.getLoggedInRole(), ++prevValue);

			}
			else
			{
				distinct_logged_in_map.put(element.getLoggedInRole(), 1);
			}
		}
		//printMap(distinct_logged_in_map);
		return distinct_logged_in_map;
	}
	public HashMap getDistinctSecondaryUser(List<ViewTransactionBean> beanList)
	{
		HashMap distinct_secondary_user_map = new HashMap();
		for (ViewTransactionBean element : beanList) {
			if(distinct_secondary_user_map.containsKey(element.getSecondaryRole()))
			{
				int prevValue = (int) distinct_secondary_user_map.get(element.getSecondaryRole());
				distinct_secondary_user_map.replace(element.getSecondaryRole(), ++prevValue);

			}
			else
			{
				distinct_secondary_user_map.put(element.getSecondaryRole(), 1);
			}
		}
	//printMap(distinct_secondary_user_map);
		return distinct_secondary_user_map;
	}
	
	public Map getDateCount(List<ViewTransactionBean> beanList)
	{
	
		List<ViewTransactionBean> reverseList = Lists.reverse(beanList);

		for (ViewTransactionBean element : reverseList) {
			Timestamp temp = element.getTimestamp();
			temp.setHours(0);
			temp.setMinutes(0);
			temp.setSeconds(0);
			temp.setNanos(0);
		    element.setTimestamp(temp);
		}
	
		HashMap map = new HashMap();
		for (ViewTransactionBean element : reverseList) {
			String time = element.getTimestamp().toString().substring(0, 10);
			if(map.containsKey(time))
			{
				int prevValue = (int) map.get(time);
				map.replace(time, ++prevValue);

			}
			else
			{
				
				map.put(time, 1);
			}
		}
		Map sortedMap = new TreeMap(map);
	//printMap(sortedMap);
		return sortedMap;
	}
	
	public HashMap getTransactionTypeCount(List<ViewTransactionBean> beanList)
	{
		HashMap map = new HashMap();
		for (ViewTransactionBean element : beanList) {
			if(map.containsKey(element.getTransactionType()))
			{
				int prevValue = (int) map.get(element.getTransactionType());
				map.replace(element.getTransactionType(), ++prevValue);

			}
			else
			{
				map.put(element.getTransactionType(), 1);
			}
		}
	//printMap(map);
		return map;
	}
/*	
	private static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	*/

}