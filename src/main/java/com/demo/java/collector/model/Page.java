package com.demo.java.collector.model;

import com.demo.java.collector.net.HttpResponse;
import com.demo.java.collector.util.CharsetDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page是爬取过程中，内存中保存网页爬取信息的一个容器，Page只在内存中存
 * 放，用于保存一些网页信息，方便用户进行自定义网页解析之类的操作。
 */
public class Page {

    public static final Logger LOG = LoggerFactory.getLogger(Page.class);

    private CrawlDatum crawlDatum = null;

    private HttpResponse response = null;
    private Exception exception = null;

    private String html = null;
    private Document doc = null;
    private int retry = 0;

    private String charset = null;

    /**
     * 判断当前Page的URL是否和输入正则匹配
     *
     * @param urlRegex
     * @return
     */
    public boolean matchUrl(String urlRegex) {
        return Pattern.matches(urlRegex, getUrl());
    }

    /**
     * 获取网页中满足指定css选择器的所有元素的指定属性的集合
     * 例如通过getAttrs("img[src]","abs:src")可获取网页中所有图片的链接
     *
     * @param cssSelector
     * @param attrName
     * @return
     */
    public ArrayList<String> getAttrs(String cssSelector, String attrName) {
        ArrayList<String> result = new ArrayList<>();
        Elements elements = select(cssSelector);
        for (Element ele : elements) {
            if (ele.hasAttr(attrName)) {
                result.add(ele.attr(attrName));
            }
        }
        return result;
    }

    /**
     * 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     *
     * @param cssSelector
     * @return
     */
    public Links getLinks(String cssSelector) {
        Links links = new Links().addBySelector(doc(), cssSelector);
        return links;
    }

    public Elements select(String cssSelector) {
        return this.doc().select(cssSelector);
    }

    public Element select(String cssSelector, int index) {
        Elements elements = select(cssSelector);
        int realIndex = index;
        if (index < 0) {
            realIndex = elements.size() + index;
        }
        return elements.get(realIndex);
    }

    public String regex(String regex, int group, String defaultResult) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getHtml());
        if (matcher.find()) {
            return matcher.group(group);
        } else {
            return defaultResult;
        }
    }

    public String regex(String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getHtml());
        matcher.find();
        return matcher.group(group);
    }

    public String regex(String regex, String defaultResult) {
        return regex(regex, 0, defaultResult);
    }

    public String regex(String regex) {
        return regex(regex, 0);
    }

    public Page(CrawlDatum datum, HttpResponse response) {
        this.crawlDatum = datum;
        this.response = response;
    }

    /**
     * 返回网页/文件的内容
     *
     * @return 网页/文件的内容
     */
    public byte[] getContent() {
        if (response == null) {
            return null;
        }
        return response.getContent();
    }

    /**
     * 返回网页的url
     *
     * @return 网页的url
     */
    public String getUrl() {
        return crawlDatum.getUrl();
    }

    /**
     * 返回网页的源码字符串
     *
     * @return 网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (response.getHtml() != null) {
            html = response.getHtml();
            return html;
        }
        if (getContent() == null) {
            return null;
        }
        if (charset == null) {
            charset = CharsetDetector.guessEncoding(getContent());
        }
        try {
            this.html = new String(getContent(), charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            LOG.error("Exception", ex);
            return null;
        }
    }

    /**
     * 设置网页的源码字符串
     *
     * @param html 网页的源码字符串
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * 返回网页解析后的DOM树(Jsoup的Document对象)
     *
     * @return 网页解析后的DOM树
     */
    @Deprecated
    public Document getDoc() {
        return doc();
    }

    public Document doc() {
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), getUrl());
            return doc;
        } catch (Exception ex) {
            LOG.error("Exception", ex);
            return null;
        }
    }

    /**
     * 设置网页解析后的DOM树(Jsoup的Document对象)
     *
     * @param doc 网页解析后的DOM树
     */
    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public CrawlDatum getCrawlDatum() {
        return crawlDatum;
    }

    public void setCrawlDatum(CrawlDatum crawlDatum) {
        this.crawlDatum = crawlDatum;
    }

    public HashMap<String, String> getMetaData() {
        return crawlDatum.getMetaData();
    }

    public void setMetaData(HashMap<String, String> metaData) {
        this.crawlDatum.setMetaData(metaData);
    }

    public void meta(String key, String value) {
        this.crawlDatum.meta(key, value);
    }

    public String meta(String key) {
        return this.crawlDatum.meta(key);
    }

    @Deprecated
    public void setMetaData(String key, String value) {
        meta(key, value);
    }

    @Deprecated
    public String getMetaData(String key) {
        return meta(key);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
