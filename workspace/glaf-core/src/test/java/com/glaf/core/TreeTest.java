package com.glaf.core;

import java.util.List;

import org.junit.Test;

import com.glaf.core.base.TreeModel;
import com.glaf.test.AbstractTest;

public class TreeTest extends AbstractTest {
	
	@Test
	public void testTree(){
		List<TreeModel> treeModels = com.glaf.core.security.IdentityFactory.getChildrenTreeModels(3L);
	   if(treeModels != null && !treeModels.isEmpty()){
		   for(TreeModel treeModel:treeModels){
			   logger.debug(treeModel.toJsonObject().toJSONString());
		   }
	   }
	}

}
