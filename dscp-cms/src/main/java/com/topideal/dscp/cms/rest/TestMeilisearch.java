package com.topideal.dscp.cms.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.Settings;
import com.meilisearch.sdk.model.SearchResult;

public class TestMeilisearch {
	
	
	
	public static void main(String[] args) throws Exception {
		Client client = new Client(new Config("http://10.10.100.15:31456/", ""));
		client.createIndex("TestIndex7", "title");
//		add(client);
//		searchAll(client);
		customSearch(client);
//		del(client);
//		update(client);
//		searchAll(client);
	  }
	
	//新增
	static void add(Client client){
		try {
			ArrayList array = new ArrayList() {{
//			  add(new JSONObject().put("title", "test111").put("genres",new JSONArray("[\"a1\",\"a2\"]")));
//			  add(new JSONObject().put("title", "test222").put("genres",new JSONArray("[\"a2\",\"a3\"]")));
//			  add(new JSONObject().put("title", "test333").put("genres",new JSONArray("[\"a3\",\"a4\"]")));
//			  add(new JSONObject().put("title", "test444").put("genres",new JSONArray("[\"a4\",\"a5\"]")));
//			  add(new JSONObject().put("title", "test555").put("genres",new JSONArray("[\"a5\",\"a6\"]")));
//			  add(new JSONObject().put("title", "test666").put("genres",new JSONArray("[\"a6\",\"a7\"]")));
			  add(new JSONObject().put("id", 20).put("title", "test777").put("genres",new JSONArray("[\"a7\",\"a8\"]")));
//			  add(new JSONObject().put("id", 3).put("title", "a").put("genres",new JSONArray("[\"b1\",\"b2\"]")));
			}};

		    String documents = array.toString();
		    

		    // An index is where the documents are stored.
		    Index index = client.index("TestIndex5");

		    // If the index 'movies' does not exist, Meilisearch creates it when you first add the documents.
		    index.addDocuments(documents); // => { "uid": 0 }
		    System.out.println("保存成功");
		} catch (Exception e) {
			 System.out.println("保存异常");
			e.printStackTrace();
		}
	}
	
	//查询 索引必须是设置了主键属性，或者是内容包含id字段，否则查询不出结果集
	static String searchAll(Client client) {
		String results = null;
		try {
			Index index = client.index("TestIndex5");
			results = index.getDocuments();
			System.out.println(results);
			System.out.println("查询成功");
		} catch (Exception e) {
			System.out.println("查询异常");
			e.printStackTrace();
		}
		return results;
	}
	//自定义查询 索引必须是设置了主键属性，或者是内容包含id字段，否则查询不出结果集
	static SearchResult customSearch(Client client) {
		SearchResult results = null;
		try {
			Index index = client.index("TestIndex6");
			//过滤
			com.alibaba.fastjson.JSONObject filter = new com.alibaba.fastjson.JSONObject();
//			filter.put("id", false);
			filter.put("title", "");
			if(filter != null && filter.size()>0) {//过滤条件不为空
	    		String[] filterArr = filter.keySet().toArray(new String[filter.size()-1]);
	    		//添加需要过滤的字段
	    		Settings settings = new Settings();
	    		settings.setFilterableAttributes(filterArr);
	    		index.updateSettings(settings);
	    	}
			//排序
			com.alibaba.fastjson.JSONObject sort = new com.alibaba.fastjson.JSONObject();
			sort.put("title", "");
			if(sort != null && sort.size()>0) {
				String[] sortArr = sort.keySet().toArray(new String[sort.size()-1]);
				Settings settings = new Settings();
	    		settings.setSortableAttributes(sortArr);
	    		index.updateSettings(settings);
			}
			
			results = index.search(
					  new SearchRequest(null)//查询的字符，也可在这里设置分页信息
//					  .setMatches(true)//是否返回匹配的信息，起始位置+长度
//					  .setAttributesToHighlight(new String[]{"title"})//如果字段中有匹配的值就高亮显示
					  //相当于where,但是必须将过滤的字段加到updateFilterableAttributesSettings
					  //且只能是比较运算符，可以是=, !=, >, >=, <, 或<= 只有数字类型才可以> <
					  .setFilter(new String[] {"title = test777"})//精准查询 可以包含 AND OR 但必须大写，数组默认and
					  .setSort(new String[] {"title:desc"})
					);
					System.out.println(results.getHits());
			System.out.println("查询成功");
		} catch (Exception e) {
			System.out.println("查询异常");
			e.printStackTrace();
		}
		return results;
	}
	
	//删除 按设置的主键精准删除，未设置主键的按id删除
	static void del(Client client) {
		try {
			List<String> list = new ArrayList<>();
//			list.add("2");
//			list.add("333");
			Index index = client.index("TestIndex6");
			index.deleteDocuments(list);//批量删除
			System.out.println("删除成功");
		} catch (Exception e) {
			System.out.println("删除异常");
			e.printStackTrace();
		}
	}
	//修改
	static void update(Client client) {
		try {
			ArrayList array = new ArrayList() {{
				  add(new JSONObject().put("title", "test777").put("test",new JSONArray("[\"b2\",\"b3\"]")));
				}};

		    String documents = array.get(0).toString();
			
			Index index = client.index("TestIndex6");
			//索引没有设置主键的情况下，更新会根据id的字段更新（有主键的，不会根据id更新）。
			index.updateDocuments(documents);//要更新的数据
			
			System.out.println("修改成功");
		} catch (Exception e) {
			System.out.println("修改异常");
			e.printStackTrace();
		}
	}
}
