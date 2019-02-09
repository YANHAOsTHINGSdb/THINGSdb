package org.arpit.java2blog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.arpit.java2blog.bean.Country;
import org.arpit.java2blog.service.CountryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import stage3.REL.Relation;
import stage3.listening.ThinkAndProgrammer;
import stage3.things.multiConditionCalc.CRUDer;

@RestController
public class THINGSdbController {

	Relation relation;

	@RequestMapping(value = "/listening", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public List listening(@RequestBody String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		ThinkAndProgrammer thinkAndProgrammer = new ThinkAndProgrammer();

		return (List)thinkAndProgrammer.thinking_byJSONMap(map);

	}

	/**
	 * RestfulWebService.数据登録
	 * 由于登陆的信息不可预测，所以不能用DTO，只能接收JSON原始数据。
	 * 然后再转成MAP格式。
	 * @param country
	 * @return
	 */
	@RequestMapping(value = "/addInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public List addInfomation(@RequestBody String json) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}


		CRUDer cRUDer = new CRUDer("addInfomation()");
		return cRUDer.add(map);
	}

	/**
	 * 複数DAO
	 * (多条件检索)
	 * @param country
	 * @return
	 */
	@RequestMapping(value = "/multiConditionCalc", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	//public List search(@RequestBody 計算情報DTO 計算情報dto) {
	public List search(@RequestBody String json) {


		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		CRUDer cRUDer = new CRUDer("search()");
		return cRUDer.search(map);

	}

	/**
	 * 複数DAO
	 * (多条件检索)
	 * @param country
	 * @return
	 */
	@RequestMapping(value = "/relation", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	//public List search(@RequestBody 計算情報DTO 計算情報dto) {
	public List relation(@RequestBody String json){
		//------------------------------------------------------------------------
		// 从UI入力的暂时有以下几种情况
		// UI--->RLA
		// 		  |--->NLP
		//		  |			{NL: }						// 人机对话
		//		  |--->THINGs
		//		  |			{{条件: },{目標: }}			// 画面转入
		//		  |
		//		  |--->词条CRUDs
		//		  |			{{GW: }}					// NLP转来的
		//		  |--->PRO
		//					{{SDP: },{from: },{to: }}	// 程序直接走
		//------------------------------------------------------------------------

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

	try {
		relation = new Relation("relation");
		// 处理入口
		return relation.process(map);

	}catch(Throwable e) {
		System.out.println(e.getMessage());
	}
	return null;

	}

	CountryService countryService = new CountryService();

	@RequestMapping(value = "/countries", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Country> getCountries() {
		List<Country> listOfCountries = countryService.getAllCountries();
		return listOfCountries;
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Country getCountryById(@PathVariable int id) {
		return countryService.getCountry(id);
	}

	@RequestMapping(value = "/countries", method = RequestMethod.PUT, headers = "Accept=application/json")
	public Country updateCountry(@RequestBody Country country) {
		return countryService.updateCountry(country);

	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteCountry(@PathVariable("id") int id) {
		countryService.deleteCountry(id);

	}
}
