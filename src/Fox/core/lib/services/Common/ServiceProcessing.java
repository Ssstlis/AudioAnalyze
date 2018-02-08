package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.acoustid.AcoustIDApi;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Fox.core.lib.services.Common.Sifter.Sifting;
import static Fox.core.main.SearchLib.NO_COUNT;

public class ServiceProcessing
{
    private static Logger logger;

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
            AcoustIDException,
            NoMatchesException
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        if (count <= 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_COUNT);
            throw new IllegalArgumentException(NO_COUNT);
        }

        String location = AudioPrint.getLocation();
        ByFingerPrint AIDResp = AIDClient.LookupByFingerPrint(AudioPrint);


        if (AIDResp == null)
        {
            AcoustIDException acoustID_lookup_error = new AcoustIDException("Lookup error.");
            if (logger.isErrorEnabled())
                logger.error("", acoustID_lookup_error);
            throw acoustID_lookup_error;
        }

        if (AIDResp.hasError() && AIDResp.getErr().hasMessage())
        {
            Error err = AIDResp.getErr();
            if (logger.isErrorEnabled())
                logger.error("AcoustID error occure");
            throw new AcoustIDException("File:" + location + " Error code: " + err.getCode() + " message: " + err.getMessage());
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
            catch (NoMatchesException e)
            {
                if (logger.isErrorEnabled())
                    logger.error("An unexpected matches lookup error occur.", e);
            }
        }

        Target.put(location, temp);
    }
}
