package com.topideal.dscp.interfaces.meilisearch;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.Settings;
import com.meilisearch.sdk.model.SearchResult;

import lombok.extern.slf4j.Slf4j;

/**
 * MeilisearchUtils 工具类
 */
@Slf4j
@Component
public class MeilisearchUtils {
	@Value("${spring.meilisearch.host-url:}")
	private String hostUrl;
	@Value("${spring.meilisearch.api-key:}")
	private String apiKey;
	
	private static String hostUrls;
	private static String apiKeys;
	/**
	 * 将配置文件中的值赋值给静态属性
	 */
	@PostConstruct
    public void init() {
		hostUrls = this.hostUrl;
		apiKeys = this.apiKey;
    }
	/**
     * Client
     */
    private static Client client;

    // 创建 client
    private static Client getClient() {
        if (client == null) {
        	client = new Client(new Config(hostUrls, apiKeys));
        }
        return client;
    }

    /**
     * 以json的模式插入数据库
     * @param indexName   索引名称（类似表名）
     * @param json        内容
     * @throws Exception 
     */
    public void insertJson(String indexName, String json) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	index.addDocuments(json); 
    }
    
    /**
     * 根据条件查询
     * @param indexName   索引名称（类似表名）
     * @param str         需要查询的字符串（模糊查询like a%）
     * @param filter      过滤 key:字段名 value:过滤条件 如 key:id value:>=10 
     * @param sort        排序 key:字段名 value:排序方式 如 key:id value:desc
     * @return
     * @throws Exception
     */
    public SearchResult list(String indexName, String str,JSONObject filter,JSONObject sort) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	//添加查询条件
    	SearchRequest searchRequest = new SearchRequest(str);//模糊查询的字符
//    	searchRequest = searchRequest.setOffset(0).setLimit(100);//可在这里设置分页信息,默认从0开始，取20条
    	//过滤条件
    	if(filter != null && filter.size()>0) {
    		String[] filterKeyArr = filter.keySet().toArray(new String[filter.size()-1]);
    		//添加需要过滤的字段
    		index.updateFilterableAttributesSettings(filterKeyArr);
    		//遍历组装过滤的条件
    		List<String> list = new ArrayList<>();
    		for(String key:filter.keySet()) {
    			String filterStr= filter.getString(key).replace("or", "OR").replace("and", "AND");
    			if(filterStr != null && !"".equals(filterStr)) {
    				list.add(key + filterStr);
    			}
    		}
    		String[] filterArr = list.toArray(new String[list.size()-1]);
    		if(filterArr != null && filterArr.length>0) {
        		/* 相当于where,但是必须将过滤的字段加到updateFilterableAttributesSettings
        		 * 只能是比较运算符，可以是=, !=, >, >=, <, 或<=;
        		 * 只有数字类型才可以> <否则可能报错或不生效
        		 * 精准匹配 元素中可以包含 AND OR 但必须大写，数组元素之间是AND
        		 */
        		searchRequest = searchRequest.setFilter(filterArr);
        	}
    	}
    	//排序
    	if(sort != null && sort.size()>0) {
    		String[] sortKeyArr = sort.keySet().toArray(new String[sort.size()-1]);
			Settings settings = new Settings();
    		settings.setSortableAttributes(sortKeyArr);
    		index.updateSettings(settings);
    		//遍历组装排序的条件
    		List<String> list = new ArrayList<>();
    		for(String key:sort.keySet()) {
    			String sortStr= sort.getString(key);
    			if(sortStr != null && !"".equals(sortStr)) {
    				list.add(key + ":" +sortStr.toLowerCase());
    			}
    		}
    		String[] sortArr = list.toArray(new String[list.size()-1]);
    		searchRequest.setSort(sortArr);
    	}
    	//执行查询操作
		return index.search(searchRequest);
    }
    
    /**
     * 以json字符串更新
     * @param indexName   索引名称（类似表名）
     * @param json        内容
     * @throws Exception 
     */
    public void updateJson(String indexName, String json) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	//索引没有设置主键的情况下，会根据id的字段更新（有主键的，不会根据id更新）。没有主键也没有id更新无效
    	index.updateDocuments(json); 
    }
    
    /**
     * 批量删除
     * @param indexName
     * @param list 为空时删除无效
     * @throws Exception
     */
    public void del(String indexName, List list) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	//按索引设置的主键删除，未设置主键的按id删除。没有主键也没有id删除无效
    	index.deleteDocuments(list);
    }
    
    /**
     * 批量全部
     * @param indexName
     * @throws Exception
     */
    public void delAll(String indexName) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	index.deleteAllDocuments();
    }
    
    /**
     * 根据条件查询
     * @param indexName   索引名称（类似表名）
     * @param str         需要查询的字符串（模糊查询like a%）
     * @return
     * @throws Exception
     */
    public SearchResult list(String indexName, String str) throws Exception {
    	Index index = MeilisearchUtils.getClient().index(indexName);
    	//添加查询条件
    	SearchRequest searchRequest = new SearchRequest(str);//模糊查询的字符
    	//执行查询操作
		return index.search(searchRequest);
    }
}
