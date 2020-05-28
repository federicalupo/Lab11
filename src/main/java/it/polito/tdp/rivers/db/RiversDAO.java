package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	/**
	 * Media e misure
	 * @param river
	 * @return
	 */
	
	public List<Double> misuraMedia(River river) {
		
		String sql = "select count(*) as nMisure, avg(flow) media " + 
				"from flow " + 
				"where river =? " + 
				"order by day ";
		
		List<Double> misureMedia = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				if( res.getInt("nMisure") > 0) {
					misureMedia.add(res.getDouble("nMisure"));
					misureMedia.add(res.getDouble("media"));
					
					river.setFlowAvg(res.getDouble("media"));
				}
				
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misureMedia;
	}
	/**
	 * date inizio e fine
	 * @param river
	 * @return
	 */
	public List<Flow> flussi(River river) {
		
		String sql = "select day, flow " + 
				"from flow " + 
				"where river =? " + 
				"order by day ";
		
		
		List<Flow> flussi = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				LocalDate data = res.getDate("day").toLocalDate();
				Double f = res.getDouble("flow");
				
				Flow flusso = new Flow(data, f, river);
				flussi.add(flusso);
				
			}
			
			river.setFlows(flussi);

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flussi;
	}
}
