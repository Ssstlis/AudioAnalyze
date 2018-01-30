package Fox.test;

import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.Common.Sifter;
import Fox.core.lib.services.Common.SimpleInfo;
import Fox.core.lib.services.acoustid.AcoustIDResponse;
import Fox.core.lib.services.acoustid.LookupByFP.AcoustIDStructureBuilder;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;

import java.io.*;
import java.util.List;

public class testerrors
{
    public static void main(String[] args)
            throws IOException, AcoustIDException, NoMatchesException
    {
        File f = new File("C:\\Users\\Ssstlis\\Desktop\\Projects\\AudioAnalyze\\json\\AcoustID\\lookupbyFP\\recordings_releases_releasegroups_tracks_usermeta_sources.json");
        BufferedReader fin = new BufferedReader(new FileReader(f));
        String json = "";
        String line;
        StringBuilder a = new StringBuilder();
        while ((line = fin.readLine()) != null)
            a.append(line);
        json = a.toString();

        AcoustIDResponse acoustIDResponse = new AcoustIDResponse();
        acoustIDResponse.setSource(json);
        ByFingerPrint temp = new AcoustIDStructureBuilder().buildLookup(acoustIDResponse);
        List<SimpleInfo> sifting = Sifter.Sifting(temp, false, 10);
        System.out.println();
    }
}
