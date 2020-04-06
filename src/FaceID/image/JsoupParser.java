package FaceID.image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class JsoupParser implements Callable<String> {

    private ExecutorService executorService;

    public JsoupParser(ExecutorService es){
        executorService = es;
    }

    @Override
    public String call() throws Exception {
        Future<String> Img_Url = executorService.submit(new ImageSearcher());
        Document doc = Jsoup.connect(Img_Url.get()).timeout(5000).get();
        return doc.select(".r5a77d" ).get(0).text().substring(19);
    }
}
