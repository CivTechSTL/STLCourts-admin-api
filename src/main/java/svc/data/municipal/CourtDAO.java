package svc.data.municipal;

import com.stlcourts.common.models.Court;
import com.stlcourts.common.models.Judge;
import com.stlcourts.common.types.HashableEntity;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourtDAO extends BaseJdbcDao {
	public static final String COURT_ID_COLUMN_NAME = "court_id";
	public static final String COURT_NAME_COLUMN_NAME = "court_name";
    public static final String COURT_PHONE_COLUMN_NAME = "phone";
    public static final String COURT_PHONE_EXTENSION_COLUMN_NAME = "extension";
    public static final String COURT_WEBSITE_COLUMN_NAME = "website";
    public static final String COURT_PAYMENT_SYSTEM_COLUMN_NAME = "payment_system";
    public static final String COURT_ADDRESS_COLUMN_NAME = "address";
    public static final String COURT_CITY_COLUMN_NAME = "city";
    public static final String COURT_STATE_COLUMN_NAME = "state";
    public static final String COURT_ZIP_COLUMN_NAME = "zip_code";
    public static final String COURT_LATITUDE_COLUMN_NAME = "latitude";
    public static final String COURT_LONGITUDE_COLUMN_NAME = "longitude";
    public static final String COURT_CITATION_EXPIRES_DAYS = "citation_expires_after_days";
	
    
    public Court newCourt(Court court){
    	GeneratedKeyHolder holder = new GeneratedKeyHolder();
    	SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", court.name)
				.addValue("phone", court.phone)
				.addValue("extension", court.extension)
				.addValue("website", court.website)
				.addValue("paymentSystem", court.paymentSystem)
				.addValue("address", court.address)
				.addValue("city", court.city)
				.addValue("state", court.state)
				.addValue("zipCode", court.zip)
				.addValue("latitude", court.latitude)
				.addValue("longitude", court.longitude)
				.addValue("citationExpires", court.citation_expires_after_days);
		
		try{
			jdbcTemplate.update(getSql("court/insert-court.sql"), parameters, holder); 
		}catch(Exception e){
			LogSystem.LogDBException(e);
		}	
		
		//updated judges
		
		long generatedId = holder.getKey().longValue();
		return court;
    }
    
    public Court updateCourt(Court court){
    	Map<String, Object> parameterMap = createParamMap(court);
		
		try{
			jdbcTemplate.update(getSql("court/update-court.sql"), parameterMap); 
		}catch(Exception e){
			LogSystem.LogDBException(e);
		}	
		//updated judges
		return court;
    }
   
    
    private Map<String, Object> createParamMap(Court court){
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", court.id.getValue());
		parameterMap.put("name", court.name);
		parameterMap.put("phone", court.phone);
		parameterMap.put("extension", court.extension);
		parameterMap.put("website", court.website);
		parameterMap.put("paymentSystem", court.paymentSystem);
		parameterMap.put("address", court.address);
		parameterMap.put("city", court.city);
		parameterMap.put("state", court.state);
		parameterMap.put("zipCode", court.zip);
		parameterMap.put("latitude", court.latitude);
		parameterMap.put("longitude", court.longitude);
		parameterMap.put("citationExpires", court.citation_expires_after_days);
		
		return parameterMap;
    }
    
	public Court getCourtById(Long courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
            String sql = getSql("court/get-all.sql") + " WHERE c.court_id = :courtId";

            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            jdbcTemplate.query(sql, parameterMap, courtRowCallbackHandler);

            return courtRowCallbackHandler.courts.get(0); //Should only be 1
		}catch (Exception e){
            LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Court> getAllCourts() {
		try  {
            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            String sql = getSql("court/get-all.sql") + " ORDER BY c.court_name";
            jdbcTemplate.query(sql, courtRowCallbackHandler);

			return courtRowCallbackHandler.courts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}

	public List<Court> getCourtsByMunicipalityId(Long municipalityId) {
        try {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("municipalityId", municipalityId);

            String sql = getSql("court/get-all.sql") + " WHERE mc.municipality_id = :municipalityId ORDER BY c.court_name";
            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            jdbcTemplate.query(sql, parameterMap, courtRowCallbackHandler);

            return courtRowCallbackHandler.courts;
        } catch (Exception e) {
            LogSystem.LogDBException(e);
            return null;
        }
    }

    private final class CourtRowCallbackHandler implements RowCallbackHandler {
        private Map<Long, Court> courtMap = new HashMap<>();
        //use array list so results remain in their sorted order
	    public List<Court> courts = new ArrayList<Court>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            try {
            	Long courtId = rs.getLong(COURT_ID_COLUMN_NAME);
                Judge judge = buildJudge(rs);
                if (courtMap.containsKey(courtId)){
                    courtMap.get(courtId).judges.add(judge);
                }else{
                    Court court = buildCourt(rs);
                    court.judges.add(judge);
                    courtMap.put(courtId, court);
                    courts.add(court);
                }
            } catch (Exception e) {
                LogSystem.LogDBException(e);
            }
        }

        private Court buildCourt(ResultSet rs) throws SQLException {
            Court court = new Court();
            court.id = new HashableEntity<Court>(Court.class,rs.getLong(COURT_ID_COLUMN_NAME));
            court.name = rs.getString(COURT_NAME_COLUMN_NAME);
            court.phone = rs.getString(COURT_PHONE_COLUMN_NAME).replaceAll("[.\\- ]", ".");
            if (!court.phone.equals("")){
                String[] phoneParts = court.phone.split("\\.");
                court.phone = "("+phoneParts[0]+") "+phoneParts[1]+"-"+phoneParts[2];
            }
            court.extension = rs.getString(COURT_PHONE_EXTENSION_COLUMN_NAME);
            court.website = rs.getString(COURT_WEBSITE_COLUMN_NAME);
            court.paymentSystem = rs.getString(COURT_PAYMENT_SYSTEM_COLUMN_NAME);
            court.address = rs.getString(COURT_ADDRESS_COLUMN_NAME);
            court.city = rs.getString(COURT_CITY_COLUMN_NAME);
            court.state = rs.getString(COURT_STATE_COLUMN_NAME);
            court.zip = rs.getString(COURT_ZIP_COLUMN_NAME);
            court.latitude = new BigDecimal(rs.getString(COURT_LATITUDE_COLUMN_NAME));
            court.longitude = new BigDecimal(rs.getString(COURT_LONGITUDE_COLUMN_NAME));
            court.citation_expires_after_days = rs.getInt(COURT_CITATION_EXPIRES_DAYS);
            court.judges = new ArrayList<Judge>();

            return court;
        }

        private Judge buildJudge(ResultSet rs) throws SQLException {
            Judge judge = new Judge();
            judge.id = new HashableEntity<Judge>(Judge.class,rs.getLong("JUDGE_ID"));
            judge.judge = rs.getString(rs.findColumn("judge"));
            judge.court_id = new HashableEntity<Court>(Court.class,rs.getLong("JUDGES_COURT_ID"));
            return judge;
        }
    }
}
