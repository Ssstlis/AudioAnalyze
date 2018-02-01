package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.services.Common.ServiceProcessing;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.SEVERE;

public class ServiceThread
        implements Runnable
{
    private FingerPrint FPrint;
    private volatile ProgressState Local, Common;
    private AcoustIDClient AIDClient;
    private boolean Trust;
    private ConcurrentHashMap<String, List<ID3V2>> target;
    private LastFMApi lastFMApi;
    private MusicBrainzWebClient musicBrainzWebClient;
    private int count;


    public ServiceThread(
            @NotNull AcoustIDClient AIDClient,
            @NotNull LastFMApi LastFMApi,
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
        this.lastFMApi = LastFMApi;
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

                ServiceProcessing.Processing(AIDClient,
                                             lastFMApi,
                                             musicBrainzWebClient,
                                             FPrint,
                                             Trust,
                                             target,
                                             count
                                            );
            }
        }
        catch (Exception e)
        {
            logger.log(SEVERE, "", e);
        }
        finally
        {
            Local.update();
            Common.update();
        }
    }
}
