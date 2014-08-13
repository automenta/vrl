/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrl.system;

import vrl.io.Download;
import vrl.io.IOUtil;
import vrl.reflection.VisualCanvas;
import vrl.visual.MessageType;
import vrl.visual.VDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public abstract class VRLUpdateActionBase implements VRLUpdateAction {

    public VRLUpdateActionBase() {
        //
    }

    @Override
    public void checkForUpdates(VRLUpdater updater, Download d, URL location) {

        VMessage.info("Checking for updates:",
                ">> checking for updates from " + location.toExternalForm());

        VisualCanvas canvas =
                VRL.getCurrentProjectController().getCurrentCanvas();
    }

    @Override
    public void errorOccured(VRLUpdater updater, Download d, URL location) {

        VMessage.getMsgBox().addUniqueMessage("Download failed",
                ">> checking for updates failed! Location: "
                + location.toExternalForm(),
                null, MessageType.WARNING);
    }

    @Override
    public void cannotReadRepositoryFile(
            VRLUpdater updater, File repositoryFile) {
        VMessage.error("Cannot check for updates:",
                ">> checking for updates failed! "
                + "Cannot read repository file: " + repositoryFile);
    }

    @Override
    public void repositoryFileHasWrongFormat(
            VRLUpdater updater, File repositoryFile) {
        VMessage.error("Cannot check for updates:",
                ">> checking for updates failed! "
                + "Repository file has wrong format: " + repositoryFile);
    }

//    @Override
//    public void downloadFinished(VRLUpdater updater, Download d, URL location) {
//
//        VMessage.info("Donloaded updates repository:",
//                ">> checking for updates finished!");
//
//    }
    @Override
    public void updateAvailable(final VRLUpdater updater, Download d,
            URL location, final RepositoryEntry update) {

        VisualCanvas canvas =
                VRL.getCurrentProjectController().getCurrentCanvas();

        if (VDialog.YES == VDialog.showConfirmDialog(canvas,
                "Update available!",
                "Shall the update "
                + update.getName() + "-" + update.getVersion()
                + " be downloaded?", VDialog.YES_NO)) {

            updater.downloadUpdate(update, new VRLDownloadActionImpl(this) {
                @Override
                public void finished(Download d, String url) {

                    installAction(updater, update, d.getTargetFile());
                }
            });
        }
    }

    @Override
    public void updateDownloadStateChanged(Download d) {
        //
    }

    @Override
    public void hostUnreachable(VRLUpdater updater, URL url) {
        //
    }
}

class VRLDownloadActionImpl implements VRLDownloadAction {

    private File targetFile;
    private VRLUpdateAction baseAction;

    public VRLDownloadActionImpl(VRLUpdateAction action) {
        this.baseAction = action;
    }

    @Override
    public void errorOccured(Download d, URL url, String error) {
        VMessage.getMsgBox().addUniqueMessage("Download failed",
                ">> download failed! Error: " + error,
                null, MessageType.WARNING);

    }

    @Override
    public void finished(Download d, String url) {
        VMessage.info("Download finished",
                ">> download finished: " + d.getTargetFile());
        targetFile = d.getTargetFile();
    }

    @Override
    public int getConnectionTimeout() {
        return 5000;
    }

    @Override
    public int getReadTimeout() {
        return 60 * 1000;
    }

    @Override
    public File getDownloadFolder() {
        try {
            return IOUtil.createTempDir();
        } catch (IOException ex) {
            Logger.getLogger(VRLDownloadActionImpl.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * @return the targetFile
     */
    @Override
    public File getTargetFile() {
        return targetFile;
    }

    @Override
    public void downloadStateChanged(Download d) {
        baseAction.updateDownloadStateChanged(d);
    }

    @Override
    public void startVerification(Download d) {
        baseAction.startVerification(d);
    }

    @Override
    public void stopVerification(Download d, boolean verificationSuccessful) {
        baseAction.stopVerification(d, verificationSuccessful);
    }
}
//File repositoryFile = d.getTargetFile();
//
//        XMLDecoder encoder = null;
//
//        try {
//            encoder = new XMLDecoder(new FileInputStream(repositoryFile));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(UpdateActionImpl.class.getName()).
//                    log(Level.SEVERE, null, ex);
//            VMessage.error("Cannot check for updates:",
//                    ">> checking for updates failed! "
//                    + "Cannot read repository file: " + repositoryFile);
//        }
//
//        Object obj = encoder.readObject();
//
//        if (!(obj instanceof Repository)) {
//            VMessage.error("Cannot check for updates:",
//                    ">> checking for updates failed! "
//                    + "Repository file has wrong format: " + repositoryFile);
//        }
//
//        Repository repository = (Repository) obj;
//
//        List<RepositoryEntry> possibleUpdates =
//                new ArrayList<RepositoryEntry>();
//
//        // search for possible updates
//        for (RepositoryEntry e : repository.getEntries()) {
//
//            if (e.getName() == null || e.getVersion() == null) {
//                System.err.println(">> update name or version null!");
//                continue;
//            }
//
//            if (!e.getName().trim().equals(updater.getIdentifier().getName())) {
//                continue;
//            }
//
//            VersionInfo vInfo = new VersionInfo(e.getVersion());
//
//            if (!vInfo.isVersionValid()) {
//                System.err.println(
//                        ">> update version invalid: name="
//                        + e.getName() + ", version= " + e.getVersion());
//                continue;
//            }
//
//            if (vInfo.compareTo(updater.getIdentifier()) > 0) {
//                possibleUpdates.add(e);
//            }
//        }
//
//        // choose the minimum version
//        // (we are careful and don't support direct updates)
//        VersionInfo min = updater.getIdentifier().getVersion();
//        RepositoryEntry minE = null;
//
//        for (RepositoryEntry e : possibleUpdates) {
//
//            VersionInfo vInfo = new VersionInfo(e.getVersion());
//
//            if (vInfo.compareTo(min) < 0) {
//                min = vInfo;
//                minE = e;
//            }
//        }
//
//        // we found and update
//        if (minE != null) {
//            VDialog.showConfirmDialog(canvas, null, null, VDialog.DialogType.OK)
//        }