package com.rvs.hhsearch.searcher;

import com.rvs.hhsearch.model.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HH_Searcher {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=%s+%s&page=%d";
//    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=автомойщик+%s&page=%d";
    String ADDITIONAL_VALUE;
    int pageValue = 0;

    public List<Vacancy> getVacancies(String vacancyText, String city) {

        List<Vacancy> result = new ArrayList<>();
        Document document = null;
        try {
            document = getDocument(vacancyText, city, pageValue);
            while (true) {
                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) {
                    pageValue = 0;
                    break;
                }
                for (Element element : elements) {
                    if (element != null) {
                        Vacancy vac = new Vacancy();
                        vac.setTitle(element.getElementsByAttributeValueContaining("data-qa", "title").text());
                        vac.setCity(element.getElementsByAttributeValueContaining("data-qa", "address").text());
                        vac.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                        vac.setSiteName(URL_FORMAT);
                        String urlPage = element.getElementsByAttributeValueContaining("data-qa", "title").attr("href");
                        vac.setUrl(urlPage);
                        String salary = element.getElementsByAttributeValueContaining("data-qa", "compensation").text();
                        vac.setSalary(salary.length() == 0 ? "" : salary);
                        result.add(vac);
                    }
                }
                ++pageValue;
                System.out.println(pageValue);
                document = getDocument(vacancyText, city, pageValue);
                if (document == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }

    protected Document getDocument(String vacancyText, String city, int page) throws IOException {
        String myURL = String.format(URL_FORMAT, vacancyText, city, page);

        Document doc = null;
        try {
            doc = Jsoup.connect(myURL)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.1 Safari/605.1.15")
                    .referrer("")
                    .get();
        } catch (IOException e) {
            e.getMessage();
        }

        return doc;
    }
}
