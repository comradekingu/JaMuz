/*
 * Copyright (C) 2015 phramusca ( https://github.com/phramusca/JaMuz/ )
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jamuz.tests;
import jamuz.AlbumBuffer;
import jamuz.process.sync.Device;
import jamuz.FileInfo;
import jamuz.process.check.FolderInfo;
import jamuz.Jamuz;
import jamuz.process.check.PanelCheck;
import jamuz.gui.PanelMain;
import jamuz.Playlist;
import jamuz.process.check.ProcessCheck;
import jamuz.process.check.ProcessCheck.Action;
import jamuz.ProcessHelper;
import jamuz.Settings;
import static jamuz.Settings.getMusicFolder;
import jamuz.process.merge.StatSource;
import java.io.File;
import java.util.ArrayList;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import jamuz.utils.Inter;

/**
 *
 * @author phramusca ( https://github.com/phramusca/JaMuz/ )
 */
public class MergeNTest extends TestCase {

    @Test
    public void test() throws Exception {
        
        Settings.startGUI("Label.Check"); //Mandatory
        
        //Create somes albums
        ArrayList<String> mbIds = new ArrayList<>();
        mbIds.add("9e097b10-8160-491e-a310-e26e54a86a10");
        mbIds.add("9dc7fe6a-3fa4-4461-8975-ecb7218b39a3");
        mbIds.add("c212b71b-848c-491c-8ae7-b62a993ae194");
        mbIds.add("8cfbb741-bd63-449f-9e48-4d234264c8d5");
        mbIds.add("be04bc1f-fc63-48f5-b1ca-2723f17d241d");
        mbIds.add("6cc35892-c44f-4aa7-bfee-5f63eca70821");
        mbIds.add("7598d527-bc8d-4282-a72c-874f335d05ac");
        mbIds.add("13ca98f6-1a9f-4d76-a3b3-a72a16d91916");
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice1_KO").create();
        }

