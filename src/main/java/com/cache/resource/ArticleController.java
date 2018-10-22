package com.cache.resource;

import com.cache.common.Result;
import com.cache.common.ResultGenerator;
import com.cache.domain.Article;
import com.cache.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);// 日志文件

    /**
     * 查找相应的数据集合
     * 未实现
     *
     * @param page
     * @param rows
     * @param article
     * @param response
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/datagrid", method = RequestMethod.POST)
//    public String list(
//            @RequestParam(value = "page", required = false) String page,
//            @RequestParam(value = "rows", required = false) String rows,
//            Article article, HttpServletResponse response) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (page != null && rows != null) {
//            PageBean pageBean = new PageBean(Integer.parseInt(page),
//                    Integer.parseInt(rows));
//            map.put("start", pageBean.getStart());
//            map.put("size", pageBean.getPageSize());
//        }
//        if (article != null) {
//            map.put("articleTitle",
//                    StringUtil.formatLike(article.getArticleTitle()));
//        }
//        List<Article> articleList = articleService.findArticle(map);
//        Long total = articleService.getTotalArticle(map);
//        JSONObject result = new JSONObject();
//        JSONArray jsonArray = JSONArray.fromObject(articleList);
//        result.put("rows", jsonArray);
//        result.put("total", total);
//        log.info("request: article/list , map: " + map.toString());
//        ResponseUtil.write(response, result);
//        return null;
//    }
//
//    /**
//     * 查找相应的数据集合
//     *
//     * @param page
//     * @param rows
//     * @param article
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    @ResponseBody
//    public Result list(
//            @RequestParam(value = "page", required = false) String page,
//            @RequestParam(value = "rows", required = false) String rows,
//            Article article) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (page != null && rows != null) {
//            PageBean pageBean = new PageBean(Integer.parseInt(page),
//                    Integer.parseInt(rows));
//            map.put("start", pageBean.getStart());
//            map.put("size", pageBean.getPageSize());
//        }
//        if (article != null) {
//            map.put("articleTitle",
//                    StringUtil.formatLike(article.getArticleTitle()));
//        }
//        List<Article> articleList = articleService.findArticle(map);
//        Long total = articleService.getTotalArticle(map);
//
//        Result result = ResultGenerator.genSuccessResult();
//        Map data = new HashMap();
//        data.put("rows", articleList);
//        data.put("total", total);
//        result.setData(data);
//        return result;
//    }

    /**
     * 添加
     *
     * @param article
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Article article)
            throws Exception {
        int resultTotal = 0;

        article.setArticleCreateDate(LocalDateTime.now());

        resultTotal = articleService.addArticle(article);

        log.info("request: article/save , " + article.toString());

        if (resultTotal > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    /**
     * 修改
     * 未实现
     *
     * @param article
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Result update(@RequestBody Article article)
            throws Exception {
        int resultTotal = 0;

        resultTotal = articleService.updateArticle(article);

        log.info("request: article/update , " + article.toString());

        if (resultTotal > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable("ids") String ids) throws Exception {
        if (ids.length() > 20) {
            return ResultGenerator.genFailResult("ERROR");
        }
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            articleService.deleteArticle(idsStr[i]);
        }

        log.info("request: article/delete , ids: " + ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据id查找
     * /{id:.+} 使用正则表达式，表示http://localhost:8080/articles/1063访问，这里与{id}并无两样，但是可以通过正则规定id的输入格式
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ResponseBody
    public Result findById(@PathVariable("id") String id) throws Exception {
        Article article = articleService.findById(id);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(article);
        log.info("request: article/findById");
        return result;
    }

    /**
     * 根据id查找
     * 使用另一个cache manager
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findById2/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result findById2(@PathVariable("id") String id) throws Exception {
        Article article = articleService.findById2(id);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(article);
        log.info("request: article/findById2");
        return result;
    }
}