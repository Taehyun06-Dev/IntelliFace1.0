package FaceID.image;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ImageSearcher implements Callable<String> {

    @Override
    public String call() throws Exception {
        try {
            HttpClient client = new DefaultHttpClient();
            String url = "https://www.google.com/searchbyimage/upload";
            String imageFile = "c:\\temp\\shirt.jpg";
            HttpPost post = new HttpPost(url);

            MultipartEntity entity = new MultipartEntity();
            entity.addPart("encoded_image", new FileBody(new File("C:\\Users\\bkcha\\Desktop\\Face\\cropped.png")));
            entity.addPart("image_url", new StringBody(""));
            entity.addPart("image_content", new StringBody(""));
            entity.addPart("filename", new StringBody(""));
            entity.addPart("h1", new StringBody("en"));
            entity.addPart("bih", new StringBody("179"));
            entity.addPart("biw", new StringBody("1600"));

            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                if (line.indexOf("HREF") > 0)
                    return(line.substring(8).replaceAll("\">here</A>.", "").replaceAll("\"", ""));
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

