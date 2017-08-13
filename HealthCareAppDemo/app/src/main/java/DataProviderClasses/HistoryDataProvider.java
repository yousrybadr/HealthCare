package DataProviderClasses;

/**
 * Created by mahmoud on 2016-04-19.
 */
public class HistoryDataProvider {
    private String mDoctorName, mCaseDescription ,mDrugs, mNotes, mDate;

    public HistoryDataProvider(String mDoctorName, String mCaseDescription, String mDrugs, String mNotes, String mDate) {
        this.mDoctorName = mDoctorName;
        this.mCaseDescription = mCaseDescription;
        this.mDrugs = mDrugs;
        this.mNotes = mNotes;
        this.mDate = mDate;
    }

    public String getmDoctorName() {
        return mDoctorName;
    }

    public void setmDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }

    public String getmCaseDescription() {
        return mCaseDescription;
    }

    public void setmCaseDescription(String mCaseDescription) {
        this.mCaseDescription = mCaseDescription;
    }

    public String getmDrugs() {
        return mDrugs;
    }

    public void setmDrugs(String mDrugs) {
        this.mDrugs = mDrugs;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
