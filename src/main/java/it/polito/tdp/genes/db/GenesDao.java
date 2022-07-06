package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Genes> getAllEssentialGenes(){
		String sql = "select distinct * "
				+ "from genes "
				+ "where essential='essential' "
				+ "order by geneId";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
public List<Adiacenza> getInteractions(Map<String, Genes> genesIdMap) {
		
		String sql = "select geneId1, geneId2, expression_corr "
				+ "from interactions" ;
		Connection conn = DBConnect.getConnection() ;
		
		List<Adiacenza> result = new ArrayList<>();
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Genes gene1 = genesIdMap.get(res.getString("geneID1")) ;
				Genes gene2 = genesIdMap.get(res.getString("geneID2")) ;
				
				if( gene1!=null && gene2!=null && !gene1.equals(gene2) ) {
					result.add(new Adiacenza(gene1, gene2, res.getDouble("Expression_Corr"))) ;
				}
			}
			conn.close();
			return result ;
		} catch(SQLException ex) {
			throw new RuntimeException("Database error", ex) ;
		}
		
		
	}
	
	
	
	


	
}
