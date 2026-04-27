package com.kyh.system.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.kyh.system.model.Syain;
import com.kyh.system.service.SyainService;


@Controller
public class SyainController {
	
	@Autowired
	public SyainService syainService;

	@GetMapping("/")
    public RedirectView redirectToSyain() {
        return new RedirectView("/syain/");
    }
	
	
	@RequestMapping(value = "/syain", method = {RequestMethod.POST, RequestMethod.GET})
	public String SyainPage() {
		return "syain/syain";
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
	public String RegisterPage() {
		return "syain/register";
	}
	
	@RequestMapping(value = "/modify", method = {RequestMethod.POST, RequestMethod.GET})
	public String ModifyPage(@RequestParam int syainId, Model model) {
		Syain syain = syainService.getSyain(syainId);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedNyuusyaDate = sdf.format(syain.getNyuusyaDate());
		
		model.addAttribute("syain", syain);
		model.addAttribute("nyuusyaDate", formattedNyuusyaDate);
		model.addAttribute("syozokuKaisya", syainService.getComapnyNameList());
		model.addAttribute("syokugyoKind", syainService.getJobTypeList());
		
		return "syain/modify";
	}
	
	@RequestMapping(value = "/done", method = {RequestMethod.POST, RequestMethod.GET})
	public String DonePage() {
		return "syain/done";
	}
	
	@RequestMapping(value = "/error", method = {RequestMethod.POST, RequestMethod.GET})
	public String ErrorPage() {
		return "syain/error";
	}
	
	@GetMapping(value = "/api/get-company")
	@ResponseBody
	public List<Map<String, Object>> getCompanyList() {
		return syainService.getComapnyNameList();
	}
	
	@GetMapping(value = "/api/get-job-type")
	@ResponseBody
	public List<Map<String, Object>> getJobTypeList() {		
		return syainService.getJobTypeList();
	}
	
	@GetMapping(value = "/api/get-employees")
	@ResponseBody
	public List<Syain> getSyainList(
			@RequestParam(value = "company", required = false) int company,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "jobType", required = false) int jobType,
			@RequestParam(value = "active", required = false) boolean active,
			@RequestParam(value = "inactive", required = false) boolean inactive) {
		return syainService.getSyainList(company, name, jobType, active, inactive);
	}
	
	@GetMapping(value = "/api/get-os")
	@ResponseBody
	public List<String> getOSList() {
		return syainService.getOSList();
	}
	
	@GetMapping(value = "/api/delete-user")
	@ResponseBody
	public boolean deleteSyain(@RequestParam int syainId) {
		int num = 0;
		
		try {
			num = syainService.deleteSyain(syainId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return num == 1 ? true : false;
	}
	
	@PostMapping(value = "/api/add-user")
	@ResponseBody
	public ResponseEntity<?> addSyain(@RequestBody Syain syain) {
		System.out.println("add진입");
		
		syain.setDeleteFlag(0);
		
		if (syainService.addSyain(syain)) {
			System.out.println("add성공");
			return ResponseEntity.ok().build();
		}
		
		System.out.println("add실패");
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value = "/api/modify-user")
	@ResponseBody
	public boolean updateSyain(@ModelAttribute Syain syain) {
		int num = 0;
		
		try {
			num = syainService.updateSyain(syain);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return num == 1 ? true : false;
	}
}
