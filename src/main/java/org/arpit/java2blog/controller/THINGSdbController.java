package org.arpit.java2blog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

		/*
		 * json=
		 * {"CRUD":{"操作":"追加","目标":"股票实时情报","条件":{"成交量":"27347564","总市值":"2.83541096635e+11","流通市值":"2.71482359264e+11","最低价":"9.66","名称":"�ַ�����","最高价":"9.8","开盘价":"9.77","涨跌幅":"-0.1034","成交金额":"266280883.0","前收盘":"9.67","股票代码":"600000","涨跌额":"-0.01","日期":"2018-12-27","成交笔数":"None","收盘价":"9.66","换手率":"0.0973"}}}
		 */
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
}