        //Scan library
        ProcessHelper.scanLibraryQuick();
        checkNumberScanned(mbIds.size());
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice1_KO").checkAfterScan(); //Note that there are no results as folder is not analyzed: expected for now
            AlbumBuffer.getAlbum(mbId, "MergeDevice2_DB").checkDbAndFS(false);
        }

        //Update stats in JamuZ (rating=4) to have enough (but not too many) files 
        //in test playlist to export to device
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice3_JaMuz").setAndCheckStatsInJamuzDb();
        }

        //Sync and Check sync
        PanelMain.selectTab(Inter.get("PanelMain.panelSync.TabConstraints.tabTitle"));
        ProcessHelper.sync(); 
        for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
            Device device = statSource.getDevice();
            Playlist playlist = device.getPlaylist();
            if(playlist.getId()>0) {
                for(String mbId : mbIds) {
                    AlbumBuffer.getAlbum(mbId, "MergeDevice3_JaMuz").checkFSdevice(device, false);
                }
            }
        }
        
        PanelMain.selectTab(Inter.get("Label.Merge"));
        ProcessHelper.merge();
        //Check then change statistics on all stat sources, including jamuz for all albums
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice4_1stMerge").checkJaMuz();
            
            for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
                Device device = statSource.getDevice();
                Playlist playlist = device.getPlaylist(); 
                //Check merge
                AlbumBuffer.getAlbum(mbId, "MergeDevice4_1stMerge").checkStatSource(statSource.getId(), playlist.getId()>0);
                //Change stats in stat source
                //TODO: change statistics in album defintion files for all stat sources, inclusing jamuz for all albums
                AlbumBuffer.getAlbum(mbId, "MergeDevice5_"+statSource.getIdStatement()).setAndCheckStatsInStatSource(statSource.getId(), playlist.getId()>0);
            }
            //Change stats in JamuZ
            AlbumBuffer.getAlbum(mbId, "MergeDevice5_JaMuz").setAndCheckStatsInJamuzDb();
        }

        //Merge again and check merge ok
        ProcessHelper.merge();
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice6_New").checkJaMuz();
            for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
                Device device = statSource.getDevice();
                Playlist playlist = device.getPlaylist(); 
                AlbumBuffer.getAlbum(mbId, "MergeDevice6_New").checkStatSource(statSource.getId(), playlist.getId()>0);
            }
        }
        
        //Scan library and SAVE in order to modiy path and filenames
        PanelMain.selectTab(Inter.get("Label.Check"));
        ProcessHelper.checkLibrary();
        checkNumberScanned(mbIds.size());
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice7_KO").checkAfterScan();
            //Set genre, cover and SAVE action. Apply changes
            //Note that MusiBrainz album should have been retrieved
            FolderInfo folder = AlbumBuffer.getAlbum(mbId, "MergeDevice7_KO").getCheckedFolder();
            folder.setNewGenre("Reggae");
            folder.setNewImage(Settings.getTestCover());
            folder.action=Action.SAVE;
            PanelCheck.addToActionQueue(folder);
        }

        //Apply changes and scan again 
        ProcessHelper.applyChanges();
        ProcessHelper.checkLibrary();
        checkNumberScanned(mbIds.size());
        
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice8_OK").checkAfterScan(); //MusicBrainz + Reggae + cover => OK
        }

        //OK should have been selected. Apply changes
        ProcessHelper.applyChanges();
        //Verifying there is nothing left unchecked
        ProcessHelper.checkLibrary();
        checkNumberScanned(0);
        
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice9_DbOk").checkDbAndFS(true); // In DB and OK
        }
        
        //First test that when stat sources are not upated, we have "not found files" issues after merge
        // Except for the one linked to a device as we have stored original path and filename
        
        PanelMain.selectTab(Inter.get("Label.Merge"));
        ProcessHelper.merge();
        
        int nbErrorsExpected=0;
        for(String mbId : mbIds) {
            nbErrorsExpected+=AlbumBuffer.getAlbum(mbId, "MergeDevice9_DbOk").getNbTracks();
        }
        //*2*(2*(Jamuz.getMachine().getStatSources().size()-2)+1) BECAUSE:
        //  *2:         source vs jamuz and reverse side
        //  *2:         1st and second run
        //  -1:         for removing middle merged source (has only one run)
        //  +1:         to add unique run for middle stat source
        //  -1 again:   to remove MyTunes that has no expected errors
        nbErrorsExpected=nbErrorsExpected*2*(2*(Jamuz.getMachine().getStatSources().size()-1)+1-1);
        ArrayList<FileInfo> errorList = ProcessHelper.processMerge.getErrorList();
        assertEquals("Nb errors", nbErrorsExpected, errorList.size());
        
        //None expected completed (all missing on both sides, and MyTunes has no changes)
        ArrayList<FileInfo> completedList = ProcessHelper.processMerge.getCompletedList();
        assertEquals("Nb completed", 0, completedList.size());
        
        //No change expected, so using the same album version finally
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice9_DbOk").checkJaMuz();            
            for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
                Device device = statSource.getDevice();
                Playlist playlist = device.getPlaylist(); 
                
                AlbumBuffer.getAlbum(mbId, "MergeDevice9_DbOk").checkStatSource(statSource.getId(), playlist.getId()>0);
                
                //Change stats in stat source for MyTunes only
                if(playlist.getId()>0) {
                    //TODO: Modify more albums, not only one as for now
                    AlbumBuffer.getAlbum(mbId, "MergeDevice10_"+statSource.getIdStatement()).setAndCheckStatsInStatSource(statSource.getId(), playlist.getId()>0);
                }
            }
            //Change stats in JamuZ
            AlbumBuffer.getAlbum(mbId, "MergeDevice10_JaMuz").setAndCheckStatsInJamuzDb();
        }
        
        //Merge again
        PanelMain.selectTab(Inter.get("Label.Merge"));
        ProcessHelper.merge();

        //Check only MyTunes is merged and properly
        assertEquals("Nb errors", nbErrorsExpected, ProcessHelper.processMerge.getErrorList().size());

        //No change expected, so using the same album version finally
        for(String mbId : mbIds) {
            AlbumBuffer.getAlbum(mbId, "MergeDevice10_New").checkJaMuz();            
            for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
                Device device = statSource.getDevice();
                Playlist playlist = device.getPlaylist(); 
                
                if(playlist.getId()>0) {
                    //MyTunes has changed, so checking new values
                    AlbumBuffer.getAlbum(mbId, "MergeDevice10_New").checkStatSource(statSource.getId(), playlist.getId()>0);
                }
                else {
                    //Other stat sources are not changed. Check no updates.
                    AlbumBuffer.getAlbum(mbId, "MergeDevice9_DbOk").checkStatSource(statSource.getId(), playlist.getId()>0);
                }
            }
        }
        
