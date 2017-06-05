import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrej on 03.05.17.
 */
public class Parser {
    private static Document getPage() throws IOException {
        String url ="http://www.pogoda.spb.ru";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    //03.05
    // \d{2}\.\d{2} регулярное выражение

    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}"); //шаблон поиска

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate); //совпадение
        if (matcher.find()){
            return matcher.group();
        }
        throw new Exception("can't extract date from string");
    }

    private static int printPartValues(Elements values, int index) {
        int iterationCount = 4;
        if (index == 0) {
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            if (isMorning) {
                iterationCount = 3;
            }
        }

        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "  ||\t\t");
            }
            System.out.println();

        }

        return iterationCount;
    }

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        // css query language
        Element tableWth = page.select("table[class=wt]").first(); //первый из возможных
        Elements names = tableWth.select("tr[class=wth]");
        Elements values = tableWth.select("tr[valign=top]");
        int index = 0;

        for (Element name: names
             ) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);
            System.out.println(date +"\tЯвление\t\tТемпература\t\tДавл\t\tВлажность\t\tВетер");
            int iterationCount = printPartValues(values,index);
            index = index +iterationCount;
        }


    }
}
