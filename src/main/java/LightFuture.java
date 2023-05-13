import java.util.function.Function;

interface LightFuture<R> extends Runnable{
    R get() throws LightExecutionException;

    boolean isReady();

    LightFuture thenApply(Function f) throws LightExecutionException;
}
