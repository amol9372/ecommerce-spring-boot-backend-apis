package org.ecomm.ecommproduct.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.util.StringUtils;

@Slf4j
public class ElasticSearchQueryBuilder {

  public static NativeQuery createSearchQuery(SearchRequest request) {

    BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

    setMatchQuery(request, boolQueryBuilder);
    setFilters(request, boolQueryBuilder);

    Query finalQuery = Query.of(bool -> bool.bool(boolQueryBuilder.build()));

    SortOptions sortOptions = getSortOptions(request);

    NativeQuery nativeQuery =
        NativeQuery.builder().withQuery(finalQuery).withSort(List.of(sortOptions)).build();

    setPagination(request, nativeQuery);

    log.info("Elasticsearch Product search query ::: {}", nativeQuery.getQuery());

    return nativeQuery;
  }

  private static void setPagination(SearchRequest request, NativeQuery nativeQuery) {
    if (Objects.nonNull(request.getPagination())) {
      nativeQuery.setPageable(
          Pageable.ofSize(request.getPagination().getPageSize())
              .withPage(request.getPagination().getPageNo()));
    }
  }

  private static SortOptions getSortOptions(SearchRequest request) {
    SortOptions.Builder sortOptionsBuilder = new SortOptions.Builder();

    Utility.stream(request.getSorts())
        .forEach(
            sort ->
                sortOptionsBuilder.field(
                    f ->
                        f.field(sort.getField())
                            .order(
                                sort.getOrder().equals("asc") ? SortOrder.Asc : SortOrder.Desc)));

    return sortOptionsBuilder.build();
  }

  private static void setMatchQuery(SearchRequest request, BoolQuery.Builder bq) {
    if (StringUtils.hasLength(request.getSearchTerm())) {
      bq.must(
          m ->
              m.match(
                  matchQuery ->
                      matchQuery.query(q -> q.stringValue(request.getSearchTerm())).field("name")));
    }
  }

  private static void setFilters(SearchRequest request, BoolQuery.Builder bq) {
    Utility.stream(request.getFilters())
        .forEach(
            filter -> {
              TermsQueryField valueTerms =
                  new TermsQueryField.Builder()
                      .value(
                          filter.getValues().stream()
                              .map(String::toLowerCase)
                              .map(FieldValue::of)
                              .toList())
                      .build();

              TermsQuery termsQuery =
                  TermsQuery.of(t -> t.field(filter.getField()).terms(valueTerms));
              bq.filter(f -> f.terms(termsQuery));
            });
  }

  private static Query multipleTermsQuery(TermsQueryField terms) {
    return Query.of(
        q ->
            q.bool(
                b ->
                    b.filter(f -> f.terms(t -> t.field("features.Brand").terms(terms)))
                        .filter(f -> f.terms(t -> t.field("category.name").terms(terms)))));
  }

  private static Query getSingleTerm(JsonData data) {
    return Query.of(
        q ->
            q.bool(
                b ->
                    b.filter(
                        f -> f.term(t -> t.field("features.Brand").value(v -> v.anyValue(data))))));
  }
}

/*
       // search query
       MatchQuery matchQuery = new MatchQuery.Builder().queryName("M2").field("name").build();

       // filter query
       TermQuery termQuery1 = TermQuery.of(t -> t.field("f1").value("v1"));
       TermQuery termQuery2 = TermQuery.of(t -> t.field("f2").value("v2"));

       Query modularQuery =
           Query.of(
               q ->
                   q.bool(
                       bool ->
                           bool.must(match -> match.match(matchQuery))
                               .filter(filter -> filter.term(termQuery1))
                               .filter(filter -> filter.term(termQuery2))));
*/
