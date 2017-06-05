import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrej on 07.05.17.
 */
public class ParserTwo {
    private static Document getPage() throws IOException {
        String url = "https://www.cbr.ru";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
    private static Pattern pattern = Pattern.compile("\\D{8}\\s\\D{6}\\s\\D{1}\\s\\d{2}\\.\\d{2}\\.\\d{4}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if(matcher.find()){
            return matcher.group();
        }
        throw new Exception("can't extract date from string");
    }

    private static void printValue(Elements value){
        for (int i = 0; i < 2; i++) {
            Element valueLine = value.get(i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "  ");
            }
        }
        System.out.println();
    }

    private static ArrayList printValueMoney(Elements value) {
      /*  for (Element ignored : value
             ) {
            Elements tableTd = ignored.select("td[class=title]");
            Elements tableDiv = ignored.select("div[class=w_data_wrap]");
            System.out.println(tableTd.text());
            System.out.println(tableDiv.text());
        }*/
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i<1; i++) {
            Elements tableTh = value.select("th");
            System.out.println(tableTh.text());
            Element valueLine = value.get(i);
            for (Element td : valueLine.select("td")) {



                strings.add(td.text()+" ");

                System.out.print(td.text() + "  ");
                System.out.println();
            }
            System.out.println();
        }
        return strings;
    }
    public static void main(String[] args) throws Exception {





        Document page = getPage();
        Elements divGlobal = page.select("div[class=col widgets no_visited]");
        Element div = divGlobal.select("div[class=widget opened type_table name_guidelines]").first();
        Elements name = div.select("div[class=content]");
        Elements value = name.select("td");
        String dateString = value.select("td[class=title]").text();
        String date = getDateFromString(dateString);

        printValue(value);


        Elements divMoney = divGlobal.select("div[class=group opened]");
        Element nameMoney = divMoney.select("div[class=widget type_table name_exchange opened]").first();
        Elements valueMoney = nameMoney.select("div[class=content]");

       ArrayList<String> stringArrayList =  printValueMoney(valueMoney);
        JOptionPane.showMessageDialog(null, stringArrayList);


       // System.out.println(valueMoney);
    }
}
