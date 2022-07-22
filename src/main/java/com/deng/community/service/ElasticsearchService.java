package com.deng.community.service;


import com.deng.community.entity.DiscussPost;
import com.deng.community.entity.SearchResult;
import com.deng.community.mapper.elasticsearch.DiscussPostRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {

    @Autowired
    private DiscussPostRepository discussRepository;

    @Autowired(required = false)
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void saveDiscussPost(DiscussPost post) {
        discussRepository.save(post);
    }

    public void deleteDiscussPost(int id) {
        discussRepository.deleteById(id);
    }

    public SearchResult<DiscussPost> searchDiscussPost(String keyword, int current, int limit) {
        NativeSearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
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

        return new SearchResult<>(search.getTotalHits(), discussPosts);
    }

}
