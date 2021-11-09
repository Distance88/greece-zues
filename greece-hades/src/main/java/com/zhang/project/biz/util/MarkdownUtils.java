package com.zhang.project.biz.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @author Yaohang Zhang
 * @ClassName MarkdownUtils
 * @description TODO
 * @date 2021-10-15 13:38
 */
public class MarkdownUtils {

    public static String transferMarkDownToHtml (String content){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
