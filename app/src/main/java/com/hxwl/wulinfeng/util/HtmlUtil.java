package com.hxwl.wulinfeng.util;

import com.hxwl.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/21.
 */
public class HtmlUtil {
        private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
         * @param htmlStr
         * @return
         *  删除Html标签
         */
        public static String delHTMLTag(String htmlStr) {
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
            Matcher m_space = p_space.matcher(htmlStr);
            htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
            return htmlStr.trim(); // 返回文本字符串
        }

        public  static String getTextFromHtml(String htmlStr){
            htmlStr = delHTMLTag(htmlStr);
            htmlStr = htmlStr.replaceAll("&nbsp;", "");
//            htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
            return htmlStr;
        }



    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgStr(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * 把网页内容中的<p><br></p> 替换为<br/>
     * @param s
     * @return
     */
    public static String pbrTobr(String s){
        if(StringUtils.isEmpty(s)){
            return "";
        }
        String str = "<p><br></p>";
        String string = s.replaceAll(str,"<br/>");
        return string;
    }
}

