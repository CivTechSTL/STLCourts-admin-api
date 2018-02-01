package svc.profiles;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.hsqldb.util.DatabaseManagerSwing;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("localWithHSQLAccess")
public class LocalWithHSQLAccessProfile {
	//default db: testdb, username : sa, password : ''
		@PostConstruct
		public void getDbManager() throws SQLException{
			DatabaseManagerSwing manager = new DatabaseManagerSwing();
			manager.main();
			manager.start();
		}
	
}
