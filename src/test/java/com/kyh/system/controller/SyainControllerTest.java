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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

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
	public void RedirectSyainTest() {
		RedirectView result = controller.redirectToSyain();
		assertEquals(result.getUrl(), "/syain/");
	}
	
	@Test
	public void SyainPageTest() {
		String result = controller.SyainPage();
		assertEquals(result, "syain/syain");
	}
	
	@Test
	public void RegisterPageTest() {
		String result = controller.RegisterPage();
		assertEquals(result, "syain/register");
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
	
	@Test
	public void DonePageTest() {
		String result = controller.DonePage();
		assertEquals(result, "syain/done");
	}
	
	@Test
	public void ErrorPageTest() {
		String result = controller.ErrorPage();
		assertEquals(result, "syain/error");
	}
	
	@Test
	public void TestGetCompanyList() {
		List<Map<String, Object>> companyNameList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category2", 1); put("value1", "株式会社ブライトスター"); }},
				new HashMap<String, Object>() {{ put("category2", 2); put("value1", "株式会社トップクラウド"); }}
				);
		
		when(syainService.getCompanyNameList()).thenReturn(companyNameList);
		
		assertEquals(controller.getCompanyList(), companyNameList);
	}
	
	@Test
	public void TestJobTypeList() {
		List<Map<String, Object>> jobtypeList = Arrays.asList(
				new HashMap<String, Object>() {{ put("category3", 1); put("value1", "役員"); }},
			    new HashMap<String, Object>() {{ put("category3", 2); put("value1", "総務"); }},
			    new HashMap<String, Object>() {{ put("category3", 3); put("value1", "IT営業"); }},
			    new HashMap<String, Object>() {{ put("category3", 4); put("value1", "ITエンジニア"); }},
			    new HashMap<String, Object>() {{ put("category3", 5); put("value1", "不動産スタッフ"); }},
			    new HashMap<String, Object>() {{ put("category3", 6); put("value1", "個人事業主"); }}
				);
		
		when(syainService.getJobTypeList()).thenReturn(jobtypeList);
		
		assertEquals(controller.getJobTypeList(), jobtypeList);
	}
	
	@Test
	public void TestGetSyainList() {
		int company = 1;
		String name = "";
		int jobType = 2;
		boolean active = true;
		boolean inactive = false;
		
		Syain syain1 = new Syain();
		
		Syain syain2 = new Syain();
		List<Syain> expected = Arrays.asList(syain1, syain2);
		
		when(syainService.getSyainList(company, name, jobType, active, inactive)).thenReturn(expected);
		
		List<Syain> result = controller.getSyainList(company, name, jobType, active, inactive);
		
		assertEquals(result, expected);
	}
	
	@Test
	public void TestGetOSList() {
		List<String> expected = Arrays.asList("Windows", "Linux");
		
		when(syainService.getOSList()).thenReturn(expected);
		
		List<String> result = controller.getOSList();
		
		assertEquals(expected, result);
	}
	
	@Test
	public void TestDeleteSyain_ReturnTrue() {
		int syainId = 1;
		
		when(syainService.deleteSyain(syainId)).thenReturn(1);
		
		boolean result = controller.deleteSyain(syainId);
		
		assertTrue(result);
	}
	
	@Test
	public void TestDeleteSyain_ReturnFalse() {
		int syainId = 1;
		
		when(syainService.deleteSyain(syainId)).thenReturn(0);
		
		boolean result = controller.deleteSyain(syainId);
		
		assertFalse(result);
	}
	
	@Test
	public void TestDeleteSyain_Exception() {
		int syainId = 1;
		
		when(syainService.deleteSyain(syainId)).thenThrow(new RuntimeException());
		
		boolean result = controller.deleteSyain(syainId);
		
		assertFalse(result);
	}
	
	@Test
	public void TestAddSyain_Success() {
		Syain syain = new Syain();
		
		when(syainService.addSyain(syain)).thenReturn(true);
		
		ResponseEntity<?> response = controller.addSyain(syain);
		
		assertEquals(syain.getDeleteFlag(), 0);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void TestAddSyain_Fail() {
		Syain syain = new Syain();
		
		when(syainService.addSyain(syain)).thenReturn(false);
		
		ResponseEntity<?> response = controller.addSyain(syain);
		
		assertEquals(syain.getDeleteFlag(), 0);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void TestUpdateSyain_ReturnTrue() {
		Syain syain = new Syain();
		
		when(syainService.updateSyain(syain)).thenReturn(1);
		
		boolean result = controller.updateSyain(syain);
		
		assertTrue(result);
	}
	
	@Test
	public void TestUpdateSyain_ReturnFalse() {
		Syain syain = new Syain();
		
		when(syainService.updateSyain(syain)).thenReturn(0);
		
		boolean result = controller.updateSyain(syain);
		
		assertFalse(result);
	}
	
	@Test
	public void TestUpdateSyain_Exception() {
		Syain syain = new Syain();
		
		when(syainService.updateSyain(syain)).thenThrow(new RuntimeException());
		
		boolean result = controller.updateSyain(syain);
		
		assertFalse(result);
	}
}
