package com.kikop;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyEs7DemoApplication implements CommandLineRunner {


    public void run(String... args) throws Exception {
        System.out.println("------MyEs7DemoApplication start------");
        testTagFilter();
    }

    /*
    * GET /myindex_tag/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "term": {
            "obid": {
              "value": "test_002"
            }
          }
        },
        {
          "wildcard": {
            "tags": "*keyword2*"
          }
        }
      ]
    }
  }
}
    * */
    private QueryBuilder buildQueryBuilder() {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("obid", "test_002"));
        // 2. 构建wildcard查询
        WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("tags", "*keyword2*");

        boolQueryBuilder.must(wildcardQuery);
        return boolQueryBuilder;
    }

    private void testTagFilter() {

        // 1. 创建RestHighLevelClient实例
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        try {
            // 2. 构建wildcard查询
            //WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("tags", "elasti*");
            QueryBuilder queryBuilder = buildQueryBuilder();

            // 3. 创建SearchRequest
            SearchRequest searchRequest = new SearchRequest("myindex_tag");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);

            searchRequest.source(sourceBuilder);

            // 4. 执行查询
            System.out.println(searchRequest.source());
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            // 5. 处理查询结果
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭客户端
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(MyEs7DemoApplication.class, args);
    }

}
