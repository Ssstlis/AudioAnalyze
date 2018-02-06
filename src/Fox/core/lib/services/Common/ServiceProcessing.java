package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.Exceptions;
import Fox.core.lib.services.acoustid.AcoustIDApi;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import Fox.core.main.AudioAnalyzeLibrary;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Fox.core.lib.services.Common.Sifter.Sifting;
import static Fox.core.main.AudioAnalyzeLibrary.NO_COUNT;

public class ServiceProcessing
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);

    public ServiceProcessing()
    {

    }

    public static void Processing(
            @NotNull AcoustIDApi AIDClient,
            @NotNull FingerPrint AudioPrint,
            boolean Trust,
            @NotNull Map<String, List<ID3V2>> Target,
            int count)
            throws
            Exceptions.AcoustIDException,
            Exceptions.NoMatchesException
    {
        if (count <= 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_COUNT);
            throw new IllegalArgumentException("Impossible to return less that zero or equals zero size of results.");
        }

        String location = AudioPrint.getLocation();
        ByFingerPrint AIDResp = AIDClient.LookupByFingerPrint(AudioPrint);


        if (AIDResp == null)
        {
            Exceptions.AcoustIDException acoustID_lookup_error = new Exceptions.AcoustIDException("Lookup error.");
            if (logger.isErrorEnabled())
                logger.error("", acoustID_lookup_error);
            throw acoustID_lookup_error;
        }

        if (AIDResp.hasError() && AIDResp.getErr().hasMessage())
        {
            Error err = AIDResp.getErr();
            if (logger.isErrorEnabled())
                logger.error("AcoustID error occure");
            throw new Exceptions.AcoustIDException("File:" + location + " Error code: " + err.getCode() + " message: " + err.getMessage());
        }

        List<SimpleInfo> AfterSift = Sifting(AudioPrint,
                AIDResp,
                count,
                Trust);

        List<ID3V2> temp = new ArrayList<>();

        for (SimpleInfo elem : AfterSift)
        {

            try
            {
                ID3V2 buildTag = BuildTagProcessing.BuildTag(elem);

                if (buildTag != null)
                    temp.add(buildTag);

            }
            catch (Exceptions.NoMatchesException e)
            {
                if (logger.isErrorEnabled())
                    logger.error("An unexpected matches lookup error occur.", e);
            }
        }

        Target.put(location, temp);
    }
}
