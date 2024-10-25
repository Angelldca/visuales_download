package com.angelldca.visuales_download.Scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkExtractor {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".mp4", ".srt", ".vtt", ".mkv", ".avi");
    public List<String> getAllLinks(String url) throws IOException {
        List<String> links = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Elements linkElements = document.select("table tbody tr td a");

        for (Element linkElement : linkElements) {
            String linkHref = linkElement.attr("href");
            if (hasAllowedExtension(linkHref)) {
                links.add(url + linkHref);
            }
        }

        return links;
    }
    private boolean hasAllowedExtension(String linkHref) {
        for (String extension : ALLOWED_EXTENSIONS) {
            if (linkHref.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
