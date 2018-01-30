package Fox.test;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import Fox.core.lib.services.acoustid.AcoustIDRequestConfig;
import Fox.core.main.CoverArtSearch;

import java.io.Console;
import java.util.Map;

public class testingJson
{
    public static void main(String[] args)
            throws
            Exception
    {
        LastFMClient lastFMClient = new LastFMClient();
        /*TrackInfo a = lastFMClient.Track().getInfo(null,
                                                         "Believe",
                                                         "Cher",
                                                         null,
                                                         null);
        ArtistInfo b = lastFMClient.Artist().getInfo(null,
                                                     "Linkin Park",
                                                     null,
                                                     null,
                                                     null);*/
       /* AlbumInfo c = lastFMClient.Album()
                                  .getInfo(null,
                                           "Cher",
                                           "Believe",
                                           null,
                                           null,
                                           null
                                          );*/

        /*LastFMTrackInfoCompilation lastFMTrackInfoCompilation = new LastFMTrackInfoCompilation(a,
                                                                                               b,
                                                                                               c);*/
        /*Search believe = lastFMClient.Album()
                                     .search(null,
                                             null,
                                             "Believe"
                                            );*/


        FingerPrint nw = new FingerPrint(
                "AQADtEmUaEkSZZoA4Djy4weO48ePK8en40jKB5F8PNKDk7he4clxv_B_XD6ePEiuHLEfdKfw4EaqHn0U4rqgHLyOPfgy-PFwj0SOI9dEaD5S68dPWDf8Y7oJ7jh54S-ii4-hlMuRJ8bJHM-I7-nwCVQ8Dc-D2fjhDsnT4Ed_XMe1wDo077iUo0ez9McHHaeHfSFCP7iGyxS-o1nxZvhH4_zAf0h-RJcsXD98psaP58XhG0-OI_6OxN_Q47hgEvmhEz--4sdp-DpkH5fQ_IguDT9-fEs8-Md74thzNM-L0JJwQVG-4gpS6UfV8MaFHl4jg7mOh0cu6EKPKXmgtPnRH-7R1-gPP2il4QarGTqeIw_2LBl-F0-kCY1T9LiwhsFjNKlRSvnRvPkwHQ-Pi4grXD-OXAtx6TiD58N_3JMRRk0laEdQK_qRi4f2bAif5fgMx9mPT7h2NE6GH_khM8mRH3_gS8EdGRWTB2lFOHegMb-RR0o-5LmC_-iVQEt0p0OeE7ty_MJFFudx52g8Df1yo7-RLPlE5A8Ouwk-MGjqFFd25IygxT3SNTSq48wSvEuDF4y2In2R7NGF6sFxppjkXMSPhi_Kp7jyog-Fpo6OHzcebbjmFskupEerG0fXI814IXmKUzmsHr_g5ypKqWjChEWfHPGgXMipY1yWBx4l4SmaKkHVcMhXHd4jXImi43mSIJd-PEvQnKIwSVfw7HiGv-iPw7GwfgjzQ08ULfgZ_Ej1Y-R45ErxQ3zAp8fFo2kiBdtzVGmGeypu6BGL5iQivTj6hMeP5yMYNVmPC-U_vHjx6kNJNFQeog-ynfjBhMpCQo8yzIf3IN-F94cdGi0X3MeBX7B7PBlGL7jwdDJyFlp_hKmPH82nBo-MRs-DPUfinMg3dEtz-ET4B5odhHGJF9dxHhUlCz5xXESeHMk_nDj64wyu7AuamLmQjCSRHzXtoqGyBKcsXB-e6sOjG5ePXDz0G-ELnyinpegZo8mpEchbHPqx53jCQ6OOI32xDz-s4zkOP8NptHiQHxWdQ8t37EF-nOCH2odJHnyOH9uPS4dtBz9S-RtUF_6L8MQpEwGehDp-VCQ--DsuHD456Prg49eE8sdzoDou5ehv_Mug6UfjSOgNVxnR4dgT1NvRPD1k_-hBNVoq_Eeeo7OC_4i5KODzQ7N0PIbbGOWGHyPtQa9z5EetxmiiCf9xEZ-JkBqaL4K6H81Z9OJxO4OWo6bwnmjjC7ZPdFNa9D9-6PmIXIR9hMed4xxUKo-MPMMnoiLSF_py1B9iHX1gmcVvpL4o9IHOHEes5MHxB9dRNBOPPBU69YZ4Ij_c-MR39MfLo3l2RNIDPciSSTq-o_l08MlUXMbO42SMUJYMUdZQLhLiPQ4uhRV-4ggz5iq0RsfrGLmFz8El4h5-CV16EamDmtB5hGR4-Aq-HGnVHOoWXEE6tgrxH02NJ8fxx8OXHPeoCJ-Pz-jxI_0h9_jh-IeeI42DFzesphTRjMf34OGN6jr6C3kSqAceSwjXI8cjaLkShbgYXfhyQTWe7AvCZwqaHie-RDl2fwg5poMUK1EQ-3i-oomzUHgkbcPFZMSO5D6iJ8ZN4eoq4WwmGH1zPBJ-5F8gh5GJXCaqHn4e9Cju48ct0P_gHUnuscjhoz_eZCnyQ44k5Aueo77RHLnkAZqa4lDH_Xh5xJqHpoqOoxc8KVKPPtSwI5Z0Qk2Yo42Obcxi2EWPa4GGMMvz4Gou40euE33QTBZT6A9y7diPji8cbvjBeULzfEAuEW-kBj0ahivyFF-L88G3aPjSC3mKJie0H7nAH9d19Me4NSWOmYVfVLxKfE_xYz4eMUc5TKlwZGV4nGJBPy062DO-HH5mYj_k480OH89yoh2PH7GOH73QPBh-4Rrxoz_sCu-PhPcRb0djzcJI5SF-5IEu5miO_pCbD2GuHT3x4FrUoDyshhWuo6bxJTXCX9Af5MGFPkzwRLvx_YhFdtCsLCvC-MUj4pKOSslDNMkfPMuN50dOKDfCH16WGX0Y_OgZI82bEd0EfcHBZ2Ea3EQPicfHIB_WHn1znKicwWOLDz1-5NCFxgxCOcyxC89xHtV5uDv6Cw-a6nj1o-mWDtH3Qi5yKcPZHE0_9DnCVLygPciFp7jMoVdh7uhznMeLGrElKB_CF_wu_MU66cRDvFlI5Cb0KEEoHo014yqqX2iOfSzyQkp1RLuPh8c7E1ZpvCX8oMenI0-OH0dFDh9cvAf8D7rqwcfbCSX34ydKvCL64Ktx6Vmh5Uc_GfUOnyKK9XiSHG0cNN8L_WBOuNFSXDeirTmaD7uPU9mQPUe9Q39ifIN_TL-Ccz_0Iz-a6aiq48eDE-HRcIugHs3ZDWWpDF0G8URNHScndAos-kRopUcoP7ih5yNyFx76I3eOkzt0JQ_-GaE6ET5yBY8OfYjF4xT84_WRPhSFHrp4_Iil48TZ42h-lOqRF52qG-KR-3DjEz-6D_2xxTxCO9AcZBp1ws_Q0-CTY5Zx6ThrxI0OLWo0APaYhI6do0UQikgHFAIMMAGBAQggoQxRiFhIBHCcIAGQcE8CIxAgQDhRCEACAEUUUUIwYjhhUAngOUEAKQMYY4gpArgACCEBpDLKCEAEUSArxwAAiDDAyHLIWQIMIcACRoAEwDDgiEHCEcQAIYwwIxFRhgjBmCAEmeAUIUQwhQwhAAimECGEASQEA8AQAAAxAAgjAGAIAKEIMopAIAAAAghjACBGEEUAEZAwAAkADAACHCLKOCKUAwAZIRB0AiFAAMDCIMFAIgZYQyAAQgpACAVMAKKIEYQhZaQARjl2DAHEQEEME4ZRApAAACCDhAHCWWAAF4g0BYBQkCgoGBBCAEocJIwJBiBARCECjFQGMWIcJUIJgoh0gEEFgCBAMAQgEIwQ4YQxEggDHHPGUWEEEAwbpsAATiAjCBFOEOAMQgADoQgiQBHmBCEGAuKIQUIIKAhDyhBEDDCCDYEAEQoRwpARQAAlqEMIAGgAIQ4gAAAQSggAhAJAIWKMSIIJp4BAFAKAASGAKCeIogIaJAlARADBADTEOQEEIYAQQRgAiQEhiFFEICikoMQ7QZBUhRgEBFCOIYEEIQggwkwyhAFBhFGUIaKEAMQooxQxQBAjhDECKIeAAMIhKoRhgjAGHFLEOCK1FEACIgkQwAEhiGJYAECIsgoAIIRERAklkGMEUqCEAMowgIRQxiCIEDKAOEWBE4wKgzAAgAFhmBEAMCCY4U4BBB2AhhkhBCIIEaUIQgABAChDmAEgGIASAUCMQIIhBYBQggHBlYAAGWGUIkRCwwwQzAkGmFhKOAYAIEJYCRAASghEBAJKMYKYsIAqAQBwRihhkEQGAAEoIkwYgKgRCEhIDaFEGCGEMAwgBJAwAABuqBBICUKcAAQITABARAAEEIAAQIGMdKA4IAQDDDFEDKBGEEQAIMoYZZKgBAiBgDBOOeEcEUIKaAhwhBAJDUDEGEIE",
                "187",
                ""
        );
        long l = System.currentTimeMillis();
        AlbumArtCompilation meteora1 = new CoverArtSearch().run("Blood Visions",
                                                                null,
                                                                target.LastFM,
                                                                5
                                                               );
        l = System.currentTimeMillis() - l;


        AcoustIDRequestConfig config = new AcoustIDRequestConfig();

        config.setDefault();

        AcoustIDClient client = new AcoustIDClient(config);
        //ByFingerPrint byFingerPrint = client.LookupByFingerPrint(nw);
        /*AlbumArt albumArt = new CoverArtArchiveClient().LookupAlbumArt(c.getAlbum()
                                                                        .getMbid());*/
        System.out.println(1);

    }
}
