import java.util.Queue;

class Worker extends Thread{
    public boolean state;
    private final Queue<LightFuture> jobs;
    public Worker(Queue<LightFuture> jobs){
        this.state = false;
        this.jobs = jobs;
    }
    @Override
    public void run() {
        LightFuture job = jobs.poll();
        if (job != null) {
            this.state = true;
            job.run();
            state = false;
        }
    }
}

