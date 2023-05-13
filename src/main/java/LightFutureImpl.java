import java.util.Queue;
import java.util.function.Function;

import static java.lang.Thread.currentThread;

abstract class LightFutureImpl<R> implements LightFuture{
    R result;
    Queue<LightFuture> job_queue;
    boolean finished = false;
    boolean exception_thrown = false;
    Worker parent = null;

    public LightFutureImpl(Queue<LightFuture> job_queue){
        this.job_queue = job_queue;
    }

    public LightFutureImpl(Queue<LightFuture> job_queue, Worker parent){
        this.job_queue = job_queue;
        this.parent = parent;
    }
    @Override
    public boolean isReady() {
        return finished;
    }

    @Override
    public R get() throws LightExecutionException {
        if(exception_thrown){
            throw new LightExecutionException();
        }else{
            return result;
        }
    }

    @Override
    public LightFuture thenApply(Function f){

        LightFutureImpl new_job = new LightFutureImpl(this.job_queue, (Worker) currentThread()) {
            @Override
            public void run() {
                while(parent.state){
                }
                result = f.apply(this.result);
            }
        };
        this.job_queue.add(new_job);
        return new_job;
    }
}
