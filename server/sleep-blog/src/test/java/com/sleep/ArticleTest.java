//package com.sleep;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.sleep.constants.PatternConstants;
//import com.sleep.domain.entity.Article;
//import com.sleep.mapper.ArticleMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @author SleepWalker
// * @version 1.0
// * @date 2023/8/20 18:27
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ArticleTest {
//    @Autowired
//    private ArticleMapper articleMapper;
//
//    @Test
//    public void doubleLink() {
//        Article article = articleMapper.selectById(5);
//        for (int i = 0; i < 10; i++) {
//            System.out.println("-------------------------");
//        }
//        System.out.println("==========================替换前====================");
//        System.out.println(article.getContent());
//        System.out.println("====================================================");
//
//        //查询双链语法,替换
//        Pattern pattern = Pattern.compile(PatternConstants.BI_DIRECTIONAL_LINKS);
//        Matcher matcher = pattern.matcher(article.getContent());
//
//        StringBuffer stringBuffer = new StringBuffer();
//        List<String> outDoubleLink = new ArrayList<>(); //出链
//        while (matcher.find()) {
//            //根据双链内的文章名 查询id
//            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
//            String group = matcher.group(1);
//            outDoubleLink.add(group);
//
//            wrapper.eq(Article::getTitle, group);
//            Article one = articleMapper.selectOne(wrapper);
//            if(one == null)
//                continue;
//            //替换
//            matcher.appendReplacement(stringBuffer, "<p><a class=\"tooltip\" href=\"http://localhost:5173/ArticleDetail/" + one.getId() + "\">" +
//                    "<span>$1</span>" +
//                    "<span class=\"tooltiptext\"><iframe src=\"http://localhost:5173/OnlyArticle/" + one.getId() + "\" width=\"800\" height=\"450\"></iframe></span>" +
//                    "</a></p>");
//        }
//        matcher.appendTail(stringBuffer);
//
//        System.out.println("==========================替换后====================");
//        System.out.println("替换后: " + stringBuffer);
//        System.out.println("====================================================");
//        System.out.println(outDoubleLink);
//    }
//}
