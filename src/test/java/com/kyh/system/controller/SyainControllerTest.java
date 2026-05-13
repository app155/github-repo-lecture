package com.kyh.system.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.kyh.system.model.Syain;
import com.kyh.system.service.SyainService;

class SyainControllerTest {

	private SyainController controller;
	private SyainService syainService;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private Model model;

	@BeforeEach
	void SetUp() {
		controller = new SyainController();
		syainService = mock(SyainService.class);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		model = mock(Model.class);
		controller.syainService = syainService;
	}

	@Test
	public void TestModifyPage_nyuusyaDateNotNull() throws ParseException {
		int testSyainId = 1;
		Syain testSyain = new Syain();
		Date nyuusyaDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-01");	
		Date taisyaDate = new SimpleDateFormat("yyyy-MM-dd").parse("2026-03-02");
		
		testSyain.setFirstNameKanji("金");
		testSyain.setLastNameKanji("ハンギョル");
		testSyain.setFirstNameKana("キム");
		testSyain.setLastNameKana("ハンギョル");
		testSyain.setFirstNameEigo("HANKYEOL");
		testSyain.setLastNameEigo("KIM");
		testSyain.setNyuusyaDate(nyuusyaDate);
		testSyain.setTaisyaDate(taisyaDate);
		
		List<Map<String, Object>> companyNameList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category2", 1); put("value1", "株式会社ブライトスター"); }},
				new HashMap<String, Object>() {{ put("category2", 2); put("value1", "株式会社トップクラウド"); }}
				);
		
		List<Map<String, Object>> jobtypeList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category3", 1); put("value1", "役員"); }},
			    new HashMap<String, Object>() {{ put("category3", 2); put("value1", "総務"); }},
			    new HashMap<String, Object>() {{ put("category3", 3); put("value1", "IT営業"); }},
			    new HashMap<String, Object>() {{ put("category3", 4); put("value1", "ITエンジニア"); }},
			    new HashMap<String, Object>() {{ put("category3", 5); put("value1", "不動産スタッフ"); }},
			    new HashMap<String, Object>() {{ put("category3", 6); put("value1", "個人事業主"); }}
				);

		when(syainService.getSyain(01)).thenReturn(testSyain);
		when(syainService.getCompanyNameList()).thenReturn(companyNameList);
		when(syainService.getJobTypeList()).thenReturn(jobtypeList);
		
		String viewName = controller.ModifyPage(testSyainId, model);
		
		assertEquals("syain/modify", viewName);
		
		verify(model).addAttribute("syain", testSyain);
		verify(model).addAttribute("nyuusyaDate", "2024-03-01");
		verify(model).addAttribute("syozokuKaisya", companyNameList);
		verify(model).addAttribute("syokugyoKind", jobtypeList);
	}
	
	@Test
	public void TestModifyPage_nyuusyaDateNull() throws ParseException {
		int testSyainId = 1;
		Syain testSyain = new Syain();
		Date nyuusyaDate = null;	
		Date taisyaDate = new SimpleDateFormat("yyyy-MM-dd").parse("2026-03-02");
		
		testSyain.setFirstNameKanji("金");
		testSyain.setLastNameKanji("ハンギョル");
		testSyain.setFirstNameKana("キム");
		testSyain.setLastNameKana("ハンギョル");
		testSyain.setFirstNameEigo("HANKYEOL");
		testSyain.setLastNameEigo("KIM");
		testSyain.setNyuusyaDate(nyuusyaDate);
		testSyain.setTaisyaDate(taisyaDate);
		
		List<Map<String, Object>> companyNameList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category2", 1); put("value1", "株式会社ブライトスター"); }},
				new HashMap<String, Object>() {{ put("category2", 2); put("value1", "株式会社トップクラウド"); }}
				);
		
		List<Map<String, Object>> jobtypeList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category3", 1); put("value1", "役員"); }},
			    new HashMap<String, Object>() {{ put("category3", 2); put("value1", "総務"); }},
			    new HashMap<String, Object>() {{ put("category3", 3); put("value1", "IT営業"); }},
			    new HashMap<String, Object>() {{ put("category3", 4); put("value1", "ITエンジニア"); }},
			    new HashMap<String, Object>() {{ put("category3", 5); put("value1", "不動産スタッフ"); }},
			    new HashMap<String, Object>() {{ put("category3", 6); put("value1", "個人事業主"); }}
				);

		when(syainService.getSyain(01)).thenReturn(testSyain);
		when(syainService.getCompanyNameList()).thenReturn(companyNameList);
		when(syainService.getJobTypeList()).thenReturn(jobtypeList);
		
		String viewName = controller.ModifyPage(testSyainId, model);
		
		assertEquals("syain/modify", viewName);
		
		verify(model).addAttribute("syain", testSyain);
		verify(model).addAttribute("nyuusyaDate", "");
		verify(model).addAttribute("syozokuKaisya", companyNameList);
		verify(model).addAttribute("syokugyoKind", jobtypeList);
	}

}