//        //TODO: Merge again but now all stat sources have been synced and scanned 
//        // so that new path and file can match for merge
//        //TODO: Update album file definitions for this point
//        // Then use that db (read it: change StatSource.location somewhat)
//        ProcessHelper.merge();
//        for(String mbId : mbIds) {
//            AlbumBuffer.getAlbum(mbId, "MergeDevice11_Sync").checkJaMuz();
//            
//            for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
//                Device device = statSource.getDevice();
//                Playlist playlist = device.getPlaylist(); 
//                
//                AlbumBuffer.getAlbum(mbId, "MergeDevice11_Sync").checkStatSource(statSource.getId(), playlist.getId()>0);
//            }
//        }
        
        //Sync and Check sync
        PanelMain.selectTab(Inter.get("PanelMain.panelSync.TabConstraints.tabTitle"));
        ProcessHelper.sync(); 
        //TODO: make sure that devicefile table is properly cleaned up and there are only playlist files
        //TODO: Do the same check (devicefile) for first sync then
        for(StatSource statSource : Jamuz.getMachine().getStatSources()) {
            Device device = statSource.getDevice();
            Playlist playlist = device.getPlaylist();
            if(playlist.getId()>0) {
                for(String mbId : mbIds) {
                    AlbumBuffer.getAlbum(mbId, "MergeDevice11_Sync2").checkFSdevice(device,  true);
                }
            }
        }
        
        //TODO: Merge again and check merge is OK for all sources
        
        assertTrue("Not valid test. Shall no pass yet !", false);
        //TODO: Update other test classes
    }

    private void checkNumberScanned(int expected){
       assertEquals("number of checked folders", expected, PanelCheck.tableModelCheck.getFolders().size());
    }

    public MergeNTest(String testMethodName) {
        super(testMethodName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Settings.setupApplication();
        
        //Create test playlist (genre=reggae) for merge test with a device
        Playlist playlist = new Playlist(0, "TestDevicePlaylist", false, 1, Playlist.LimitUnit.Gio, false,
            Playlist.Type.Songs, Playlist.Match.All);
        Assert.assertTrue("playlist creation", playlist.insert());
        
        //Getting playlist from library (so that id is set , so we can add filter)
        //Read created options
        Jamuz.readPlaylists();
        playlist = Jamuz.getPlaylist(1);
        
        Playlist.Filter filter = new Playlist.Filter(-1, Playlist.Field.RATING, Playlist.Operator.IS, "4"); //NOI18N
        playlist.addFilter(filter); 
        playlist.update();
        
        //Create test device
        //TODO: create a device for each stat source
        Device device = new Device(-1, 
                "TestDevice", 
                FilenameUtils.normalizeNoEndSeparator(getMusicFolder() + "Archive")+File.separator, 
                FilenameUtils.normalizeNoEndSeparator(getMusicFolder() + "TestDevice")+File.separator, 
                1, //playlist.getId()is not set (only when retrieved from db)
                Jamuz.getMachine().getName()
        );
        Assert.assertTrue("Device creation", Jamuz.getDb().setDevice(device));
        
        //Set stat sources
        String rootPath; int idDevice; String name;
        int idStatement;
//        case 1: // Guayadeque 	(Linux)
//        case 2: // XBMC 	(Linux/Windows)
//        case 3: // MediaMonkey (Windows)
//        case 4: // Mixxx 	(Linux/Windows)
//        case 5: // MyTunes 	(Android)
        Settings.addStatSource(
            name = "guayadeque_Device.db", 
            idStatement=1, 
            rootPath=Settings.getMusicFolder() + "Archive" + File.separator, 
            idDevice = -1
        );
        Settings.addStatSource(
                name = "MyMusic32_Device.db", //TODO: Test in Windows and test on a SSH box and a FTP box
                idStatement=2, 
                rootPath=Settings.getMusicFolder() + "Archive" + File.separator, 
                idDevice = -1);
        //TODO: Enable this when I have a Windows PC available
//        Settings.addStatSource(
//                name = "MediaMonkey source", 
//                idStatement=3, 
//                rootPath=Settings.getMusicFolder() + "Archive" + File.separator, 
//                idDevice = -1);
        Settings.addStatSource(
                name = "mixxxdb_Device.sqlite", //TODO: Test on windows
                idStatement=4, 
                rootPath=Settings.getMusicFolder() + "Archive" + File.separator, 
                idDevice = -1);
        Settings.addStatSource(
                name = "MusicIndexDatabase_Device.db", 
                idStatement=5, 
                rootPath="/storage/extSdCard/Musique/",
                idDevice = 1); //This one has a linked device
        
        //Read created options
        Jamuz.getMachine().read();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}