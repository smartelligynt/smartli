package com.st.services.oauth.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.st.services.oauth.domain.dao.ThirdPartyInfoDAO;
import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;

@Component("thirdPartyInfoStore")
public class ThirdPartyInfoStore {

	private Map<String, ThirdPartyInfoEntity> thirdpartyInfoMap = new HashMap<String, ThirdPartyInfoEntity>();

	@Autowired
	ThirdPartyInfoDAO thirdPartyInfoDAO;

	@PostConstruct
	public void load() {

		synchronized (thirdpartyInfoMap) {

			List<ThirdPartyInfoEntity> tpList = thirdPartyInfoDAO.getAll();
			for (ThirdPartyInfoEntity tp : tpList) {
				thirdpartyInfoMap.put(tp.getShortName(), tp);
			}

		}

	}

	public ThirdPartyInfoEntity get(String tpName) {

		ThirdPartyInfoEntity tpInfo = null;
		synchronized (thirdpartyInfoMap) {
			tpInfo = thirdpartyInfoMap.get(tpName);
		}

		if (tpInfo == null) {
			load();
			synchronized (thirdpartyInfoMap) {
				tpInfo = thirdpartyInfoMap.get(tpName);
			}
		}
		return tpInfo;
	}

	public ThirdPartyInfoEntity add(ThirdPartyInfoEntity thirdPartyInfo) {
		if (thirdPartyInfo == null
				|| StringUtils.isEmpty(thirdPartyInfo.getShortName())) {
			return null;
		}

		synchronized (thirdpartyInfoMap) {
			thirdpartyInfoMap
					.put(thirdPartyInfo.getShortName(), thirdPartyInfo);
		}

		return thirdPartyInfo;
	}

}
