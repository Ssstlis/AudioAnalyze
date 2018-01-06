package Fox.test.util;

public class LibChecker {
    /*private final String fingerprintbase = System.getProperty("user.dir") + "\\resources\\fingerprintlist.txt";
    private ArrayList<FingerPrint> FPList;
    public LibChecker()
    {
        FPList = new ArrayList<>();
    }

    public void check(String location) throws Exception
        {
        core.utils.FileList FileListClient = new core.utils.FileList();
        List<File> FileList = FileListClient.GetFileList(location, new Mp3Filter());
            File Finder = new File(fingerprintbase);
            if (Finder.exists())
            {
                FileInputStream fstream = new FileInputStream(fingerprintbase);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                List<String> strList = new ArrayList<>();
                String strLine;

                while ((strLine = br.readLine()) != null)
                    strList.add(strLine);

                int size = strList.size();

                for(int i = 0; i<size-1;i++)
                    for(int j = 0; j<2 || size-i>2; i++, j++)
                        FPList.add(new FingerPrint(strList.get(i+2),strList.get(i+1),strList.get(i)));

                List<String> StringList = new ArrayList<>();
                for(File file:FileList)
                {
                    String temploc = file.getPath();
                    if (!LibChecker.contains(temploc, FPList))
                        StringList.add(temploc);
                }
                if (!StringList.isEmpty())
                {
                    AudioAnalyzeLibrary CalcClient = new AudioAnalyzeLibrary();
                    List<FingerPrint> PrintList = CalcClient.buildingStrings(StringList).run(false);
                    TFile TFileClient = new TFile(fingerprintbase);
                    for (FingerPrint OnePrint:PrintList)
                        TFileClient.Append(OnePrint.ToString());
                }
            }
            else
            {
                if (!Finder.createNewFile())
                    throw new Exception();
                else
                {

                }
            }
        }

    private static boolean contains(String location, List<FingerPrint> FPlist)
    {
        for (FingerPrint one:FPlist)
            if (one.getLocation().equals(location))
                return true;
        return false;
    }*/
}
