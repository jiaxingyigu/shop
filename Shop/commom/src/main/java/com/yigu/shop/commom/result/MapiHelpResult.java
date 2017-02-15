package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/1/16.
 */
public class MapiHelpResult extends MapiBaseResult{

    private List<MapiArticleResult> article;

    public List<MapiArticleResult> getArticle() {
        return article;
    }

    public void setArticle(List<MapiArticleResult> article) {
        this.article = article;
    }
}
