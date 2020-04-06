package FaceID;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FaceID {

    private static ExecutorService executorService;

    public static void main(String[] args) throws Exception {
        Long C_M = System.currentTimeMillis();
        executorService = Executors.newFixedThreadPool(3);
        Future<Long> Task_Face = executorService.submit(new FaceIDModule(executorService));
        Long D_M = Task_Face.get() - C_M;
        System.out.println("Time Elapsed: "+ D_M/1000f+"s");
        executorService.shutdown();
    }
}
