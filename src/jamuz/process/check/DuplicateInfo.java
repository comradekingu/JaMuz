/*
 * Copyright (C) 2016 phramusca ( https://github.com/phramusca/JaMuz/ )
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
package jamuz.process.check;

/**
 *
 * @author phramusca ( https://github.com/phramusca/JaMuz/ )
 */
public class DuplicateInfo {

    /**
     *
     * @param album
     * @param artist
     * @param rating
     * @param checkedFlag
     * @param errorLevel
     */
    public DuplicateInfo(String album, String artist, double rating, FolderInfo.CheckedFlag checkedFlag, int errorLevel) {
        this.album = album;
        this.artist = artist;
        this.rating = rating;
        this.checkedFlag = checkedFlag;
        this.errorLevel = errorLevel;
    }

    private String album;

    /**
     * Get the value of album
     *
     * @return the value of album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Set the value of album
     *
     * @param album new value of album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    private String artist;

    /**
     * Get the value of artist
     *
     * @return the value of artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Set the value of artist
     *
     * @param artist new value of artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

        private double rating;

    /**
     * Get the value of rating
     *
     * @return the value of rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Set the value of rating
     *
     * @param rating new value of rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }


    private FolderInfo.CheckedFlag checkedFlag;

    /**
     * Get the value of checkedFlag
     *
     * @return the value of checkedFlag
     */
    public FolderInfo.CheckedFlag getCheckedFlag() {
        return checkedFlag;
    }

    /**
     * Set the value of checkedFlag
     *
     * @param checkedFlag new value of checkedFlag
     */
    public void setCheckedFlag(FolderInfo.CheckedFlag checkedFlag) {
        this.checkedFlag = checkedFlag;
    }
    
        private int errorLevel;

    /**
     * Get the value of errorLevel
     *
     * @return the value of errorLevel
     */
    public int getErrorLevel() {
        return errorLevel;
    }

    /**
     * Set the value of errorLevel
     *
     * @param errorLevel new value of errorLevel
     */
    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }


    @Override
    public String toString() {
        return "<html><b>" + FolderInfoResult.colorField("\"" + album + "\" (\"" + artist + "\")",  //NOI18N
                        errorLevel, false) + "</b> ["+checkedFlag.toString()+"] ["+rating+"]</html>";
    }
    
    
}
