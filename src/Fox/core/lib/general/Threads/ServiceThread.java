package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.services.Common.ServiceProcessing;
import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ServiceThread
        implements Runnable
{
    private FingerPrint FPrint;
    private volatile ProgressState Local, Common;
    private AcoustIDClient AIDClient;
    private boolean Trust;
    private ConcurrentHashMap<String, List<ID3V2>> target;
    private LastFMClient lastFMClient;
    private MusicBrainzWebClient musicBrainzWebClient;
    private int count;


    public ServiceThread(
            @NotNull AcoustIDClient AIDClient,
            @NotNull LastFMClient LastFMClient,
            @NotNull MusicBrainzWebClient musicBrainzWebClient,
            @NotNull FingerPrint FPrint,
            @NotNull ConcurrentHashMap<String, List<ID3V2>> Target,
            @NotNull ProgressState ServiceState,
            @NotNull ProgressState CommonProgress,
            boolean Trust,
            int count)
    {
        this.AIDClient = AIDClient;
        this.FPrint = FPrint;
        this.Common = CommonProgress;
        this.Local = ServiceState;
        this.Trust = Trust;
        this.target = Target;
        this.lastFMClient = LastFMClient;
        this.count = count;
        this.musicBrainzWebClient = musicBrainzWebClient;
    }

    @Override
    public void run()
    {
        try
        {
            synchronized (FPrint)
            {
                FPrint.wait(4000);
                ServiceProcessing ProcessingClient = new ServiceProcessing(AIDClient,
                                                                           lastFMClient,
                                                                           musicBrainzWebClient);

                ProcessingClient.Processing(FPrint,
                                            Trust,
                                            target,
                                            count
                                           );

                Local.update();
                Common.update();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
