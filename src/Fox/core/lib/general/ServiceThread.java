package Fox.core.lib.general;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ServiceThread implements Runnable {
    private FingerPrint FPrint;
    private ProgressState Local, Common;


    public ServiceThread(@NotNull FingerPrint FPrint,
                         @NotNull Map<String, Tag> Target,
                         @NotNull ProgressState ServiceState,
                         @NotNull ProgressState CommonProgress
    ) {
        this.FPrint = FPrint;
        this.Common = CommonProgress;
        this.Local = ServiceState;
    }

    @Override
    public void run() {
        try {
            synchronized (FPrint) {
                FPrint.wait(4000);
                //System.out.println(FPrint.toString());
                //TimeUnit.MILLISECONDS.sleep(2000);

                synchronized (Local) {
                    Local.update(Local.state + 1, Local.desc);
                }
                synchronized (Common) {
                    Common.update(Common.state + 1, Common.desc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
