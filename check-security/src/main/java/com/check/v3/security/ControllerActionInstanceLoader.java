package com.check.v3.security;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.check.v3.service.InstanceLoaderService;
import com.check.v3.util.KeyUtils;

@Service("controllerActionInstanceLoader")
public class ControllerActionInstanceLoader {
	private static final Logger logger = LoggerFactory.getLogger(ControllerActionInstanceLoader.class);

	private  Map<String,InstanceLoaderService> allInstanceLoaders 	= new HashMap<String,InstanceLoaderService>();

	
	public void registerInstanceLoaderService(String controller,String action ,InstanceLoaderService instanceLoaderService)
	{
		addInstanceLoader(controller,action,instanceLoaderService);
	}
	
	private void  addInstanceLoader(String controller,String action,InstanceLoaderService loadInstance)
	{
		logger.trace("add InstanceLoader {} for controller[{}] action[{}] ",new Object[]{loadInstance,controller,action});
		if (loadInstance == null){
			throw new RuntimeException("null loadInstace");
		}
		allInstanceLoaders.put(KeyUtils.buildKey(controller,action),loadInstance);
	}
	
	public InstanceLoaderService getInstanceLoaderService(String controller,String action)
	{
		InstanceLoaderService loadInstanceService = allInstanceLoaders.get(KeyUtils.buildKey(controller,action));
		if (loadInstanceService == null)
			logger.trace("can't find controller {} action {} loadInstanceService",controller,action);
		return loadInstanceService;
	}

}
