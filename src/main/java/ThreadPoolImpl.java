import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Supplier;

public class ThreadPoolImpl {
    public final Worker[] workers;
    public final Queue<LightFuture> jobs;

    public ThreadPoolImpl(int n){
        this.jobs = new PriorityQueue<LightFuture>();
        this.workers = new Worker[n];

        for(int i = 0; i < n; i++){
            workers[i] = new Worker(jobs);
        }
    }

    public void shutdown(){
        for (Worker t : workers) {
            t.interrupt();
        }
    }

    public void submit(Supplier supplier) {
        LightFuture job = new LightFutureImpl(jobs) {
            @Override
            public void run() {
                try{
                    result = supplier.get();
                    finished = true;
                }
                catch(Exception e){
                    exception_thrown = true;
                }
            }
        };
        jobs.add(job);
    }
}
