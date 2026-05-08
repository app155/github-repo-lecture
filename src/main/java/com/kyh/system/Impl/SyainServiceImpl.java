package com.kyh.system.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyh.system.mapper.SyainMapper;
import com.kyh.system.model.Syain;
import com.kyh.system.service.SyainService;

@Service(value = "syainService")
public class SyainServiceImpl implements SyainService {

	@Autowired
	public SyainMapper syainMapper; 
	
	@Override
	public List<Map<String, Object>> getCompanyNameList() {
		return syainMapper.getCompanyNameList();
	}

	@Override
	public List<Map<String, Object>> getJobTypeList() {
		return syainMapper.getJobTypeList();
	}

	@Override
	public List<Syain> getSyainList(int company, String name, int jobType, boolean active, boolean inactive) {
		return syainMapper.getSyainList(company, name, jobType, active, inactive);
	}

	@Override
	public int deleteSyain(int syainId) {
		return syainMapper.deleteByPrimaryKey(syainId);
	}

	@Override
	public Syain getSyain(int syainId) {
		return syainMapper.selectByPrimaryKey(syainId);
	}

	@Override
	public int updateSyain(Syain syain) {
		return syainMapper.updateByPrimaryKeySelective(syain);
	}

	@Override
	public List<String> getOSList() {
		return syainMapper.getOSList();
	}

	@Override
	public boolean addSyain(Syain syain) {
		return syainMapper.insert(syain) == 0 ? false : true;
	}
}
