package com.java.web.util;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Repository
public class CommonDao implements CommonDaoInterface {
	@Autowired
	SqlSession sqlSession; 
	

	@Override
	public HashMap<String, Object> commonDB(HashMap<String, Object> paramMap) {
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		String queryType = paramMap.get("queryType").toString();
		String queryTarget=paramMap.get("queryTarget").toString();
		HashMap<String,Object> params = (HashMap<String, Object>) paramMap.get("params");
		if("selectone".equals(queryType)) {
			resultMap.put("result", sqlSession.selectOne(queryTarget,params));
		} else if("selectList".equals(queryType)) {
			resultMap.put("result", sqlSession.selectList(queryTarget, params));
		} else if("insert".equals(queryType)) {
			resultMap.put("result", sqlSession.insert(queryTarget, params));
			System.out.println(resultMap.get("result"));
		} else if("update".equals(queryType)) {
			resultMap.put("result", sqlSession.update(queryTarget, params));
		} else if("delete".equals(queryType)) {
			resultMap.put("result", sqlSession.delete(queryTarget, params));
		}
		return resultMap;
	}
}
