package com.glaf.core.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.core.domain.*;
 

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface DatabaseAccessMapper {

	void deleteAccessor(DatabaseAccess model);

	void deleteDatabaseAccessByActorId(String actorId);
	
	void deleteDatabaseAccessByDatabaseId(Long databaseId);

	List<DatabaseAccess> getAllDatabaseAccesses();

	List<DatabaseAccess> getDatabaseAccessesByActorId(String actorId);
	
	List<DatabaseAccess> getDatabaseAccessesByDatabaseId(Long databaseId);

	void insertDatabaseAccess(DatabaseAccess model);

}
