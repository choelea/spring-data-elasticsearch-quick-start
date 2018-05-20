建立对Elasticsearch的初步的认识可以参考：[https://mp.weixin.qq.com/s/stC_xMP1n3aQ-0ZNAc3eQA](https://mp.weixin.qq.com/s/stC_xMP1n3aQ-0ZNAc3eQA)
## 版本
 - Spring Boot： 1.4.7
 - Spring Data Elasticsearch  2.0.11
 - Elasticsearch server 2.4

## 配置
```
spring.data.elasticsearch.repositories.enabled = true
spring.data.elasticsearch.cluster-nodes : 192.168.1.99:9300
```
## 定义Document
参考： `com.joe.springdataelasticsearch.document.ProductDoc`
当前版本下需要指定Field的type，否则会报错。
> 修改FieldType 会导致无法通过程序启动异常，需要手动删除后创建索引。 比如: 原有的type字段的FieldType是Long，改成String后会出现类似如下错误：`mapper [type] of different type, current_type [long], merged_type [string]`

## 创建索引
系统启动后，创建索引和创建/更新mapping

```
elasticsearchTemplate.deleteIndex(ProductDoc.class);
elasticsearchTemplate.createIndex(ProductDoc.class);
elasticsearchTemplate.putMapping(ProductDoc.class);
```
启动后可以通过`http://192.168.1.99:9200/product-index/_mapping/main/` 来查看mapping。
## 