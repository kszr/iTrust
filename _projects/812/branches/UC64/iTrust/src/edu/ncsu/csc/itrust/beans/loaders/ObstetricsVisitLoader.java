package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;

/**
 * A loader for ObstetricsVisitBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ObstetricsVisitLoader implements BeanLoader<ObstetricsVisitBean> {
	public List<ObstetricsVisitBean> loadList(ResultSet rs) throws SQLException {
		List<ObstetricsVisitBean> list = new ArrayList<ObstetricsVisitBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public ObstetricsVisitBean loadSingle(ResultSet rs) throws SQLException {
		ObstetricsVisitBean ov = new ObstetricsVisitBean(rs.getInt("ID"));
		ov.setHcpID(rs.getLong("HCPID"));
		ov.setPatientID(rs.getLong("PatientID"));
		ov.setVisitDateStr(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("VisitDate").getTime())));
		ov.setWeeksPregnant(rs.getInt("WeeksPregnant"));
		ov.setDaysPregnant(rs.getInt("DaysPregnant"));
		ov.setFetalHeartRate(rs.getInt("FetalHeartRate"));
		ov.setFundalHeightOfUterus(rs.getDouble("FundalHeightOfUterus"));
		return ov;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, ObstetricsVisitBean p) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}
}
