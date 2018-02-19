package Fox.core.main;

/*
 * MIT License
 *
 * Copyright (c) 2018 Ivan Aristov (Ssstlis/Fox)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

import Fox.core.lib.general.data.*;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.threads.FPCalcThread;
import Fox.core.lib.general.threads.ServiceThread;
import Fox.core.lib.general.utils.ExecutableHelper.Entry;
import Fox.core.lib.general.utils.*;
import Fox.core.lib.services.Common.BuildTagProcessing;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveApi;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.Album.search.sources.albummatches;
import Fox.core.lib.services.LastFM.Album.search.sources.results;
import Fox.core.lib.services.LastFM.CommonSources.image;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.ReleaseArtist;
import org.musicbrainz.android.api.data.ReleaseInfo;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import static Fox.core.lib.services.Common.Elapsed.MusicBrainzElapse;
import static Fox.core.lib.services.LastFM.Album.LastFMAlbumClient.getInfo;
import static Fox.core.lib.services.LastFM.Album.LastFMAlbumClient.search;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;


/**
 * Main class of library. Calling methods in according with javadoc for expected results.
 */
public class SearchLib
{
    private static Logger logger;
    public final static String NO_MATCHES = "No matches.";
    public final static String NO_COUNT = "Impossible to return less then zero or equals zero size of results.";
    private final static String CALL_WO_BUILD = "Trying to call method without build file list.";
    private final static String SAME_INSTANCES = "Same progress state instances";
    private final static String NO_ONE_FILE_ERROR = "No one file were accepted.";

