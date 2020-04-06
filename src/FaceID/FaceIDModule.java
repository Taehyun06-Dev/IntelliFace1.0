package FaceID;

import FaceID.image.JsoupParser;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.opencv.imgproc.Imgproc.putText;
import static org.opencv.imgproc.Imgproc.rectangle;

public class FaceIDModule implements Callable<Long> {

    private ExecutorService executorService;

    public FaceIDModule(ExecutorService es){
        executorService = es;
    }

    @Override
    public Long call() throws ExecutionException, InterruptedException {
        nu.pattern.OpenCV.loadShared();
        System.out.println("Running IntelliFace!");
        CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\bkcha\\Desktop\\Face\\haarcascade_frontalface_alt2.xml");
        Mat image = Imgcodecs.imread("C:\\Users\\bkcha\\Desktop\\Face\\input_t.jpg");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        Rect[] rectlist = faceDetections.toArray();
        ArrayList<String> stringList = new ArrayList<>();
        for (Rect rect : rectlist) {
            Rect rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            Mat markedImage = new Mat(image,rectCrop);
            Imgcodecs.imwrite("C:\\Users\\bkcha\\Desktop\\Face\\cropped.png", markedImage );
            Future<String> Str_FT = executorService.submit(new JsoupParser(executorService));
            System.out.println(Str_FT.get());
            stringList.add(Str_FT.get());
        }
        int count = 0;
        for (Rect rect2 : rectlist){
            count ++;
            Rect rect2Crop = new Rect(rect2.x, rect2.y, rect2.width, rect2.height);
            rectangle(image, new Point(rect2.x, rect2.y), new Point(rect2.x + rect2.width, rect2.y + rect2.height), new Scalar(0, 255, 0), 3, 0, 0);
            putText(image, stringList.get(count-1), new Point(rect2.x, rect2.y), Core.FONT_ITALIC, 1.0, new Scalar(0, 255, 0), 2);
        }
        String filename = "C:\\Users\\bkcha\\Desktop\\Face\\faceDetection.png";
        Imgcodecs.imwrite(filename, image);
        return System.currentTimeMillis();
    }
}
