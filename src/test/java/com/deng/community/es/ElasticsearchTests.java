package com.deng.community.es;


import com.deng.community.entity.DiscussPost;
import com.deng.community.mapper.DiscussPostMapper;
import com.deng.community.mapper.elasticsearch.DiscussPostRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.SearchResultMapper;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussMapper;

    @Autowired
    private DiscussPostRepository discussRepository;

    @Autowired(required = false)
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testInsert() {
        discussRepository.save(discussMapper.selectDiscussPostById(241));
        discussRepository.save(discussMapper.selectDiscussPostById(242));
        discussRepository.save(discussMapper.selectDiscussPostById(243));
    }

    @Test
    public void testInsertList() {
        discussRepository.saveAll(discussMapper.selectDiscussPosts(101, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(102, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(103, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(111, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(112, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(131, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(132, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(133, 0, 100,0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(134, 0, 100,0));
    }

    @Test
    public void testUpdate() {
        DiscussPost post = discussMapper.selectDiscussPostById(231);
        post.setContent("我是新人,使劲灌水.");
        discussRepository.save(post);
    }

    @Test
    public void testDelete() {
        // discussRepository.deleteById(231);
        discussRepository.deleteAll();
    }

    @Test
    public void testSearchByRepository() {
        //NativeSearchQueryBuilder query = new NativeSearchQueryBuilder().
        NativeSearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        //discussRepository.fin
        // elasticTemplate.queryForPage(searchQuery, class, SearchResultMapper)
        // 底层获取得到了高亮显示的值, 但是没有返回.
        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder, DiscussPost.class);

        System.out.println(search.getMaxScore());
        System.out.println(search.getTotalHits());
        List<DiscussPost> discussPosts = new ArrayList<>();
        for (SearchHit<DiscussPost> discussPost : search) {
            discussPosts.add(discussPost.getContent());
        }

        discussPosts.forEach(item -> {
                    System.out.println(item);
                }
        );
    }

    @Test
    public void testSearchByTemplate() {
        NativeSearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.multiMatchQuery("陶", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        //查询
        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder, DiscussPost.class);
        // 得到查询返回结果
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        // 返回结果集
        List<DiscussPost> discussPosts = new ArrayList<>();
        for (SearchHit<DiscussPost> searchHit : searchHits) {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();

            String highlightTitle = highlightFields.get("title") == null ?
                    searchHit.getContent().getTitle() : highlightFields.get("title").get(0);
            searchHit.getContent().setTitle(highlightTitle);

            String highlightContent = highlightFields.get("content") == null ?
                    searchHit.getContent().getTitle() : highlightFields.get("content").get(0);
            searchHit.getContent().setTitle(highlightContent);

            discussPosts.add(searchHit.getContent());
        }

        discussPosts.forEach(System.out::println);




/*        Page<DiscussPost> page = elasticTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }

                List<DiscussPost> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    DiscussPost post = new DiscussPost();

                    String id = hit.getSourceAsMap().get("id").toString();
                    post.setId(Integer.valueOf(id));

                    String userId = hit.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.valueOf(userId));

                    String title = hit.getSourceAsMap().get("title").toString();
                    post.setTitle(title);

                    String content = hit.getSourceAsMap().get("content").toString();
                    post.setContent(content);

                    String status = hit.getSourceAsMap().get("status").toString();
                    post.setStatus(Integer.valueOf(status));

                    String createTime = hit.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.valueOf(createTime)));

                    String commentCount = hit.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.valueOf(commentCount));

                    // 处理高亮显示的结果
                    HighlightField titleField = hit.getHighlightFields().get("title");
                    if (titleField != null) {
                        post.setTitle(titleField.getFragments()[0].toString());
                    }

                    HighlightField contentField = hit.getHighlightFields().get("content");
                    if (contentField != null) {
                        post.setContent(contentField.getFragments()[0].toString());
                    }

                    list.add(post);
                }

                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), response.getAggregations(), response.getScrollId(), hits.getMaxScore());
            }
        });

        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        for (DiscussPost post : page) {
            System.out.println(post);
        }*/
    }

}