    /** One of the main functional of library. Doing processing with your files
     * and services, using finger print of media file for looking up data about FP in services.
     * You can manually configure processing and observing with progress bars.
     * @param Files list of files locations that you need to data looking up.
     * @param YourFPCalcImplementation Your implementation of FingerPrintCalc/Chromaprint library to get fingerprint
     *                                 of media file.
     * @param CheckerProgressBar Progress bar for observing files checking process for each file.
     * @param FPProgressBar Progress bar for observing fingerprints build for each file that was not reject.
     * @param ServiceProgressBar Progress bar for observing search data for each file that was not reject.
     * @param CommonProgressBar Common progress bar, show common progress.
     * @param Speed Allows you to configure the number of threads in thread pool.
     * @param count Configure maximum number of tags set for each file.
     * @return Entry with result as key, which keys - file location (or other data, creating by your fingerprint
     * realisation) and value - tag set, and reject files location (or other data, creating by your fingerprint
     * realisation) as value.
     * @throws InterruptedException if you have some problems with thread pool.
     * @throws NoBuildException if Files are empty.
     * @throws IllegalArgumentException if you configure count <= 0. Or same instances of progress bars.
     * @throws ProgressStateException if your implementation of progress bar doing resize wrong.
     * @throws NoMatchesException if library can`t look up any results for your files.
     * @throws NoAccessingFilesException if all files are rejected.
     */
    @NotNull
    public static Entry<Map<String, List<ID3V2>>, List<String>> SearchTags(@NotNull List<String> Files,
                                                                           @NotNull FingerPrintThread YourFPCalcImplementation,
                                                                           ProgressState CheckerProgressBar,
                                                                           ProgressState FPProgressBar,
                                                                           ProgressState ServiceProgressBar,
                                                                           ProgressState CommonProgressBar,
                                                                           performance Speed,
                                                                           int count)
            throws
            InterruptedException,
            NoBuildException,
            IllegalArgumentException,
            ProgressStateException,
            NoMatchesException,
            NoAccessingFilesException
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        if (logger.isDebugEnabled())
            logger.debug("Multiple files search start");
        if (Files.size() == 0 || count < 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_COUNT);
            throw new IllegalArgumentException(NO_COUNT);
        }

        if (IsSameInstances(CheckerProgressBar, FPProgressBar, ServiceProgressBar, CommonProgressBar))
        {
            if (logger.isErrorEnabled())
                logger.error(SAME_INSTANCES);

            throw new IllegalArgumentException(SAME_INSTANCES);
        }

        boolean isBuild = false;

        List<String> Locations = ExcludeDuplicate(Files);

        if (Locations.size() != 0)
        {
            isBuild = true;
        }

        if (!isBuild)
        {
            if (logger.isErrorEnabled())
                logger.error(CALL_WO_BUILD);
            throw new NoBuildException(CALL_WO_BUILD);
        }

        if (CommonProgressBar != null)
            CommonProgressBar.setSize(Locations.size() * 3);
        if (CheckerProgressBar != null)
            CheckerProgressBar.setSize(Locations.size());

        FileChecker FileReviewer = new FileChecker();

        if (logger.isDebugEnabled())
            logger.debug("Start files check");

        FileReviewer.SiftFileAsString(Locations,
                                      CheckerProgressBar,
                                      CommonProgressBar
                                     );

        if (logger.isDebugEnabled())
            logger.debug("End files check");

        Locations = FileReviewer.getAccepted();
        List<String> Rejected = FileReviewer.getRejected();
        int size = Locations.size();
        if (size == 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_ONE_FILE_ERROR);
            throw new NoAccessingFilesException(NO_ONE_FILE_ERROR);
        }

        Map<String, List<ID3V2>> target = new ConcurrentHashMap<>(size);

        if (Rejected.size() > 0 && CommonProgressBar != null)
        {
            CommonProgressBar.setSize(CommonProgressBar.getSize() - 3 * Rejected.size());
        }
        if (FPProgressBar != null)
            FPProgressBar.setSize(size);
        if (ServiceProgressBar != null)
            ServiceProgressBar.setSize(size);

        int N_CPUs = Runtime.getRuntime()
                            .availableProcessors();
        int CPU = 1;
        if (N_CPUs != 1)
        {
            switch (Speed)
            {
                case MAX:
                    CPU = (N_CPUs > 1) ? (N_CPUs) : (2);
                    break;
                case HALF:
                    CPU = (N_CPUs / 2 > 1) ? (N_CPUs / 2) : (2);
                    break;
                case CLOSETOMAX:
                    CPU = (N_CPUs * 3 / 4 > 1) ? (N_CPUs * 3 / 4) : (2);
                    break;
                case CLOSETOMIN:
                    CPU = (N_CPUs / 4 > 1) ? (N_CPUs / 4) : (2);
                    break;
                default: CPU = 1; break;
            }
        }

        if (logger.isInfoEnabled())
            logger.info("Starting work with " + CPU + " threads");

        ExecutorService ServicePool = Executors.newFixedThreadPool(CPU, new ThreadFactory()
        {
            @Override
            public Thread newThread(@NotNull Runnable r)
            {
                return new Thread(r, "Service Pool");
            }
        });

        if (logger.isInfoEnabled())
            logger.info("Instance FingerPrint thread list");

        long t = System.currentTimeMillis();
        List<Callable<FingerPrint>> tasks = new ArrayList<>(size);

        for (String file : Locations)
        {
            tasks.add(new FPCalcThread(YourFPCalcImplementation,
                                       file,
                                       FPProgressBar,
                                       CommonProgressBar));
        }

        if (logger.isInfoEnabled())
            logger.info("Instance done in {} milliseconds", System.currentTimeMillis() - t);

        List<Future<FingerPrint>> futureList = ServicePool.invokeAll(tasks);
        boolean TrustMode = count == 1;
        while (futureList.size() > 0)
        {
            Future<FingerPrint> toRemove = null;
            for (Future<FingerPrint> thread : futureList)
                if (thread.isDone())
                {
                    toRemove = thread;
                    FingerPrint print = null;
                    try
                    {
                        print = thread.get();
                    }
                    catch (ExecutionException e)
                    {
                        if (logger.isTraceEnabled())
                            logger.error("", e);
                    }

                    if (print != null)
                    {
                        if (logger.isInfoEnabled())
                            logger.info("Start service thread");

                        ServicePool.submit(new ServiceThread(print,
                                                             target,
                                                             ServiceProgressBar,
                                                             CommonProgressBar,
                                                             TrustMode,
                                                             count,
                                                             Rejected));
                    }
                    break;
                }

            if (toRemove != null)
            {
                futureList.remove(toRemove);
            }
        }

        ServicePool.shutdown();
        ServicePool.awaitTermination(25, MINUTES);
        if (target.size() == 0)
        {
            NoMatchesException e = new NoMatchesException(NO_MATCHES);
            if (logger.isErrorEnabled())
            {
                logger.error("", e);
            }
            throw e;
        }
        return new Entry<>(target, Rejected);
    }

    /** One of the main functional of library. Doing processing with your file
     * and services, using finger print of media file for looking up data about FP in services.
     * You can manually configure processing and observing with progress bar.
     * @param file file location that you need to looking up data.
     * @param YourFPCalcImplementation Your implementation of FingerPrintCalc/Chromaprint library to get fingerprint
     *                                       of media file.
     * @param ProgressBar Common progress bar, show common progress.
     * @param count Configure maximum number of tags set for file.
     * @return List of tags for this file.
     * @throws InterruptedException if you have some problems with thread pool.
     * @throws IllegalArgumentException if you configure count <= 0. Or same instances of progress bars.
     * @throws ProgressStateException if your implementation of progress bar doing resize wrong.
     * @throws NoMatchesException if library can`t look up any results for your files.
     * @throws NoAccessingFilesException if file is rejected.
     */
    public static List<ID3V2> SearchTags(@NotNull String file,
                                         @NotNull FingerPrintThread YourFPCalcImplementation,
                                         ProgressState ProgressBar,
                                         int count)
            throws
            InterruptedException,
            IllegalArgumentException,
            ProgressStateException,
            NoMatchesException,
            NoAccessingFilesException
    {
        if (count <= 0 || file.length() < 4)
            throw new IllegalArgumentException(NO_COUNT + " Or wrong file name.");

        logger = LoggerFactory.getLogger(SearchLib.class);
        if (logger.isDebugEnabled())
            logger.debug("Single files search start");

        boolean TrustMode = count == 1;
        FileChecker FileReviewer = new FileChecker();

        if (logger.isDebugEnabled())
            logger.debug("Start file check");

        @NotNull List<String> Locations = new ArrayList<>();
        Locations.add(file);
        ProgressBar.setSize(ProgressBar.getSize() + 3);
        FileReviewer.SiftFileAsString(Locations,
                                      null,
                                      ProgressBar);

        if (logger.isDebugEnabled())
            logger.debug("End file check");

        List<String> reviewerAccepted = FileReviewer.getAccepted();
        if (reviewerAccepted.size() != 1)
        {
            ProgressBar.setSize(ProgressBar.getSize() - 2);
            throw new NoAccessingFilesException("No one file were accepted.");
        }

        String temp_path = reviewerAccepted.get(0);

        Map<String, List<ID3V2>> target = new HashMap<>();
        ExecutorService ServicePool = Executors.newFixedThreadPool(1, new ThreadFactory()
        {
            @Override
            public Thread newThread(@NotNull Runnable r)
            {
                return new Thread(r, "Service Pool");
            }
        });

        if (logger.isInfoEnabled())
            logger.info("Instance FingerPrint thread");

        Future<FingerPrint> future = ServicePool.submit(new FPCalcThread(YourFPCalcImplementation,
                                                                         temp_path,
                                                                         null,
                                                                         ProgressBar));
        FingerPrint fingerPrint;
        try
        {
            fingerPrint = future.get();
        }
        catch (ExecutionException e)
        {
            throw new IllegalArgumentException(e);
        }

        if (logger.isInfoEnabled())
            logger.info("Start service thread");

        if (fingerPrint != null)
            ServicePool.submit(new ServiceThread(fingerPrint,
                                                 target,
                                                 null,
                                                 ProgressBar,
                                                 TrustMode,
                                                 count,
                                                 null));

        ServicePool.shutdown();
        ServicePool.awaitTermination(25, MINUTES);
        if (target.size() == 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_MATCHES);
            throw new NoMatchesException(NO_MATCHES);
        }
        List<ID3V2> list = null;
        for(Map.Entry<String, List<ID3V2>> elem:target.entrySet())
        {
            Object value = elem.getValue();
            //noinspection unchecked
            list = (List<ID3V2>)value;
        }

        return list;
    }

    public static AlbumArtCompilation SearchCovers(@NotNull String AlbumName,
                                                   String ArtistName,
                                                   target source,
                                                   int count)
            throws
            IllegalArgumentException,
            NoMatchesException

    {
        logger = LoggerFactory.getLogger(SearchLib.class);

        if (logger.isDebugEnabled())
            logger.debug("Starting search album arts");

        if (AlbumName.isEmpty() || count < 1)
        {
            if (logger.isErrorEnabled())
            {
                logger.error("Illegal album title or number of arts.");
            }
            throw new IllegalArgumentException();
        }

        long time = System.currentTimeMillis();
        AlbumArtCompilation temp = new AlbumArtCompilation(AlbumName, ArtistName,null);

        if (ArtistName != null && !ArtistName.isEmpty())
        {
            if (logger.isDebugEnabled())
                logger.debug("First scenario\nArtist: {} Album:{}", ArtistName, AlbumName);

            AlbumInfo LFMInfo =
                    getInfo(null,
                            ArtistName,
                            AlbumName,
                            null,
                            null,
                            true
                           );
            if (LFMInfo == null || LFMInfo.hasError())
            {
                throw new NoMatchesException(NO_MATCHES);
            }

            if (LFMInfo.hasAlbum())
            {
                album album = LFMInfo.getAlbum();
                List<Art> ArtList = new ArrayList<>();

                String albumArtist = album.getArtist();
                String albumName = album.getName();

                if (album.hasImages())
                {
                    AddMostResolutionImageLink(albumArtist,
                                               albumName,
                                               album.getImages(),
                                               ArtList);
                }

                if (album.hasMbid())
                {
                    if (logger.isDebugEnabled())
                        logger.debug("Cover Art lookup start");
                    try
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                    }
                    catch (InterruptedException e)
                    {
                        if (logger.isErrorEnabled())
                            logger.error("Sleep error wtf?");
                    }

                    AlbumArt lookupAlbumArt = CoverArtArchiveApi.LookupAlbumArt(album.getMbid());

                    if (logger.isDebugEnabled())
                        logger.debug("Cover Art lookup end");

                    if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                        for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                            if (elem.hasFront())
                                ArtList.add(new Art(elem.getImage(),
                                                    "mega",
                                                    albumArtist,
                                                    albumName,
                                                    target.CoverArtArchive));
                }
                temp.setArtList(ArtList);
                if (logger.isDebugEnabled())
                    logger.debug("end in {} milliseconds", System.currentTimeMillis() - time);
                return temp;
            }
        }

        if (source == target.LastFM)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("second scenario\nAlbum:{} ", AlbumName);
            }

            if (logger.isDebugEnabled())
                logger.debug("LastFM lookup start");

            Search searchLF =
                    search(count,
                           null,
                           AlbumName
                          );

            if (logger.isDebugEnabled())
            {
                logger.debug("LastFM lookup end");
            }

            if (searchLF != null && searchLF.hasResults())
            {
                results LFMResults = searchLF.getResults();

                if (LFMResults.hasAlbummatches())
                {
                    albummatches LFMResultsAlbummatches = LFMResults.getAlbummatches();

                    if (LFMResultsAlbummatches.hasAlbumList())
                    {
                        List<Fox.core.lib.services.LastFM.Album.search.sources.album> LFMResultsAlbummatchesAlbumList = LFMResultsAlbummatches.getAlbumList();

                        List<Art> ArtList = new ArrayList<>();

                        int ResSize = LFMResultsAlbummatchesAlbumList.size();

                        for (int l = 0; ArtList.size() != count && l < ResSize; l++)
                        {
                            Fox.core.lib.services.LastFM.Album.search.sources.album elem = LFMResultsAlbummatchesAlbumList.get(l);

                            String elemArtist = elem.getArtist();
                            String elemName = elem.getName();

                            if (elem.hasImages())
                                AddMostResolutionImageLink(elemArtist,
                                                           elemName,
                                                           elem.getImages(),
                                                           ArtList);
                        }

                        if (ArtList.isEmpty())
                            throw new NoMatchesException(NO_MATCHES);

                        temp.setArtList(ArtList);
                        if (logger.isDebugEnabled())
                            logger.debug("end in {} milliseconds", System.currentTimeMillis() - time);
                        return temp;
                    }
                }
            }
        }

        if (source == target.MusicBrainz)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("third scenario\nAlbum:{} ", AlbumName);
            }
            try
            {
                if (logger.isDebugEnabled())
                    logger.debug("MusicBrainz lookup start");

                try
                {
                    MILLISECONDS.sleep(MusicBrainzElapse());
                }
                catch (InterruptedException e)
                {
                    if (logger.isErrorEnabled())
                        logger.error("Sleep error wtf?");
                }
                LinkedList<ReleaseInfo> releaseInfos = new MusicBrainzWebClient("AudioAnalyzeApp").searchRelease(AlbumName);

                if (logger.isDebugEnabled())
                    logger.debug("MusicBrainz lookup end");


                if (releaseInfos != null && !releaseInfos.isEmpty())
                {
                    List<Art> ArtList = new ArrayList<>();
                    int size1 = releaseInfos.size();
                    for (int i = 0; ArtList.size() != count && i < size1; i++)
                    {
                        if (logger.isDebugEnabled())
                            logger.debug("CoverArt lookup start");

                        try
                        {
                            MILLISECONDS.sleep(MusicBrainzElapse());
                        }
                        catch (InterruptedException e)
                        {
                            if (logger.isErrorEnabled())
                                logger.error("Sleep error wtf?");
                        }
                        AlbumArt albumArt = CoverArtArchiveApi.LookupAlbumArt(releaseInfos.get(i)
                                                                                          .getReleaseMbid());
                        if (logger.isDebugEnabled())
                            logger.debug("CoverArt lookup end");

                        String releaseInfoTitle = null;
                        String name = null;

                        ReleaseInfo releaseInfo = releaseInfos.get(i);

                        if (releaseInfo != null)
                        {
                            releaseInfoTitle = releaseInfo.getTitle();
                            ArrayList<ReleaseArtist> artists = releaseInfo.getArtists();

                            if (artists != null && !artists.isEmpty())
                                name = artists.get(0).getName();
                        }


                        if (albumArt != null && albumArt.hasImages())
                            for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : albumArt.getImages())
                                if (elem.hasFront())
                                    ArtList.add(new Art(elem.getImage(),
                                                        "mega",
                                                        name,
                                                        releaseInfoTitle,
                                                        target.CoverArtArchive));
                    }
                    temp.setArtList(ArtList);

                    if (logger.isDebugEnabled())
                        logger.debug("Count of results: {}. end in {} milliseconds {}", ArtList.size(), System.currentTimeMillis() - time);
                    return temp;
                }
            }
            catch (IOException e)
            {
                if (logger.isErrorEnabled())
                    logger.error("", e);
                throw new NoMatchesException(NO_MATCHES, e);
            }
        }

        throw new NoMatchesException(NO_MATCHES);
    }


    /**
     * Clearing cache. Use it if library using too much memory.
     */
    public static void ClearCache()
    {
        BuildTagProcessing.ClearCache();
    }


    /** Adding to targetList only the one link from imageList with highest resolution.
     * @param Artist Artist name.
     * @param Album Album title.
     * @param imageList original list for processing.
     * @param targetList target list for addition.
     */
    private static void AddMostResolutionImageLink(String Artist,
                                                   String Album,
                                                   List<image> imageList,
                                                   @NotNull List<Art> targetList)
    {
        Extract extract;

        try
        {
            extract = image.extract(imageList);

            if (extract != null && extract.hasText())
            {
                targetList.add(new Art(extract.getText(),
                                       extract.getSize(),
                                       Artist,
                                       Album,
                                       target.LastFM
                ));
            }
        }
        catch (NoMatchesException e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }

    /** Exclude duplicates from list, fill and return new list.
     * @param Files original list of String.
     * @return treated list of String.
     */
    private static List<String> ExcludeDuplicate(@NotNull List<String> Files)
    {
        Map<String, Boolean> AssistMap = new HashMap<>();
        List<String> result = null;
        if (Files.size() != 0) {
            result = new ArrayList<>();
            for (String elem : Files)
            {
                if (!AssistMap.containsKey(elem.toLowerCase()))
                {
                    result.add(elem);
                    AssistMap.put(elem.toLowerCase(), true);
                }
            }
        }
        return result;
    }

    /**
     * @param list items for for comparison
     * @return true if at least two items in the list are the same links, else false
     */
    private static boolean IsSameInstances(Object... list)
    {
        boolean result = false;
        if (list != null)
        {
            loop:
            {
                int length_ = list.length;
                for (int i = 0; i < length_; i++)
                    if (list[i] != null)
                        for (int l = i + 1; l < length_; l++)
                            if (list[l] != null && list[i] == list[l])
                            {
                                result = true;
                                break loop;
                            }
            }
        }

        return result;
    }
}
