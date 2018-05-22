
建立对Elasticsearch的初步的认识可以参考：[https://mp.weixin.qq.com/s/stC_xMP1n3aQ-0ZNAc3eQA](https://mp.weixin.qq.com/s/stC_xMP1n3aQ-0ZNAc3eQA)
官方的中文文档参考： [https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html](https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html)
## 版本
 - Spring Boot： 1.4.7
 - Spring Data Elasticsearch  2.0.11
 - Elasticsearch server 2.4

## 代码
https://github.com/choelea/spring-data-elasticsearch-quick-start
## 配置
```
spring.data.elasticsearch.repositories.enabled = true
spring.data.elasticsearch.cluster-nodes : 192.168.1.99:9300
```
## 定义Document
参考： `com.joe.springdataelasticsearch.document.ProductDoc` . 定义文档需要注意必须有个id字段或者通过注解指定一个id字段，只有在有ID得情况下，文档才可以被更新。 否则会抛出异常：`No id property found for class com.joe.springdataelasticsearch.document.ProductDoc`

当前版本下需要指定Field的type，否则也会报错。
> 修改FieldType 会导致无法通过程序启动异常，需要手动删除后创建索引。 比如: 原有的type字段的FieldType是Long，改成String后会出现类似如下错误：`mapper [type] of different type, current_type [long], merged_type [string]`

## 创建索引
系统启动后，创建索引和创建/更新mapping

```
elasticsearchTemplate.deleteIndex(ProductDoc.class);
elasticsearchTemplate.createIndex(ProductDoc.class);
elasticsearchTemplate.putMapping(ProductDoc.class);
```
启动后可以通过`http://192.168.1.99:9200/product-index/_mapping/main/` 来查看mapping。
## 索引文档
elasticsearch 是通过PUT接口来索引文档。[https://www.elastic.co/guide/cn/elasticsearch/guide/current/index-doc.html](https://www.elastic.co/guide/cn/elasticsearch/guide/current/index-doc.html)。 在使用Spring Data Elasticsearch的的时候，我们可以很方便的通过防JPA Repository的方式来操作;  `ProductDocRespository.save(ProductDoc doc)`  来索引和更新文档。 
```
public interface ProductDocRespository extends ElasticsearchRepository<ProductDoc, Long>
```
参考`com.joe.springdataelasticsearch.listner.ContextRefreshedListener` 来查看索引测试文档数据。

## 测试数据
![spring-data-elasticsearch-productdocs](http://tech.jiu-shu.com/Elastic-Technologies/spring-data-elasticsearch-productdocs.png)

## 全文检索
查询主要解决：
* 多个字段搜索查询使用布尔匹配的方式， 参考官方说明 [布尔匹配](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_how_match_uses_bool.html)
* 不同字段的权重设置，采用设置Boost方式， 参考： [查询语句提升权重](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_boosting_query_clauses.html)
* 聚合结果集 

具体代码参考如下：
```
public Page<ProductDoc> search(String keyword, Boolean isSelfRun, Pageable pageable) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (StringUtils.isNotEmpty(keyword)) {
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._name, keyword).boost(3)); // 给name字段更高的权重
			queryBuilder.should(QueryBuilders.matchQuery(ProductDoc._description, keyword));   // description 默认权重 1
			queryBuilder.minimumNumberShouldMatch(1); // 至少一个should条件满足
		}

		if (isSelfRun!=null && isSelfRun) {
			queryBuilder.must(QueryBuilders.matchQuery(ProductDoc._isSelfRun, Boolean.TRUE)); // 精准值条件查询
		}
		
				
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
				.withPageable(pageable).build();
		LOGGER.info("\n search(): searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
		return productDocRespository.search(searchQuery);
	}
	```


## 测试查询
1. http://localhost:8080/products?keyword=huawei 
2. http://localhost:8080/products?keyword=iphone 通过对iphone的搜索可以验证boost值得效果
3. http://localhost:8080/products?keyword=iphone&isSelfRun=true  验证精准值匹配效果








