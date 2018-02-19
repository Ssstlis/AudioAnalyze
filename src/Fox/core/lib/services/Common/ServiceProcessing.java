package Fox.core.lib.services.Common;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.data.ID3V2;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.AcoustID.AcoustIDApi;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.Error;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.Contract;
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
    @Contract("_, null, _, _, _ -> fail")
    public static void Processing(
            @NotNull AcoustIDApi AIDClient,
            FingerPrint AudioPrint,
            boolean Trust,
            @NotNull Map<String, List<ID3V2>> Target,
            int count)
            throws
            AcoustIDException,
            NoMatchesException
    {
        if (AudioPrint == null || (!AudioPrint.hasDuration() && !AudioPrint.hasPrint()))
            throw new IllegalArgumentException("FingerPrint is null or contains error");

        Logger logger = LoggerFactory.getLogger(SearchLib.class);
        if (logger.isDebugEnabled())
            logger.debug("Start service processing");
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
            throw new AcoustIDException("Lookup error.");
        }

        if (AIDResp.hasError() && AIDResp.getErr().hasMessage())
        {
            Error err = AIDResp.getErr();
            if (logger.isErrorEnabled())
                logger.error("AcoustID error occur");
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
                if (logger.isDebugEnabled())
                    logger.debug("Building tag start");
                ID3V2 buildTag = BuildTagProcessing.BuildTag(elem);
                if (logger.isDebugEnabled())
                    logger.debug("Building tag done");
                if (buildTag != null)
                    temp.add(buildTag);
            }
            catch (NoMatchesException e)
            {
                if (logger.isErrorEnabled())
                    logger.error("No matches in tag builder for {}", location);
            }
        }
        if (temp.isEmpty())
        {
            throw new NoMatchesException();
        }
        if (Trust)
            temp = Sifter.Choosing(temp);
        Target.put(location, temp);
    }
}
