package eu.arrowhead.common.database.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import eu.arrowhead.common.database.entity.Cloud;

@Repository
public interface CloudRepository extends RefreshableRepository<Cloud,Long> {

	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public List<Cloud> findByOwnCloudAndSecure(final boolean ownCloud, final boolean secure);
}