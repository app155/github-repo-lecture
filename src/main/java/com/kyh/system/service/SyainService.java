package com.kyh.system.service;

import java.util.List;
import java.util.Map;

import com.kyh.system.model.Syain;

public interface SyainService {
	public List<Map<String, Object>> getCompanyNameList();
	public List<Map<String, Object>> getJobTypeList();
	public List<Syain> getSyainList(int company, String name, int jobType, boolean active, boolean inactive);
	public int deleteSyain(int syainId);
	public Syain getSyain(int syainId);
	public int updateSyain(Syain syain);
	public List<String> getOSList();
	public boolean addSyain(Syain syain);
}
